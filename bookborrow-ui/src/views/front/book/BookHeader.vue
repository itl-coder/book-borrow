<template>
  <div class="book-header">
    <div class="header-container">
      <div class="logo">馆藏图书</div>
      <el-menu
        :default-active="activeIndex"
        class="menu"
        mode="horizontal"
        @select="handleSelect"
      >
        <el-menu-item index="1">首页</el-menu-item>
        <el-menu-item index="2">文学小说</el-menu-item>
        <el-menu-item index="3">科技科普</el-menu-item>
        <el-menu-item index="4">历史哲学</el-menu-item>
        <el-menu-item index="5">计算机</el-menu-item>
        <el-menu-item index="6">特价专区</el-menu-item>
      </el-menu>
      <div class="search-box" v-if="showFlag">
        <el-input
          v-model="searchText"
          placeholder="请输入图书名"
          @keyup.enter.native="handleSearch"
        >
          <el-button slot="append" icon="el-icon-search" @click="handleSearch"></el-button>
        </el-input>
      </div>
      <div class="user-actions">
        <el-button icon="el-icon-user" type="text"></el-button>
        <el-button icon="el-icon-shopping-cart" type="text"></el-button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BookHeader',
  props: {
    showFlag: {
      type: Boolean,
      require: true
    }
  },
  data() {
    return {
      activeIndex: '1',
      searchText: ''
    }
  },
  methods: {
    handleSelect(key) {
      console.log('select menu:', key)
    },
    handleSearch() {
      console.log('search:', this.searchText)
      this.$emit('search-book', this.searchText)
    }
  }
}
</script>

<style lang="scss" scoped>
.book-header {
  background-color: #ffffff;
  position: sticky;
  top: 0;
  z-index: 1000;
  height: 70px;
  transition: all 0.3s ease;
  margin-bottom: 10px;
}

.header-container {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 70px;
  padding: 0 20px;
}

.logo {
  font-size: 28px;
  font-weight: bold;
  color: #ff6700;
  white-space: nowrap;
}

.el-menu {
  flex: 1;
  background-color: transparent;
  border-bottom: none;
}

.el-menu-item {
  font-size: 16px;
  color: #333;
  padding: 0 20px;
}

.el-menu-item:hover {
  color: #ff6700;
  background-color: transparent;
}

.search-box {
  width: 300px;
}

.el-input__inner {
  border-radius: 20px;
  height: 36px;
}

.el-input-group__append {
  background-color: #ff6700;
  color: white;
}

.el-button--text {
  font-size: 18px;
  color: #666;
  margin-left: 15px;
}

.el-button--text:hover {
  color: #ff6700;
}

.user-actions {
  display: flex;
  align-items: center;
}
</style>
