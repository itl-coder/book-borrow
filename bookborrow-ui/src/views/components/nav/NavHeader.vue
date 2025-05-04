<template>
  <div>
    <!-- 导航栏 -->
    <el-menu
      ref="navbar"
      :class="['nav-bar', { 'is-sticky': isSticky }]"
      mode="horizontal"
      :default-active="activeIndex"
      background-color="transparent"
      text-color="#fff"
      active-text-color="#ffd04b"
      @select="handleSelect"
    >
      <el-menu-item
        v-for="item in navItems"
        :key="item.path"
        :index="item.path"
      >
        <i :class="['menu-icon', item.icon]"></i>
        {{ item.title }}
      </el-menu-item>
    </el-menu>

    <!-- 占位元素 -->
    <div v-show="isSticky" class="nav-placeholder" :style="{ height: navbarHeight + 'px' }"></div>
  </div>
</template>

<script>
export default {
  name: 'StickyNav',
  data() {
    return {
      isSticky: false,
      navbarHeight: 0,
      activeIndex: '/',
      navItems: [
        { path: '/', title: '首页', icon: 'el-icon-s-home' },
        { path: '/about', title: '关于我们', icon: 'el-icon-info' },
        { path: '/products', title: '产品中心', icon: 'el-icon-goods' },
        { path: '/news', title: '新闻动态', icon: 'el-icon-news' },
        { path: '/contact', title: '联系我们', icon: 'el-icon-phone-outline' }
      ]
    };
  },
  mounted() {
    this.activeIndex = this.$route.path;
    this.navbarHeight = this.$refs.navbar.$el.offsetHeight;
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },
  watch: {
    $route(to) {
      this.activeIndex = to.path;
    }
  },
  methods: {
    handleSelect(index) {
      this.$router.push(index);
    },
    handleScroll() {
      const scrollTop = window.pageYOffset || document.documentElement.scrollTop;
      this.isSticky = scrollTop > this.navbarHeight;
    }
  }
};
</script>

<style scoped lang="scss">
.nav-bar {
  width: 100%;
  background: linear-gradient(to right, rgba(40, 60, 90, 0.95), rgba(60, 90, 130, 0.95));
  backdrop-filter: blur(10px);
  transition: all 0.3s ease-in-out;

  &.is-sticky {
    position: fixed;
    top: 0;
    left: 0;
    z-index: 1000;
    box-shadow: 0 4px 20px rgba(0, 0, 0, 0.3);
    animation: slideDown 0.3s ease-out;
  }

  ::v-deep .el-menu-item {
    font-size: 16px;
    padding: 0 25px;
    height: 60px;
    line-height: 60px;
    transition: all 0.3s ease;

    &:hover {
      background-color: rgba(255, 255, 255, 0.1) !important;
      color: #ffd04b !important;
    }

    .menu-icon {
      margin-right: 8px;
      font-size: 18px;
    }
  }
}

.nav-placeholder {
  width: 100%;
}

@keyframes slideDown {
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>
