<template>
  <div class="book-detail-wrapper">
    <book-header/>
    <div class="book-detail-container">
      <div class="book-detail-card">
        <!-- 封面区域 -->
        <div class="cover-wrapper">
          <div class="cover">
            <img :src="book.coverUrl | imageUrl" alt="封面图" class="book-cover"/>
            <div v-if="book.stock > 50" class="book-badge trending">
              <i class="el-icon-star-on"></i> 热门推荐
            </div>
            <div v-else-if="book.stock > 0" class="book-badge limited">
              <i class="el-icon-warning"></i> 库存紧张
            </div>
            <div v-else class="book-badge out-of-stock">
              <i class="el-icon-error"></i> 已借完
            </div>
          </div>

          <!-- 封面下方的快速操作区 -->
          <div class="quick-actions">
            <el-button
              :disabled="book.stock <= 0"
              class="quick-btn"
              round
              type="primary"
              @click="borrowBook"
            >
              <i class="el-icon-reading"></i> 立即借阅
            </el-button>
            <el-button
              class="quick-btn"
              plain
              round
              type="success"
              @click="addToList"
            >
              <i class="el-icon-folder-add"></i> 加入书单
            </el-button>
          </div>
        </div>

        <!-- 信息区域 -->
        <div class="info-wrapper">
          <div class="info">
            <h1 class="title">{{ book.title }}</h1>
            <p class="subtitle">{{ book.subtitle || '&nbsp;' }}</p>
            <p class="author">作者：{{ book.author }}</p>

            <!-- 评分和热门度 -->
            <div class="rating-section">
              <div class="rating">
                <el-rate
                  v-model="book.rating"
                  disabled
                  score-template="{value} 分"
                  show-score
                  text-color="#ff9900"
                />
              </div>
              <div class="popularity">
                <span class="popularity-value">
                  <i class="el-icon-view"></i> {{ book.views || 0 }} 浏览
                </span>
                <span class="borrow-count">
                  <i class="el-icon-collection"></i> {{ book.borrowTime || 0 }} 借阅
                </span>
              </div>
            </div>

            <!-- 图书元信息区域 -->
            <div class="book-meta">
              <div class="meta-grid">
                <div class="meta-item">
                  <span class="meta-label">出版社</span>
                  <span class="meta-value">{{ book.publisher || '未知' }}</span>
                </div>
                <div class="meta-item">
                  <span class="meta-label">出版时间</span>
                  <span class="meta-value">{{ book.publishDate || '未知' }}</span>
                </div>
                <div class="meta-item">
                  <span class="meta-label">分类</span>
                  <span class="meta-value">{{ book.categoryName || '未分类' }}</span>
                </div>
                <div class="meta-item">
                  <span class="meta-label">ISBN</span>
                  <span class="meta-value">{{ book.isbn || '未知' }}</span>
                </div>
                <div class="meta-item highlight">
                  <span class="meta-label">价格</span>
                  <span class="meta-value price">¥ {{ book.price || '0.00' }}</span>
                </div>
                <div class="meta-item highlight">
                  <span class="meta-label">库存状态</span>
                  <span
                    :class="{
                      'stock-ok': book.stock > 10,
                      'stock-low': book.stock > 0 && book.stock <= 10,
                      'stock-empty': book.stock <= 0
                    }"
                    class="meta-value"
                  >
                    {{ book.stock > 0 ? `${book.stock} 本可借` : '已借完' }}
                  </span>
                </div>
              </div>
            </div>

            <!-- 简介部分 -->
            <div class="description-section">
              <h3 class="section-title">
                <i class="el-icon-notebook-2"></i> 内容简介
              </h3>
              <div class="description-content">
                <p class="description">{{ book.description || '暂无内容简介' }}</p>
              </div>
            </div>

            <!-- 扩展信息 -->
            <div class="expand-section">
              <el-collapse accordion>
                <el-collapse-item name="1" title="目录结构">
                  <div class="expand-content">
                    {{ book.catalog || '暂无目录信息' }}
                  </div>
                </el-collapse-item>
                <el-collapse-item name="2" title="作者简介">
                  <div class="expand-content">
                    {{ book.authorDesc || '暂无作者简介' }}
                  </div>
                </el-collapse-item>
              </el-collapse>
            </div>
          </div>
        </div>
      </div>

      <!-- 相关推荐 -->
      <div v-if="relatedBooks.length > 0" class="related-books">
        <h3 class="related-title">
          <i class="el-icon-collection-tag"></i> 相关推荐
        </h3>
        <div class="book-list">
          <div v-for="item in relatedBooks" :key="item.id" class="book-item">
            <img :src="item.coverUrl" class="related-cover"/>
            <p class="related-book-title">{{ item.title }}</p>
          </div>
        </div>
      </div>
    </div>
    <y-footer/>
  </div>
</template>
<style lang="scss" scoped>
.book-detail-wrapper {
  background: linear-gradient(135deg, #f5f7fa 0%, #f2f5f8 100%);
  min-height: 100vh;
  padding-bottom: 10px; /* 减少底部间距 */
}

.book-detail-container {
  max-width: 1200px; /* 减少最大宽度 */
  margin: 0 auto;
  padding: 10px; /* 减少容器内边距 */
}

.book-detail-card {
  display: flex;
  flex-wrap: wrap;
  background: #fff;
  overflow: hidden;
  transition: all 0.3s ease;
  padding: 10px; /* 增加一些内边距，使内容紧凑 */
}

.cover-wrapper {
  flex: 0 0 280px; /* 缩小封面区域 */
  padding: 10px; /* 调整内边距 */
  background: linear-gradient(to bottom, #f9fafc, #f2f5f8);
  display: flex;
  flex-direction: column;
  align-items: center;

  .cover {
    width: 220px; /* 调整封面图大小 */
    height: 300px;
    background: #f6f7f8;
    border-radius: 12px;
    position: relative;
    box-shadow: 0 8px 24px rgba(0, 0, 0, 0.1);
    overflow: hidden;
    transition: transform 0.3s ease;

    &:hover {
      transform: translateY(-5px);
    }
  }

  .book-cover {
    width: 100%;
    height: 100%;
    object-fit: cover;
    transition: opacity 0.3s ease;

    &:hover {
      opacity: 0.9;
    }
  }

  .book-badge {
    position: absolute;
    top: 15px;
    right: -5px;
    padding: 8px 12px; /* 缩小徽章尺寸 */
    font-size: 12px; /* 调整字体大小 */
    font-weight: bold;
    border-radius: 4px;
    color: white;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    display: flex;
    align-items: center;

    i {
      margin-right: 5px;
      font-size: 14px; /* 调整图标大小 */
    }

    &.trending {
      background: linear-gradient(to right, #ff7b4e, #ff5e62);
    }

    &.limited {
      background: linear-gradient(to right, #ffb347, #ffcc33);
    }

    &.out-of-stock {
      background: linear-gradient(to right, #868f96, #596164);
    }
  }

  .quick-actions {
    display: grid;
    grid-template-rows: repeat(2, 44px);
    row-gap: 12px;
    width: 100%;
    margin-top: 20px;

    .quick-btn {
      font-size: 14px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
  }
}

.info-wrapper {
  flex: 1;
  min-width: 250px; /* 缩小内容区最小宽度 */
  padding: 20px 30px; /* 调整内边距 */

  .info {
    height: 100%;
    display: flex;
    flex-direction: column;
  }

  .title {
    font-size: 24px; /* 调整标题大小 */
    font-weight: 700;
    color: #2c3e50;
    margin-bottom: 5px;
    line-height: 1.3;
  }

  .subtitle {
    font-size: 14px; /* 调整副标题字体 */
    color: #666;
    margin-bottom: 15px;
  }

  .author {
    color: #666;
    margin-bottom: 15px;
    font-size: 14px;
  }

  .rating-section {
    display: flex;
    align-items: center;
    margin-bottom: 20px; /* 调整评分区间距 */
    gap: 20px;

    .rating {
      flex: 1;
    }

    .popularity {
      display: flex;
      gap: 15px;
      font-size: 12px; /* 调整热门度文本大小 */
      color: #666;

      i {
        margin-right: 5px;
        color: #409eff;
      }

      .borrow-count i {
        color: #67c23a;
      }
    }
  }

  .book-meta {
    margin-bottom: 20px; /* 调整图书元信息间距 */
    background: #f9fafc;
    border-radius: 10px;
    padding: 15px; /* 调整内边距 */

    .meta-grid {
      display: grid;
      grid-template-columns: repeat(2, 1fr);
      gap: 10px; /* 减少元信息项之间的间距 */
    }

    .meta-item {
      display: flex;
      justify-content: space-between;
      padding: 5px 0; /* 减少行间距 */
      border-bottom: 1px dashed #eaeef5;
    }

    .meta-label {
      color: #888;
      font-size: 12px; /* 调整标签字体大小 */
      min-width: 70px; /* 减少标签宽度 */
    }

    .meta-value {
      color: #333;
      text-align: right;
      font-size: 12px; /* 调整标签字体大小 */
      min-width: 70px; /* 减少标签宽度 */

      &.price {
        color: #f56c6c;
        font-size: 16px;
        font-weight: 700;
      }

      &.stock-ok {
        color: #67c23a;
      }

      &.stock-low {
        color: #e6a23c;
      }

      &.stock-empty {
        color: #f56c6c;
      }
    }
  }

  .description-section {
    margin-bottom: 20px; /* 调整简介区间距 */

    .section-title {
      font-size: 16px; /* 调整标题字体 */
      font-weight: 600;
      color: #2c3e50;
      margin-bottom: 10px;
      display: flex;
      align-items: center;

      i {
        margin-right: 8px;
        color: #409eff;
      }
    }

    .description-content {
      background: #f9fafc;
      border-radius: 8px;
      padding: 15px; /* 减少内边距 */
    }

    .description {
      color: #555;
      line-height: 1.6rem;
      font-size: 12px; /* 调整字体大小 */
      margin: 0;
      text-align: justify;

      /* 超出三行显示省略号 */
      display: -webkit-box;
      -webkit-box-orient: vertical;
      -webkit-line-clamp: 3; /* 限制显示3行 */
      overflow: hidden;
    }

  }

  .expand-section {
    margin-top: auto;

    .expand-content {
      padding: 10px;
      color: #666;
      line-height: 1.6;
      white-space: pre-line;
    }

    .el-collapse-item__header {
      font-weight: 600;
      padding-left: 10px;
    }
  }
}

.related-books {
  margin-top: 40px; /* 调整相关推荐区间距 */

  .related-title {
    font-size: 18px; /* 调整标题大小 */
    color: #2c3e50;
    margin-bottom: 20px;
    display: flex;
    align-items: center;

    i {
      margin-right: 10px;
      color: #409eff;
    }
  }

  .book-list {
    display: flex;
    gap: 10px; /* 减少书籍项之间的间距 */
    overflow-x: auto;
    padding-bottom: 15px;
  }

  .book-item {
    flex: 0 0 100px; /* 调整书籍项大小 */
    text-align: center;
    cursor: pointer;
    transition: transform 0.3s ease;

    &:hover {
      transform: translateY(-5px);
    }
  }

  .related-cover {
    width: 100px; /* 调整封面图大小 */
    height: 140px;
    object-fit: cover;
    border-radius: 6px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    margin-bottom: 10px;
  }

  .related-book-title {
    font-size: 12px; /* 调整标题字体大小 */
    color: #333;
    margin: 0;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

@media (max-width: 992px) {
  .cover-wrapper {
    flex: 0 0 100%;
    flex-direction: row;
    padding: 15px;
  }

  .info-wrapper {
    padding: 15px;
  }

  .book-meta .meta-grid {
    grid-template-columns: 1fr !important;
  }
}

@media (max-width: 768px) {
  .cover-wrapper {
    flex-direction: column;

    .cover {
      margin-right: 0;
      margin-bottom: 15px;
    }
  }

  .rating-section {
    flex-direction: column;
    gap: 10px;
  }
}

</style>
<script>
import BookHeader from "@/views/front/book/BookHeader.vue";
import YFooter from "@/views/components/footer/YFooter.vue";
import {getBook, getRelatedBooks} from "@/api/bookinfo/book";
import {addLend} from "@/api/bookinfo/lend";
import {bookCoverUrl} from "@/utils/previewImg"

export default {
  name: "BookDetailCard",
  components: {BookHeader, YFooter},
  data() {
    return {
      book: {
        rating: 4.5 // 默认评分
      },
      relatedBooks: []
    };
  },
  created() {
    this.getBookById();
    this.fetchRelatedBooks();
  },
  methods: {
    getBookById() {
      const id = this.$route.params.id;
      getBook(id).then(res => {
        this.book = {
          ...res.data,
          rating: res.data.rating || 4.5 // 确保有默认评分
        };
        console.log("res: ", res)
      }).catch(error => {
        console.error("获取图书详情失败:", error);
        this.$message.error("获取图书详情失败");
      });
    },
    fetchRelatedBooks() {
      const categoryId = this.book.categoryId;
      if (categoryId) {
        getRelatedBooks(categoryId, this.$route.params.id)
          .then(res => {
            this.relatedBooks = res.data || [];
          })
          .catch(error => {
            console.error("获取相关图书失败:", error);
          });
      }
    },
    /**
     * 生成 7 天后的日期，并将时间设置为下午 18:00:00
     * 返回格式为 yyyy-MM-dd HH:mm:ss
     */
    getDueTimeAfter7DaysAtSixPM() {
      const now = new Date();
      const due = new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000);

      // 设置时间为 18:00:00
      due.setHours(18, 0, 0, 0);

      const year = due.getFullYear();
      const month = String(due.getMonth() + 1).padStart(2, '0');
      const day = String(due.getDate()).padStart(2, '0');
      const hours = String(due.getHours()).padStart(2, '0');
      const minutes = String(due.getMinutes()).padStart(2, '0');
      const seconds = String(due.getSeconds()).padStart(2, '0');

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
    }
    ,
    borrowBook() {
      const userid = this.$store.state.user.id;
      const username = this.$store.state.user.name;

      console.log("loginUserId: ", userid, username)
      if (userid == undefined || userid == '') {
        return;
      }

      if (this.book.stock <= 0) {
        this.$message.warning("当前图书已无库存");
        return;
      }
      let bookParam = {
        userId: userid,
        userName: username,
        bookId: this.book.id,
        bookName: this.book.title,
        status: 0,
        lateFee: 0,
        borrowTime: this.book.borrowTime + 1,
        categoryId: this.book.categoryId,
        categoryName: this.book.categoryName,
        imgCover: bookCoverUrl(this.book.coverUrl),
        dueTime: this.getDueTimeAfter7DaysAtSixPM()
      }
      // 借阅图书
      addLend(bookParam).then(res => {
        if (res.code === 200) {
          this.$message.warning("已经借阅图书: " + this.book.title + ",请于七天后归还！")
        }
      })
    }
    ,
    addToList() {
      this.$emit("add-to-list", this.book.id);
      this.$message.success("已加入书单");
    }
  },
  watch: {
    '$route.params.id':
      {
        handler(newId) {
          if (newId) {
            this.getBookById();
            this.fetchRelatedBooks();
          }
        }
        ,
        immediate: true
      }
  }
}
;
</script>
