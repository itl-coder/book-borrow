<template>
  <div class="book-list">
    <!-- 列表头部 -->
    <div class="list-header">
      <h3>热门图书</h3>
      <div class="sort-options">
        <div
          v-for="(label, key) in sortOptions"
          :key="key"
          :class="['sort-option', { active: sortType === key }]"
          @click="toggleSort(key)"
        >
          <i :class="sortIcons[key]" style="margin-right: 8px;"></i>
          {{ label }}
          <i
            v-if="sortType === key"
            :class="sortOrder === 'asc' ? 'el-icon-caret-top' : 'el-icon-caret-bottom'"
            style="margin-left: 4px;"
          />
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
        :page-sizes="[3, 10, 20, 30, 40]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 返回顶部 -->
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
      required: true
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
      sortType: 'title', // 当前排序字段
      sortOrder: 'asc',   // 排序顺序
      sortOptions: {
        title: '默认排序',
        publish_date: '按出版时间排序',
        is_recommended: '按推荐排序'
      },
      sortIcons: {
        title: 'el-icon-sort',
        publish_date: 'el-icon-calendar',
        is_recommended: 'el-icon-star-on'
      }
    }
  },
  methods: {
    toggleSort(type) {
      if (this.sortType === type) {
        // 点击相同字段，切换排序顺序
        this.sortOrder = this.sortOrder === 'asc' ? 'desc' : 'asc'
      } else {
        // 切换排序字段时，默认降序
        this.sortType = type
        this.sortOrder = 'desc'
      }
      // 通知父组件排序变更
      this.$emit('sort-change', {
        sortField: this.sortType,
        sortOrder: this.sortOrder
      })
    },
    handleSizeChange(size) {
      this.$emit('update:pageSize', size)
    },
    handleCurrentChange(page) {
      this.$emit('update:currentPage', page)
    }
  }
}
</script>

<style scoped>
.book-list {
  margin: 0 auto;
  padding: 20px;
  background: #fff;
  max-width: 1200px;
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
  margin-right: 4px;
}

.book-items {
  margin-bottom: 30px;
}

.pagination {
  text-align: center;
  margin-top: 30px;
}

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

.el-backtop {
  z-index: 9999;
}

@media (min-width: 1200px) {
  .el-col {
    padding: 10px;
  }
}
</style>
