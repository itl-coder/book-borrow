<template>
  <div class="book-header">
    <div class="header-container">
      <div class="logo">馆藏图书</div>
      <el-menu
        :default-active="activeIndex"
        class="menu"
        mode="horizontal"
        router
        @select="handleSelect"
      >
        <el-menu-item index="/front/index">首页</el-menu-item>
        <el-menu-item index="/seat">自习室</el-menu-item>
        <el-menu-item index="3">科技科普</el-menu-item>
        <el-menu-item index="4">历史哲学</el-menu-item>
        <el-menu-item index="5">计算机</el-menu-item>
        <el-menu-item index="/index" @click="reloadPage">进入后台</el-menu-item>
      </el-menu>
      <div v-if="showFlag" class="search-box">
        <el-input
          v-model="searchText"
          placeholder="请输入图书名"
          @keyup.enter.native="handleSearch"
        >
          <el-button
            slot="append"
            icon="el-icon-search"
            @click="handleSearch"
          ></el-button>
        </el-input>
      </div>
      <div class="user-actions">
        <template
          v-if="loginInfo.nickName == undefined || loginInfo.nickName == ''"
        >
          <el-dropdown @command="handleCommand">
            <span class="el-dropdown-link">
              <el-avatar
                :size="40"
                :src="loginInfo.avatar | bookCoverUrl"
              ></el-avatar>
              <span style="margin-left: 8px">{{ loginInfo.nickName }}</span>
            </span>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="myAppointment"
              >我的预约
              </el-dropdown-item
              >
              <el-dropdown-item command="borrowHistory"
              >借阅历史
              </el-dropdown-item
              >
              <el-dropdown-item command="logout" divided
              >退出登录
              </el-dropdown-item
              >
            </el-dropdown-menu>
          </el-dropdown>
        </template>
        <template v-else>
          <span class="login-text" @click="goToLogin">登录</span>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "BookHeader",
  props: {
    showFlag: {
      type: Boolean,
      require: true,
    },
  },
  data() {
    return {
      loginInfo: {
        userName: "",
        avatar: "",
        nickName: "",
      },
      activeIndex: "/front/index",
      searchText: "",
    };
  },
  created() {
    this.updateActiveIndex(this.$route.path)
    this.getLoginUserName();
  },
  watch: {
    '$route'(to) {
      this.updateActiveIndex(to.path)
    }
  },
  methods: {
    reloadPage() {
      window.location.href = '/index'; // 强制跳转并刷新整个页面
    },
    goToLogin() {
      // 这里根据你的路由配置跳转到登录页
      this.$router.push("/login");
    },
    getLoginUserName() {
      this.$store.dispatch("GetInfo").then((resp) => {
        this.userName = resp.user.userName;
        console.log("resp: ", resp);
      });
    },
    handleCommand(command) {
      if (command == 'myAppointment') {
        this.$router.push("/seat")
      }
      if (command == 'borrowHistory') {
        this.$router.push(`/borrow`)
      }
    },
    updateActiveIndex(path) {
      // 根据当前路由路径设置激活菜单项
      this.activeIndex = path
    },
    handleSelect(index) {
      this.activeIndex = index
    },
    handleSearch() {
      console.log("search:", this.searchText);
      this.$emit("search-book", this.searchText);
    },
  },
};
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
  cursor: pointer;
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
  margin-left: 10px;
}

.el-dropdown-link {
  cursor: pointer;
  color: #409eff;
}

.el-icon-arrow-down {
  font-size: 12px;
}

.login-text {
  cursor: pointer;
  color: #409eff;
}

.login-text:hover {
  text-decoration: underline;
}
</style>
