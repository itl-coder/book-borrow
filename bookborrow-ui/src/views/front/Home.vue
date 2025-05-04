<template>
  <div class="book-home">
    <!-- 顶部导航 -->
    <book-header/>
    <div class="main-container">
      <!-- 轮播图 -->
      <book-slider :slides="slides"/>

      <!-- 主内容区域 -->
      <div class="content-wrapper banner">
        <!-- 吸顶分类列表 -->
        <book-category :categories="categories"/>
        <!-- 图书列表 -->
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
import YFooter from "@/views/components/footer/YFooter.vue"
import { categoryBookList } from "@/api/bookinfo/book"

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
      categories: [],
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
          "updateTime": "2025-05-03 19:54:10",
          "remark": null,
          "id": 2,
          "title": "活着",
          "author": "余华",
          "publisher": "作家出版社",
          "publishDate": "2012-05-01",
          "categoryId": 6,
          "categoryName": "小说",
          "price": 39,
          "stock": 60,
          "borrowStatus": 1,
          "description": "普通人命运沉浮的故事",
          "isPublished": 1,
          "isRecommended": 0,
          "coverUrl": "/profile/upload/2025/05/03/bg2_20250503195408A002.png",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 3,
          "title": "时间简史",
          "author": "斯蒂芬·霍金",
          "publisher": "湖南科技出版社",
          "publishDate": "2001-03-01",
          "categoryId": 3,
          "categoryName": "科技",
          "price": 68,
          "stock": 30,
          "borrowStatus": 0,
          "description": "探索宇宙奥秘的科普经典",
          "isPublished": 1,
          "isRecommended": 1,
          "coverUrl": "https://example.com/covers/time.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 4,
          "title": "苏菲的世界",
          "author": "乔斯坦·贾德",
          "publisher": "作家出版社",
          "publishDate": "2015-07-01",
          "categoryId": 4,
          "categoryName": "哲学",
          "price": 49,
          "stock": 40,
          "borrowStatus": 0,
          "description": "哲学入门读物",
          "isPublished": 1,
          "isRecommended": 0,
          "coverUrl": "https://example.com/covers/sophie.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 5,
          "title": "围城",
          "author": "钱钟书",
          "publisher": "人民文学出版社",
          "publishDate": "1991-08-01",
          "categoryId": 1,
          "categoryName": "文学",
          "price": 45,
          "stock": 35,
          "borrowStatus": 1,
          "description": "讽刺现实的经典作品",
          "isPublished": 1,
          "isRecommended": 0,
          "coverUrl": "https://example.com/covers/weicheng.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 6,
          "title": "挪威的森林",
          "author": "村上春树",
          "publisher": "上海译文出版社",
          "publishDate": "2001-10-01",
          "categoryId": 6,
          "categoryName": "小说",
          "price": 48,
          "stock": 20,
          "borrowStatus": 0,
          "description": "青春与孤独的交错",
          "isPublished": 1,
          "isRecommended": 1,
          "coverUrl": "https://example.com/covers/nwsl.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 7,
          "title": "三体",
          "author": "刘慈欣",
          "publisher": "重庆出版社",
          "publishDate": "2008-01-01",
          "categoryId": 3,
          "categoryName": "科技",
          "price": 69,
          "stock": 25,
          "borrowStatus": 1,
          "description": "中国硬核科幻的代表作",
          "isPublished": 1,
          "isRecommended": 1,
          "coverUrl": "https://example.com/covers/santi.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 8,
          "title": "史记",
          "author": "司马迁",
          "publisher": "中华书局",
          "publishDate": "1959-10-01",
          "categoryId": 2,
          "categoryName": "历史",
          "price": 120,
          "stock": 10,
          "borrowStatus": 0,
          "description": "中国第一部纪传体通史",
          "isPublished": 1,
          "isRecommended": 0,
          "coverUrl": "https://example.com/covers/shiji.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 9,
          "title": "人类简史",
          "author": "尤瓦尔·赫拉利",
          "publisher": "中信出版社",
          "publishDate": "2014-11-01",
          "categoryId": 2,
          "categoryName": "历史",
          "price": 88,
          "stock": 15,
          "borrowStatus": 0,
          "description": "从认知革命到人工智能",
          "isPublished": 1,
          "isRecommended": 1,
          "coverUrl": "https://example.com/covers/renlei.jpg",
          "isDeleted": 0
        },
        {
          "createBy": null,
          "createTime": "2025-05-02 04:32:25",
          "updateBy": null,
          "updateTime": "2025-05-02 04:32:25",
          "remark": null,
          "id": 10,
          "title": "深入理解计算机系统",
          "author": "Randal E. Bryant",
          "publisher": "机械工业出版社",
          "publishDate": "2016-06-01",
          "categoryId": 9,
          "categoryName": "计算机",
          "price": 99,
          "stock": 12,
          "borrowStatus": 0,
          "description": "程序员必读的底层原理书籍",
          "isPublished": 0,
          "isRecommended": 1,
          "coverUrl": "https://example.com/covers/csapp.jpg",
          "isDeleted": 0
        }
      ] // 示例中省略具体内容
    }
  },
  methods: {
    getBookCategory() {
      categoryBookList().then(response => {
        this.categories = response.data
      })
    }
  },
  created() {
    this.getBookCategory()
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
  justify-content: space-between;
  gap: 20px;
  margin-top: 20px;
  align-items: flex-start;
}

.book-category {
  width: 240px;
  height: 480px;
  position: sticky;
  top: 150px; /* ✅ 设为 header + slider 总高度 */
  align-self: flex-start;
  background-color: #fff;
  border-radius: 8px;
  padding: 15px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  z-index: 10;
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
    transform: translateY(-5px);
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
</style>
