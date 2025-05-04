<template>
  <div class="book-category banner">
    <div class="category-header">
      <h3 class="category-title">
        <i class="el-icon-menu"></i>
        图书分类
      </h3>
    </div>

    <el-scrollbar class="category-scrollbar">
      <el-menu
        :default-active="activeCategory"
        class="category-menu"
        @select="handleSelect"
      >
        <el-menu-item
          v-for="category in categories"
          :key="category.categoryId"
          :index="category.categoryId.toString()"
          class="category-item"
        >
          <template #title>
            <span class="category-name">{{ category.categoryName }}</span>
            <el-tag
              :type="activeCategory === category.categoryId.toString() ? 'primary' : 'info'"
              class="category-count"
              size="mini"
            >
              {{ category.bookCount }}
            </el-tag>
          </template>
        </el-menu-item>
      </el-menu>
    </el-scrollbar>
  </div>
</template>

<script>
export default {
  name: 'BookCategory',
  props: {
    categories: {
      type: Array,
      required: true,
      default: () => []
    },
    height: {
      type: String,
      default: '480px'
    }
  },
  data() {
    return {
      activeCategory: '0',
    }
  },
  // computed: {
  //   scrollHeight() {
  //     return `calc(${this.height} - 60px)`
  //   }
  // },
  methods: {
    handleSelect(index) {
      this.activeCategory = index
      this.$emit('category-change', index)
    }
  }
}
</script>

<style lang="scss" scoped>
.book-category {
  width: 240px;
  height: 480px; // 设置固定高度
  position: fixed; // 固定定位
  top: 100px; // 距离顶部 100px，可根据需要调整
  left: 20px; // 固定在页面左侧，可改为 right: 20px 定位右侧
  z-index: 1000; // 保证浮在上层
  background: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.05);
  overflow: hidden;
  transition: all 0.3s;

  &:hover {
    box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
  }
}

.category-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  height: 60px;
  border-bottom: 1px solid #f0f0f0;
  background-color: #fafafa;
}

.category-title {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;

  i {
    margin-right: 8px;
    color: #ff6700;
  }
}

.category-scrollbar {
  ::v-deep .el-scrollbar__wrap {
    overflow-x: hidden;
  }
}

.category-menu {
  border-right: none;

  &:not(.el-menu--collapse) {
    width: 240px;
  }
}

.category-item {
  height: 50px;
  line-height: 50px;
  transition: all 0.3s;

  &:hover {
    background-color: #fff9f5;

    .category-name {
      color: #ff6700;
    }
  }

  &.is-active {
    background-color: #fff0e6;

    .category-name {
      color: #ff6700;
      font-weight: 500;
    }
  }
}

.category-name {
  display: inline-block;
  width: 140px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  vertical-align: middle;
  transition: color 0.3s;
}

.category-count {
  float: right;
  margin-top: 12px;
  margin-right: 10px;
  height: 20px;
  line-height: 20px;
  border-radius: 10px;
  min-width: 30px;
  text-align: center;
  padding: 0 5px;
}

/* 响应式调整 */
@media (max-width: 768px) {
  .book-category {
    width: 180px;
  }

  .category-name {
    width: 100px;
  }
}
</style>
