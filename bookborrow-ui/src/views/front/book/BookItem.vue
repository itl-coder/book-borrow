<template>
  <div class="book-item" @click="goToDetail(book.id)">
    <div class="book-cover">
      <img :alt="book.title" :src="book.coverUrl"/>
      <div v-if="book.isRecommended" class="book-tag hot">推荐</div>
      <div v-if="book.stock <= 10" class="book-tag discount">特价</div>
    </div>
    <div class="book-info">
      <h4 class="book-title">{{ book.title }}</h4>
      <p class="book-author">{{ book.author }}</p>
      <p class="book-publisher">{{ book.publisher }}</p>
      <p class="book-publish-date">出版日期: {{ book.publishDate }}</p>
      <p class="book-category">类别: {{ book.categoryName }}</p>
      <div class="book-price">
        <span class="current-price">¥{{ book.price }}</span>
        <span v-if="book.originalPrice" class="original-price">¥{{ book.originalPrice }}</span>
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
      console.log('go to book detail:', id);
      // 这里可以导航到书籍详情页
    }
  }
}
</script>

<style scoped>
.book-item {
  background: #fff;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: transform 0.3s, box-shadow 0.3s;
  width: 250px;
  margin: 0 auto;
  margin-bottom: 20px;
}

.book-item:hover {
  transform: translateY(-5px);
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.1);
}

.book-cover {
  position: relative;
  height: 120px; /* 调整为更合适的高度 */
  overflow: hidden;
}

.book-cover img {
  width: 100%;
  height: 100%;
  object-fit: cover; /* 确保图片填满容器并保持比例 */
}

.book-tag {
  position: absolute;
  top: 10px;
  right: 10px;
  padding: 5px 10px;
  font-size: 12px;
  color: white;
  border-radius: 3px;
  background-color: #ff6700;
  font-weight: bold;
}

.book-tag.hot {
  background-color: #ff6700;
}

.book-tag.discount {
  background-color: #f56c6c;
}

.book-info {
  padding: 15px;
  font-family: Arial, sans-serif;
}

.book-title {
  font-size: 18px;
  color: #333;
  font-weight: bold;
  margin-bottom: 10px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.book-author {
  font-size: 14px;
  color: #666;
  margin-bottom: 5px;
}

.book-publisher {
  font-size: 14px;
  color: #777;
  margin-bottom: 5px;
}

.book-publish-date,
.book-category {
  font-size: 12px;
  color: #999;
  margin-bottom: 5px;
}

.book-price {
  display: flex;
  align-items: center;
  margin-top: 15px;
}

.current-price {
  font-size: 18px;
  color: #ff6700;
  font-weight: bold;
}

.original-price {
  font-size: 12px;
  color: #999;
  text-decoration: line-through;
  margin-left: 8px;
}

/* Small screen responsive design */
@media (max-width: 768px) {
  .book-item {
    width: 100%;
  }

  .book-cover {
    height: 180px; /* 在小屏幕上，调整封面图高度 */
  }
}

</style>
