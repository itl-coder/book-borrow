<template>
  <div class="book-slider banner">
    <el-carousel :interval="5000" arrow="always" height="380px" indicator-position="outside">
      <el-carousel-item v-for="slide in slides" :key="slide.id">
        <div
          :style="{ backgroundImage: `linear-gradient(to right, rgba(0,0,0,0.7) 0%, rgba(0,0,0,0.3) 100%), url(${slide.image})` }"
          class="slide-content"
        >
          <div class="slide-text">
            <h2 class="slide-title">{{ slide.title }}</h2>
            <p class="slide-subtitle">{{ slide.subtitle }}</p>
            <el-button
              class="detail-btn"
              type="primary"
              @click="goToDetail(slide.link)"
            >
              查看详情
              <i class="el-icon-arrow-right"></i>
            </el-button>
          </div>
        </div>
      </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script>
export default {
  name: 'BookSlider',
  props: {
    slides: {
      type: Array,
      required: true,
      default: () => []
    }
  },
  methods: {
    goToDetail(link) {
      this.$router.push(link)
    }
  }
}
</script>

<style lang="scss" scoped>
.book-slider {
  width: 100%; /* 让容器宽度适应父元素宽度 */
  max-width: 1200px; /* 最大宽度为1200px */
  margin: 10px auto; /* 确保居中对齐 */
  border-radius: 3px;

  .el-carousel__arrow {
    background-color: rgba(255, 255, 255, 0.2);
    backdrop-filter: blur(5px);
  }

  .el-carousel__indicator .el-carousel__button {
    width: 30px;
    height: 4px;
    border-radius: 2px;
    background-color: rgba(255, 255, 255, 0.5);
  }

  .slide-content {
    height: 100%;
    background-size: cover;
    background-position: center;
    position: relative;
    display: flex;
    align-items: center;
    padding: 0 80px;
  }

  .slide-text {
    max-width: 50%;
    color: white;
    transform: translateY(-10%);
  }

  .slide-title {
    font-size: 42px;
    font-weight: 700;
    line-height: 1.2;
    margin-bottom: 20px;
  }

  .slide-subtitle {
    font-size: 18px;
    margin-bottom: 30px;
  }

  .detail-btn {
    background-color: #ff6700;
    border-radius: 30px;
    padding: 12px 30px;
    box-shadow: 0 4px 15px rgba(255, 103, 0, 0.3);
  }
}

</style>
