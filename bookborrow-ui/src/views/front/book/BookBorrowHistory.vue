<template>
  <div class="book-borrow-container">
    <book-header />
    <div class="page-header">
      <div class="header-content">
        <h1 class="page-title">我的借阅</h1>
        <p class="page-subtitle">管理您当前借阅的图书</p>
      </div>
      <el-tabs
        v-model="activeTab"
        class="custom-tabs"
        @tab-click="handleTabClick"
      >
        <el-tab-pane label="在借中" name="borrowing"></el-tab-pane>
        <el-tab-pane label="已逾期" name="overdue"></el-tab-pane>
        <el-tab-pane label="借书历史" name="history"></el-tab-pane>
      </el-tabs>
    </div>

    <div class="content-wrapper">
      <div
        v-loading="loading"
        class="book-list"
        element-loading-background="rgba(255, 255, 255, 0.7)"
      >
        <transition-group class="transition-container" name="list" tag="div">
          <el-card
            v-for="book in borrowList"
            :key="book.id"
            :body-style="{ padding: '0' }"
            class="book-card"
            shadow="hover"
          >
            <div class="card-content">
              <div class="book-cover-container">
                <div class="book-cover">
                  <el-image
                    :preview-src-list="[book.imgCover]"
                    :src="book.imgCover | bookCoverUrl"
                    :style="{ backgroundColor: '#f5f5f5' }"
                    class="cover-image"
                    fit="cover"
                  >
                    <div slot="error" class="image-slot">
                      <div class="cover-placeholder">
                        <i class="el-icon-notebook-2"></i>
                      </div>
                    </div>
                  </el-image>
                </div>
                <!-- 移除逾期标签，因为历史记录不需要 -->
              </div>

              <div class="book-info">
                <div class="info-header">
                  <h3 class="book-title">{{ book.bookName }}</h3>
                  <el-tag class="category-tag" effect="plain" size="small">
                    {{ book.categoryName }}
                  </el-tag>
                </div>

                <div class="book-meta">
                  <div class="meta-row">
                    <i class="el-icon-user meta-icon"></i>
                    <span class="meta-text"
                      >借阅人: {{ book.userName || "匿名用户" }}</span
                    >
                  </div>

                  <div class="meta-row">
                    <i class="el-icon-date meta-icon"></i>
                    <span class="meta-text"
                      >借阅日期: {{ formatTime(book.borrowTime) }}</span
                    >
                  </div>

                  <div class="meta-row">
                    <i class="el-icon-time meta-icon"></i>
                    <span class="meta-text">
                      {{ activeTab === "history" ? "归还日期" : "应还日期" }}:
                      {{
                        activeTab === "history"
                          ? formatTime(book.returnTime)
                          : formatTime(book.dueTime)
                      }}
                    </span>
                    <!-- 只在非历史页面显示逾期标签 -->
                    <el-tag
                      v-if="activeTab !== 'history' && isOverdue(book.dueTime)"
                      class="overdue-tag"
                      effect="dark"
                      size="mini"
                      type="danger"
                    >
                      逾期 {{ calculateOverdueDays(book.dueTime) }} 天
                    </el-tag>
                  </div>

                  <!-- 添加借阅时长显示 -->
                  <div v-if="activeTab === 'history'" class="meta-row">
                    <i class="el-icon-timer meta-icon"></i>
                    <span class="meta-text">
                      借阅时长:
                      {{
                        calculateBorrowDuration(
                          book.borrowTime,
                          book.returnTime
                        )
                      }}
                    </span>
                  </div>
                </div>

                <!-- 操作按钮区域 - 根据tab显示不同按钮 -->
                <div class="book-actions">
                  <!-- 历史记录只显示查看详情按钮 -->
                  <template v-if="activeTab === 'history'">
                    <el-button
                      class="action-btn detail-btn"
                      plain
                      size="small"
                      @click="handleViewDetail(book)"
                    >
                      <i class="el-icon-document"></i> 查看详情
                    </el-button>
                  </template>

                  <!-- 非历史记录显示续借和归还按钮 -->
                  <template v-else>
                    <el-tooltip content="续借可延长30天借阅期" placement="top">
                      <el-button
                        :disabled="isOverdue(book.dueTime)"
                        class="action-btn renew-btn"
                        plain
                        size="small"
                        type="primary"
                        @click="handleRenew(book)"
                      >
                        <i class="el-icon-refresh-left"></i> 续借
                      </el-button>
                    </el-tooltip>
                    <el-button
                      :type="isOverdue(book.dueTime) ? 'danger' : 'success'"
                      class="action-btn return-btn"
                      plain
                      size="small"
                      @click="handleReturn(book)"
                    >
                      <i class="el-icon-circle-check"></i> 归还
                    </el-button>
                  </template>
                </div>
              </div>
            </div>
          </el-card>
        </transition-group>

        <el-empty
          v-if="borrowList.length === 0"
          class="empty-state"
          description="暂无借阅记录"
        >
          <el-button
            size="small"
            type="primary"
            @click="$router.push('/front/index')"
            >去借书
          </el-button>
        </el-empty>
      </div>

      <!-- 分页器 -->
      <div v-if="total > 0" class="pagination-wrapper">
        <el-pagination
          :current-page="pageNum"
          :page-size="pageSize"
          :page-sizes="[5, 10, 15, 20]"
          :total="total"
          hide-on-single-page
          layout="total, sizes, prev, pager, next, jumper"
          @current-change="handlePageChange"
          @size-change="handlePageSizeChange"
        >
        </el-pagination>
      </div>
    </div>

    <YFooter />
  </div>
</template>

<script>
import { listLend } from "@/api/bookinfo/lend";
import moment from "moment";
import BookHeader from "@/views/front/book/BookHeader.vue";
import YFooter from "@/views/components/footer/YFooter.vue";
export default {
  components: { BookHeader, YFooter },
  data() {
    return {
      total: 0,
      pageNum: 1,
      pageSize: 5,
      loading: false,
      status: 2, // 查看逾期的
      activeTab: "overdue", // 逾期的激活页
      borrowList: [],
    };
  },
  methods: {
    formatTime(time) {
      return moment(time).format("YYYY-MM-DD");
    },
    handlePageChange(page) {
      this.pageNum = page;
      this.fetchBorrowList();
    },
    handlePageSizeChange(size) {
      this.pageSize = size;
      this.pageNum = 1;
      this.fetchBorrowList();
    },
    calculateLateFee(dueTime) {
      const now = new Date();
      const due = new Date(dueTime);
      const diffMs = now - due;
      const hours = Math.max(Math.floor(diffMs / (1000 * 60 * 60)), 0);
      return (hours * 0.3).toFixed(2);
    },
    isOverdue(dueTime) {
      return moment(dueTime).isBefore(moment());
    },
    calculateOverdueDays(dueTime) {
      return moment().diff(moment(dueTime), "days");
    },
    handleRenew(book) {
      this.$confirm(
        `确定要续借《${book.bookName}》吗? 续借后将延长30天借阅期。`,
        "续借确认",
        {
          confirmButtonText: "确认续借",
          cancelButtonText: "再想想",
          type: "info",
          center: true,
        }
      )
        .then(() => {
          this.$message.success("续借成功，借阅期已延长");
        })
        .catch(() => {});
    },
    handleReturn(book) {
      const isOverdue = this.isOverdue(book.dueTime);
      const fee = isOverdue ? this.calculateLateFee(book.dueTime) : 0;

      const title = isOverdue ? "归还逾期书籍" : "归还确认";
      const message = isOverdue
        ? `《${book.bookName}》已逾期，应缴纳逾期费用 ￥${fee}，确认归还？`
        : `确定要归还《${book.bookName}》吗?`;

      this.$confirm(message, title, {
        confirmButtonText: "确认归还",
        cancelButtonText: "取消",
        type: isOverdue ? "warning" : "info",
        center: true,
      })
        .then(() => {
          this.$message.success(
            isOverdue
              ? `归还成功，已计入逾期费用 ￥${fee}`
              : "归还申请已提交，请将书籍归还至图书馆"
          );
        })
        .catch(() => {});
    },
    handleTabClick(tab) {
      this.activeTab = tab.name;
      // 根据activeTab设置API查询的status
      switch (tab.name) {
        case "borrowing":
          this.status = 0; // 在借中
          break;
        case "history":
          this.status = 1; // 借书历史(已归还的书)
          break;
        default: // 已逾期
          this.status = 2; // 不筛选状态
      }
      this.pageNum = 1;
      this.fetchBorrowList();
    },
    // 添加计算借阅时长的方法
    calculateBorrowDuration(startTime, endTime) {
      console.log("time: ", startTime, endTime);
      const start = moment(startTime);
      const end = moment(endTime);
      const duration = moment.duration(end.diff(start));

      if (duration.asDays() > 30) {
        return `${Math.floor(duration.asMonths())}个月${Math.floor(
          duration.asDays() % 30
        )}天`;
      }
      return `${Math.floor(duration.asDays())}天`;
    },

    // 添加查看详情方法
    handleViewDetail(book) {
      this.$router.push(`/front/book-detail/${book.bookId}`);
    },
    fetchBorrowList() {
      this.loading = true;

      let query = {
        pageNum: this.pageNum,
        pageSize: this.pageSize,
        status: this.status,
      };
      listLend(query)
        .then((res) => {
          this.borrowList = res.rows;
          this.loading = false;
          this.total = res.total;
        })
        .catch(() => {
          this.loading = false;
        });
    },
  },
  created() {
    this.fetchBorrowList();
  },
};
</script>

<style lang="scss" scoped>
.book-borrow-container {
  max-width: 1280px;
  margin: 0 auto;
  padding: 24px;
  min-height: calc(100vh - 48px);
  background-color: #f8f9fa;
}

.page-header {
  background: #fff;
  padding: 24px;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  margin-bottom: 24px;
  position: relative;
  overflow: hidden;

  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 4px;
    height: 100%;
    background: linear-gradient(to bottom, #409eff, #67c23a);
  }
}

.header-content {
  margin-bottom: 20px;
}

.page-title {
  font-size: 28px;
  color: #303133;
  margin-bottom: 8px;
  font-weight: 600;
  letter-spacing: 0.5px;
}

.page-subtitle {
  font-size: 14px;
  color: #909399;
  margin: 0;
}

.content-wrapper {
  background: #fff;
  padding: 24px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
  flex: 1;
}

.custom-tabs {
  ::v-deep .el-tabs__nav-wrap::after {
    height: 1px;
    background-color: #e4e7ed;
  }

  ::v-deep .el-tabs__item {
    font-size: 15px;
    padding: 0 24px;
    height: 48px;
    line-height: 48px;
    color: #606266;
    font-weight: 500;

    &:hover {
      color: #409eff;
    }
  }

  ::v-deep .el-tabs__item.is-active {
    color: #409eff;
    font-weight: 600;
  }

  ::v-deep .el-tabs__active-bar {
    background-color: #409eff;
    height: 3px;
    border-radius: 3px 3px 0 0;
  }
}

.book-list {
  position: relative;
  min-height: 300px;
}

.transition-container {
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(360px, 1fr));
  gap: 24px;
}

.book-card {
  border-radius: 12px;
  overflow: hidden;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  border: 1px solid #ebeef5;
  background-color: #fff;
  position: relative;

  &:hover {
    transform: translateY(-4px);
    box-shadow: 0 6px 18px rgba(0, 0, 0, 0.1);
    border-color: #d6e4ff;
  }
}

.card-content {
  display: flex;
  height: 100%;
}

.book-cover-container {
  position: relative;
  width: 140px;
  flex-shrink: 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 16px;
  background-color: #f5f7fa;
}

.book-cover {
  width: 100%;
  height: 180px;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
  display: flex;
  align-items: center;
  justify-content: center;
  overflow: hidden;
  border-radius: 6px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
}

.cover-image {
  width: 100%;
  height: 100%;
  transition: transform 0.3s;
  object-fit: cover;

  &:hover {
    transform: scale(1.05);
  }
}

.cover-placeholder {
  color: #c0c4cc;
  font-size: 48px;
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
  background: linear-gradient(135deg, #f5f7fa 0%, #e4e8f0 100%);
}

.overdue-ribbon {
  position: absolute;
  top: 16px;
  right: -30px;
  background-color: #ff4757;
  color: white;
  padding: 3px 30px;
  font-size: 12px;
  transform: rotate(45deg);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  font-weight: 600;
  z-index: 1;
}

.book-status {
  margin-top: 12px;
  width: 100%;
  text-align: center;

  .status-badge {
    display: inline-block;
    padding: 4px 8px;
    border-radius: 4px;
    font-size: 12px;
    font-weight: 500;

    i {
      margin-right: 4px;
    }

    &.overdue {
      background-color: #ffebee;
      color: #f44336;
    }

    &.normal {
      background-color: #e8f5e9;
      color: #4caf50;
    }
  }
}

.book-info {
  flex: 1;
  padding: 16px;
  display: flex;
  flex-direction: column;
}

.info-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 12px;
}

.book-title {
  margin: 0;
  font-size: 16px;
  color: #303133;
  font-weight: 600;
  line-height: 1.5;
  flex: 1;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  text-overflow: ellipsis;
}

.category-tag {
  margin-left: 8px;
  border-radius: 4px;
  background-color: #f0f7ff;
  color: #409eff;
  border-color: #d6e4ff;
  flex-shrink: 0;
}

.book-meta {
  flex: 1;
  margin: 12px 0;
}

.meta-row {
  display: flex;
  align-items: center;
  margin-bottom: 10px;
  font-size: 13px;
  color: #606266;
  line-height: 1.5;

  &.overdue-row {
    color: #f44336;
    font-weight: 500;
  }
}

.meta-icon {
  margin-right: 8px;
  font-size: 14px;
  color: #909399;
  flex-shrink: 0;
}

.meta-text {
  flex: 1;
}

.overdue-tag {
  margin-left: 8px;
  font-weight: 500;
}

.book-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: 16px;
  gap: 12px;

  .action-btn {
    min-width: 80px;
    padding: 8px 12px;
    border-radius: 6px;
    font-size: 13px;
    font-weight: 500;
    transition: all 0.2s;
    letter-spacing: 0.5px;

    i {
      margin-right: 6px;
    }

    &:hover {
      transform: translateY(-2px);
      box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    }

    &:active {
      transform: translateY(0);
    }
  }

  .renew-btn {
    border-color: #409eff;
    color: #409eff;

    &:hover {
      background-color: rgba(64, 158, 255, 0.1);
    }

    &:disabled {
      opacity: 0.6;
      cursor: not-allowed;
    }
  }

  .return-btn {
    &:not(.el-button--danger) {
      border-color: #67c23a;
      color: #67c23a;

      &:hover {
        background-color: rgba(103, 194, 58, 0.1);
      }
    }
  }
}

.empty-state {
  padding: 80px 0;
  background-color: transparent;

  ::v-deep .el-empty__description {
    margin: 16px 0 24px;
    color: #909399;
    font-size: 15px;
  }

  ::v-deep .el-empty__image {
    width: 180px;
  }
}

.pagination-wrapper {
  margin-top: 32px;
  padding-top: 24px;
  border-top: 1px solid #ebeef5;
  display: flex;
  justify-content: center;
}

/* 列表过渡动画 */
.list-enter-active,
.list-leave-active {
  transition: all 0.5s cubic-bezier(0.55, 0, 0.1, 1);
}

.list-enter,
.list-leave-to {
  opacity: 0;
  transform: translateY(20px);
}

.list-move {
  transition: transform 0.5s;
}

@media (max-width: 768px) {
  .book-borrow-container {
    padding: 16px;
  }

  .transition-container {
    grid-template-columns: 1fr;
  }

  .card-content {
    flex-direction: column;
  }

  .book-cover-container {
    width: 100%;
    padding: 16px;
    flex-direction: row;
    justify-content: center;
    gap: 16px;
  }

  .book-cover {
    width: 120px;
    height: 160px;
  }

  .book-status {
    margin-top: 12px;
    width: 100%;
    text-align: center;

    .status-badge {
      display: inline-block;
      padding: 4px 8px;
      border-radius: 4px;
      font-size: 12px;
      font-weight: 500;

      i {
        margin-right: 4px;
      }

      &.overdue {
        background-color: #ffebee;
        color: #f44336;
      }

      &.normal {
        background-color: #e8f5e9;
        color: #4caf50;
      }

      &.returned {
        background-color: #e3f2fd;
        color: #2196f3;
      }
    }
  }
}

/* 新增历史记录卡片样式 */
.book-card.history-card {
  .book-cover-container {
    background-color: #f8f8f8;
  }

  .book-status .status-badge {
    background-color: #e3f2fd;
    color: #2196f3;
  }

  .book-meta {
    .meta-row {
      color: #666;
    }
  }
}

/* 调整操作按钮区域 */
.book-actions {
  .detail-btn {
    border-color: #909399;
    color: #909399;

    &:hover {
      background-color: rgba(144, 147, 153, 0.1);
    }
  }
}
</style>
