<template>
  <div class="book-list">
    <!-- 列表头部 -->
    <div class="list-header">
      <h3>热门图书</h3>
      <div class="sort-options">
        <div :class="{'active': sortType === 'default'}" class="sort-option" @click="selectSort('default')">
          <i class="el-icon-sort" style="margin-right: 8px;"></i> 默认排序
        </div>
        <div :class="{'active': sortType === 'publish-date'}" class="sort-option" @click="selectSort('publish-date')">
          <i class="el-icon-calendar" style="margin-right: 8px;"></i> 按出版时间排序
        </div>
        <div :class="{'active': sortType === 'borrowed-count'}" class="sort-option"
             @click="selectSort('borrowed-count')">
          <i class="el-icon-download" style="margin-right: 8px;"></i> 按借阅次数排序
        </div>
        <div :class="{'active': sortType === 'recommended'}" class="sort-option" @click="selectSort('recommended')">
          <i class="el-icon-star-on" style="margin-right: 8px;"></i> 按推荐排序
        </div>
      </div>
    </div>

    <!-- 图书项展示 -->
    <div class="book-items">
      <el-row :gutter="20">
        <el-col v-for="book in books" :key="book.id" :span="8">
          <book-item :book="book"/>
        </el-col>
      </el-row>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        :current-page="pageNum"
        :page-size="pageSize"
        :page-sizes="[3,10, 20, 30, 40]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange">
      </el-pagination>
    </div>

    <!-- 返回顶部按钮 -->
    <el-backtop :bottom="50" :right="50" :visibility-height="200"/>
  </div>
</template>

<script>
import BookItem from './BookItem'

export default {
  name: 'BookList',
  components: {
    BookItem
  },
  props: {
    books: {
      type: Array,
      required: true
    },
    pageNum: {
      type: Number,
      required: true,
    },
    pageSize: {
      type: Number,
      required: true
    },
    total: {
      type: Number,
      required: true
    }
  },
  data() {
    return {
      sortType: 'default', // 默认排序
      sortOptions: { // 排序选项
        default: '默认排序',
        'publish-date': '按出版时间排序',
        'borrowed-count': '按借阅次数排序',
        'recommended': '按推荐排序'
      }
    }
  },
  methods: {
    handleSizeChange(size) {
      // 子组件发送 pageSize 的变化到父组件
      this.$emit('update:pageSize', size);
    },
    handleCurrentChange(page) {
      // 子组件发送 currentPage 的变化到父组件
      this.$emit('update:currentPage', page);
    },
    selectSort(value) {
      this.sortType = value
    },
  }
}
</script>

<style scoped>
.book-list {
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  max-width: 1200px; /* 限制最大宽度 */
  border-radius: 10px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.list-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #f0f0f0;
}

.list-header h3 {
  font-size: 20px;
  font-weight: bold;
  color: #333;
  margin: 0;
}

.sort-options {
  display: flex;
  gap: 20px;
}

.sort-option {
  display: flex;
  align-items: center;
  font-size: 14px;
  color: #333;
  cursor: pointer;
  transition: all 0.3s ease;
}

.sort-option:hover {
  color: #ff6700;
}

.sort-option.active {
  color: #ff6700;
  font-weight: bold;
}

.sort-option i {
  margin-right: 8px;
}

.book-items {
  margin-bottom: 30px;
}

.pagination {
  text-align: center;
  margin-top: 30px;
}

/* 针对图书项间距调整 */
.el-row {
  margin-bottom: 20px;
}

.el-col {
  display: flex;
  justify-content: center;
  align-items: center;
}

.book-item {
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 2px 6px rgba(0, 0, 0, 0.05);
  transition: all 0.3s ease;
  padding: 10px;
  cursor: pointer;
}

.book-item:hover {
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  transform: translateY(-5px);
}

.book-item img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  border-radius: 8px;
  margin-bottom: 15px;
}

.book-info {
  text-align: center;
}

.book-info h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 5px;
}

.book-info p {
  font-size: 14px;
  color: #777;
}

/* 返回顶部按钮 */
.el-backtop {
  z-index: 9999; /* 保证它在最上层 */
}

@media (min-width: 1200px) {
  .el-col {
    padding: 10px;
  }
}
</style>
