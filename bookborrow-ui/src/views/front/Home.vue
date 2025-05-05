<template>
  <div class="book-home">
    <!-- 顶部导航 -->
    <book-header :showFlag="showFlag" @search-book="searchBook" />
    <div class="book-main-container">
      <!-- 轮播图 -->
      <book-slider :slides="slides" />

      <!-- 主内容区域 -->
      <div class="content-wrapper banner">
        <!-- 左侧分类栏 -->
        <div class="left-side">
          <book-category
            :categories="categories"
            @category-change="categoryChangeHandle"
          />
        </div>

        <!-- 右侧图书列表 -->
        <div class="right-side">
          <book-list
            :books="books"
            :pageNum="query.pageNum"
            :pageSize="query.pageSize"
            :total="query.total"
            @sort-change="sortBookInfo"
            @update:currentPage="handleCurrentPageChange"
            @update:pageSize="handlePageSizeChange"
          />
        </div>
      </div>
    </div>
    <y-footer />
  </div>
</template>

<script>
import BookHeader from "@/views/front/book/BookHeader";
import BookSlider from "@/views/front/book/BookSlider";
import BookList from "@/views/front/book/BookList";
import BookCategory from "@/views/front/book/BookCategory";
import YFooter from "@/views/components/footer/YFooter.vue";
import { categoryBookList, listFrontBook } from "@/api/bookinfo/book";
import { frontListCarousel } from "@/api/bookinfo/carousel";

export default {
  name: "Home",
  components: {
    BookHeader,
    BookSlider,
    BookList,
    BookCategory,
    YFooter,
  },
  data() {
    return {
      showFlag: true, // 是否显示搜索
      query: {
        sortField: "",
        sortOrder: "",
        categoryId: "", // 分类id
        title: "", // 图书名称
        pageNum: 1, // 当前页
        pageSize: 3, // 每页大小
        total: 0, // 总条目数
      },
      slides: [],
      categories: [],
      books: [],
    };
  },
  methods: {
    getSlide() {
      frontListCarousel().then((res) => {
        console.log("frontListCarousel: ", res);
        this.slides = res.data;
      });
    },
    sortBookInfo(sortParam) {
      this.query.sortField = sortParam.sortField;
      this.query.sortOrder = sortParam.sortOrder;
      this.getBookList();
    },
    searchBook(bookName) {
      this.query.title = bookName;
      this.getBookList();
    },
    handleCurrentPageChange(page) {
      this.query.pageNum = page;
      this.getBookList();
      // 这里可以执行任何需要的逻辑，例如重新获取分页数据
      console.log("Current Page changed:", page);
    },
    handlePageSizeChange(size) {
      this.query.pageSize = size;
      this.getBookList();
      // 这里可以执行任何需要的逻辑，例如重新获取分页数据
      console.log("Page Size changed:", size);
    },
    getBookList() {
      listFrontBook(this.query).then((res) => {
        console.log("getBookList: ", res);
        this.books = res.rows;
        this.query.total = res.total;
      });
    },
    categoryChangeHandle(categoryId) {
      console.log("categoryChangeHandle: ", categoryId);
      this.query.categoryId = categoryId;
      this.getBookList();
    },
    getBookCategory() {
      categoryBookList().then((response) => {
        this.categories = response.data;
      });
    },
  },
  created() {
    this.getBookCategory();
    this.getBookList();
    this.getSlide();
  },
};
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

.content-wrapper {
  display: flex;
  justify-content: space-between;
  gap: 20px;
  margin-top: 20px;
  align-items: flex-start;
}

.left-side {
  width: 240px;
  flex-shrink: 0; // 防止收缩
}

.right-side {
  flex: 1; // 自动撑满剩余空间
  min-width: 0; // 防止溢出，特别在 flex 下很重要
}
</style>
