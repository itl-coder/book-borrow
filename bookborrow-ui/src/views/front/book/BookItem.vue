<template>
  <div class="book-item" @click="goToDetail(book.id)">
    <div class="book-cover">
      <img :alt="book.title" :src="book.coverUrl"/>
      <span v-if="book.isRecommended" class="custom-tag recommended">推荐</span>
      <span v-if="book.stock <= 10" class="custom-tag discount">特价</span>
    </div>
    <div class="book-info">
      <div class="info-content">
        <div class="title-section">
          <h4 class="book-title">{{ book.title }}</h4>
          <div class="meta-line">
            <span class="author">{{ book.author }}</span>
            <span class="divider">·</span>
            <span class="publisher">{{ book.publisher }}</span>
          </div>
        </div>

        <div class="details-section">
          <div class="detail-row">
            <div class="detail-item">
              <i class="el-icon-date"></i>
              <span>{{ formatDate(book.publishDate) }}</span>
            </div>
            <div class="detail-item">
              <i class="el-icon-collection-tag"></i>
              <span>{{ book.categoryName }}</span>
            </div>
          </div>
          <div v-if="typeof book.stock === 'number'" class="detail-row">
            <div class="detail-item stock-item">
              <i class="el-icon-box"></i>
              <span>库存 {{ book.stock }} 本</span>
              <div :style="{ width: getStockPercentage(book.stock) }" class="stock-bar"></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'BookItem',
  props: {
    book: {
      type: Object,
      required: true
    }
  },
  methods: {
    goToDetail(id) {
      this.$router.push(`/book/${id}`);
    },
    formatDate(dateStr) {
      return dateStr ? dateStr.split(' ')[0] : '未知日期';
    },
    getStockPercentage(stock) {
      const maxStock = 100; // 假设最大库存为100
      const percentage = Math.min((stock / maxStock) * 100, 100);
      return `${percentage}%`;
    }
  }
};
</script>

<style lang="scss" scoped>
.book-item {
  width: 280px;
  background: #fff;
  border-radius: 12px;
  overflow: hidden;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.08);
  cursor: pointer;
  transition: all 0.3s cubic-bezier(0.25, 0.8, 0.25, 1);
  margin-bottom: 15px;
  position: relative;
  border: 1px solid #f0f0f0;

  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.12);
    border-color: #e0e0e0;

    .book-cover img {
      transform: scale(1.05);
    }

    .price-section .price-value {
      color: #ff3d00;
    }
  }
}

.book-cover {
  position: relative;
  height: 170px;
  background: linear-gradient(135deg, #f5f7fa, #e4e7eb);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 14px;
  overflow: hidden;
  border-bottom: 1px solid #f0f0f0;

  img {
    max-width: 100%;
    max-height: 100%;
    object-fit: contain;
    border-radius: 4px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transition: transform 0.4s cubic-bezier(0.25, 0.8, 0.25, 1);
  }
}

.custom-tag {
  position: absolute;
  top: 12px;
  right: 12px;
  padding: 4px 12px;
  font-size: 10px;
  font-weight: 600;
  color: #fff;
  border-radius: 12px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 1;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
  transform: rotate(30deg); // 添加旋转
  &.recommended {
    background: linear-gradient(135deg, #ff8a00, #ff3d00);
  }

  &.discount {
    background: linear-gradient(135deg, #e53935, #c62828);
  }
}

.book-info {
  padding: 18px;
  background: #fff;
  position: relative;
}

.info-content {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.title-section {
  .book-title {
    font-size: 15px;
    font-weight: 700;
    color: #222;
    line-height: 1.2;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  .meta-line {
    display: flex;
    align-items: center;
    font-size: 10px;
    color: #666;
    gap: 6px;
    margin-top: 4px;

    .author {
      font-weight: 500;
      color: #444;
    }

    .divider {
      color: #bbb;
      font-weight: bold;
    }

    .publisher {
      color: #666;
      white-space: nowrap;
      overflow: hidden;
      text-overflow: ellipsis;
      max-width: 120px;
    }
  }
}

.details-section {
  display: flex;
  flex-direction: column;
  gap: 10px;
  padding: 12px 0;
  border-top: 1px dashed #eee;
  border-bottom: 1px dashed #eee;

  .detail-row {
    display: flex;
    flex-wrap: wrap;
    gap: 8px;
  }

  .detail-item {
    display: flex;
    align-items: center;
    font-size: 13px;
    color: #555;
    background: #f8f9fa;
    padding: 6px 12px;
    border-radius: 14px;
    transition: all 0.2s ease;

    i {
      margin-right: 6px;
      font-size: 14px;
      color: #777;
    }

    &:hover {
      background: #f0f2f5;
      transform: translateY(-1px);
    }

    &.stock-item {
      position: relative;
      overflow: hidden;
      padding-right: 30px;
      background: #f0f7ff;
      color: #1890ff;

      i {
        color: #1890ff;
      }

      .stock-bar {
        position: absolute;
        bottom: 0;
        left: 0;
        height: 2px;
        background: linear-gradient(90deg, #1890ff, #69c0ff);
        transition: width 0.6s ease;
      }
    }
  }
}

.price-section {
  display: flex;
  align-items: flex-end;
  gap: 16px;
  position: relative;
  padding-top: 8px;

  .current-price, .original-price {
    display: flex;
    flex-direction: column-reverse;
    align-items: flex-start;

    .price-label {
      font-size: 12px;
      color: #999;
      margin-bottom: 2px;
    }

    .price-value {
      font-weight: 700;
      transition: color 0.3s ease;
    }
  }

  .current-price {
    .price-value {
      font-size: 22px;
      color: #ff4d4f;
    }
  }

  .original-price {
    .price-value {
      font-size: 15px;
      color: #999;
      text-decoration: line-through;
      position: relative;

      &::after {
        content: "";
        position: absolute;
        top: 50%;
        left: 0;
        width: 100%;
        height: 1px;
        background: #ff4d4f;
        transform: translateY(-50%);
      }
    }
  }

  .discount-badge {
    position: absolute;
    right: 0;
    bottom: 0;
    background: linear-gradient(135deg, #ff8a00, #ff3d00);
    color: white;
    padding: 2px 10px;
    border-radius: 10px;
    font-size: 12px;
    font-weight: 600;
    box-shadow: 0 2px 6px rgba(255, 61, 0, 0.2);
  }
}

/* 响应式调整 */
@media (max-width: 768px) {
  .book-item {
    width: 100%;
    max-width: 320px;
  }

  .book-cover {
    height: 180px;
  }

  .book-info {
    padding: 14px;
  }

  .price-section {
    flex-wrap: wrap;
    gap: 12px;

    .current-price .price-value {
      font-size: 20px;
    }
  }
}
</style>
