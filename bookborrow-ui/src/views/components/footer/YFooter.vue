<template>
  <div class="footer-container">
    <div class="footer-content">
      <!-- 顶部链接区域 -->
      <div class="footer-links">
        <el-link :underline="false" @click.prevent>关于我们</el-link>
        <el-divider direction="vertical"></el-divider>
        <el-link :underline="false" @click.prevent>联系我们</el-link>
        <el-divider direction="vertical"></el-divider>
        <el-link :underline="false" @click.prevent>帮助中心</el-link>
        <el-divider direction="vertical"></el-divider>
        <el-link :underline="false" @click.prevent>隐私政策</el-link>
      </div>

      <!-- 版权信息 -->
      <div class="copyright-info">
        <p>
          <i class="el-icon-office-building"></i>
          © 2025 阜新市图书馆借阅管理系统 版权所有
        </p>
        <p>
          <i class="el-icon-link"></i>
          <a href="http://www.library.edu.cn" target="_blank">www.library.edu.cn</a>
          <el-divider direction="vertical"></el-divider>
          <i class="el-icon-phone"></i>
          服务热线：400-123-4567
        </p>
        <p>
          <i class="el-icon-document"></i>
          京ICP备12345678号-1
          <el-divider direction="vertical"></el-divider>
          京公网安备11010802023456号
        </p>
      </div>

      <!-- 社交媒体图标 -->
      <div class="social-media">
        <el-link
          v-for="social in socialMedias"
          :key="social.name"
          :href="social.link"
          :underline="false"
          class="social-icon"
          target="_blank"
        >
          <i :class="social.icon" :title="social.name"></i>
        </el-link>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooterCopyright',
  data() {
    return {
      // 社交媒体图标与链接
      socialMedias: [
        { name: '微信公众号', icon: 'el-icon-chat-dot-round', link: '#' },
        { name: '微博', icon: 'el-icon-star-off', link: '#' },
        { name: '抖音', icon: 'el-icon-video-camera', link: '#' },
        { name: 'B站', icon: 'el-icon-film', link: '#' }
      ]
    }
  }
}
</script>

<style scoped lang="scss">
.footer-container {
  background: linear-gradient(135deg, #2c3e50, #4a6491);
  color: #fff;
  padding: 30px 0;
  margin-top: 40px;
  box-shadow: 0 -2px 10px rgba(0, 0, 0, 0.1);

  .footer-content {
    max-width: 1200px;
    margin: 0 auto;
    text-align: center;
  }

  .footer-links {
    margin-bottom: 20px;

    .el-link {
      color: #fff;
      font-size: 14px;
      margin: 0 10px;
      transition: color 0.3s;

      &:hover {
        color: #ffd04b;
      }
    }

    .el-divider {
      background-color: rgba(255, 255, 255, 0.3);
    }
  }

  .copyright-info {
    font-size: 13px;
    line-height: 1.8;
    margin-bottom: 20px;

    p {
      margin: 5px 0;
    }

    i {
      margin-right: 5px;
    }

    a {
      color: #fff;
      text-decoration: none;
      transition: color 0.3s;

      &:hover {
        color: #ffd04b;
      }
    }

    .el-divider {
      background-color: rgba(255, 255, 255, 0.3);
    }
  }

  .social-media {
    .social-icon {
      color: #fff;
      font-size: 20px;
      margin: 0 12px;
      transition: all 0.3s;

      &:hover {
        color: #ffd04b;
        transform: translateY(-3px);
      }
    }
  }

  @media (max-width: 768px) {
    padding: 20px 0;

    .footer-links {
      margin-bottom: 15px;

      .el-link {
        display: block;
        margin: 10px 0;
      }

      .el-divider {
        display: none;
      }
    }

    .copyright-info {
      font-size: 12px;
    }

    .social-media {
      .social-icon {
        margin: 0 8px;
        font-size: 18px;
      }
    }
  }
}
</style>
