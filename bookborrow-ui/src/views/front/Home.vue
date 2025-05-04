<template>
  <div class="book-home">
    <!-- 顶部导航 -->
    <book-header/>
    <div class="main-container">
      <!-- 轮播图 -->
      <book-slider :slides="slides"/>
      <div class="content-wrapper banner">
        <book-category :categories="categories"/>
        <book-list :books="books"/>
      </div>
    </div>
    <y-footer/>
  </div>
</template>

<script>
import BookHeader from '@/views/front/book/BookHeader'
import BookSlider from '@/views/front/book/BookSlider'
import BookList from '@/views/front/book/BookList'
import BookCategory from '@/views/front/book/BookCategory'

import YFooter from "@/views/components/footer/YFooter.vue";
export default {
  name: 'Home',
  components: {
    BookHeader,
    BookSlider,
    BookList,
    BookCategory,
    YFooter
  },
  data() {
    return {
      slides: [
        {
          id: 1,
          title: '百年孤独',
          subtitle: '魔幻现实主义经典小说',
          image: 'http://localhost/dev-api/profile/upload/2025/05/03/bg1_20250503195032A001.jpg',
          link: '/book/1'
        },
        {
          id: 2,
          title: '三体全集',
          subtitle: '中国硬核科幻代表作',
          image: 'https://example.com/covers/santi.jpg',
          link: '/book/7'
        }
      ],
      categories: [
        {id: 1, name: '文学', count: 2},
        {id: 2, name: '历史', count: 2},
        {id: 3, name: '科技', count: 2},
        {id: 4, name: '哲学', count: 1},
        {id: 5, name: '计算机', count: 1},
        {id: 6, name: '小说', count: 2}
      ],
      books: [
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "http://localhost/dev-api/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "http://localhost/dev-api/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        }, {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-03 19:53:33",
          "remark": null,
          "id": 1,
          "title": "百年孤独",
          "author": "加西亚·马尔克斯",
          "publisher": "南海出版公司",
          "publishDate": "2011-06-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 59,
          "stock": 50,
          "borrowStatus": 0,
          "description": "魔幻现实主义经典小说",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "/profile/upload/2025/05/03/bg1_20250503195032A001.jpg",
          "isDeleted": 0
        }

        // 其他书籍数据省略
      ]
    }
  },
  created() {
    // 可以添加API请求获取真实数据
  }
}
</script>

<style lang="scss" scoped>
.banner {
  width: 1200px;
  margin: 0 auto;
}

.content-wrapper {
  display: flex;
  justify-content: space-between; /* 两个组件分布到容器两边 */
  gap: 20px; /* 组件之间的间距 */
  margin-top: 20px;
}

.book-category {
  width: 240px;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 15px;
}

.book-list {
  flex: 1;
  background-color: #fff;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
  padding: 20px;
}

.book-item {
  background-color: #fafafa;
  border-radius: 8px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
  padding: 15px;
  transition: all 0.3s ease;

  &:hover {
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    transform: translateY(-5px); /* 轻微上浮效果 */
  }
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

.book-category .category-item {
  margin-bottom: 10px;
  padding: 8px;
  background-color: #f9f9f9;
  border-radius: 6px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.book-category .category-item:hover {
  background-color: #f5f5f5;
}

.book-category .category-item .category-name {
  font-size: 16px;
  font-weight: 600;
  color: #333;
}

.book-category .category-item .category-count {
  font-size: 14px;
  color: #ff6700;
  background-color: #fff;
  border: 1px solid #ff6700;
  border-radius: 12px;
  padding: 2px 8px;
}


</style>
