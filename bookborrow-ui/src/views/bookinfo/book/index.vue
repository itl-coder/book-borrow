<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="118px" size="small">
      <el-form-item label="图书标题" prop="title">
        <el-input
          v-model="queryParams.title"
          clearable
          placeholder="请输入图书标题"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="作者" prop="author">
        <el-input
          v-model="queryParams.author"
          clearable
          placeholder="请输入作者"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版社" prop="publisher">
        <el-input
          v-model="queryParams.publisher"
          clearable
          placeholder="请输入出版社"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出版日期" prop="publishDate">
        <el-date-picker v-model="queryParams.publishDate"
                        clearable
                        placeholder="请选择出版日期"
                        type="datetime"
                        value-format="yyyy-MM-dd HH:mm:ss">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="书籍分类" prop="categoryId">
        <el-select
          v-model="queryParams.categoryId"
          clearable
          placeholder="请选择分类"
          @change="handleQuery"
        >
          <!-- 遍历 bookCategoryList，渲染选项 -->
          <el-option
            v-for="item in bookCategoryList"
            :key="item.id"
            :label="item.categoryName"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="是否上架" prop="isPublished">
        <el-select
          v-model="queryParams.isPublished"
          clearable
          placeholder="请选择是否上架"
          @change="handleQuery"
        >
          <el-option :label="'上架'" :value="1" />
          <el-option :label="'下架'" :value="0" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['bookinfo:book:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['bookinfo:book:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['bookinfo:book:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['bookinfo:book:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="bookList"
      @selection-change="handleSelectionChange"
      stripe
      border
      highlight-current-row
      style="width: 100%"
    >
      <!-- 多选框 -->
      <el-table-column type="selection" width="55" align="center"/>

      <el-table-column prop="id" label="图书ID" width="80" align="center"/>
      <el-table-column prop="title" label="标题" width="160" align="center" show-overflow-tooltip/>
      <el-table-column prop="author" label="作者" width="120" align="center" show-overflow-tooltip/>
      <el-table-column prop="publisher" label="出版社" width="120" align="center" show-overflow-tooltip/>

      <el-table-column label="出版日期" width="120" align="center" show-overflow-tooltip>
        <template #default="{ row }">
          <span>{{ parseTime(row.publishDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>

      <el-table-column prop="categoryName" label="分类" width="100" align="center" show-overflow-tooltip/>
      <el-table-column prop="price" label="价格" width="80" align="center">
        <template #default="{ row }">
          {{ row.price }}$
        </template>
      </el-table-column>

      <el-table-column prop="stock" label="库存" width="80" align="center"/>

      <!-- 借阅状态标签 -->
      <el-table-column label="借阅状态" width="100" align="center">
        <template #default="{ row }">
          <el-tag :type="row.borrowStatus === 0 ? 'success' : 'warning'" disable-transitions>
            {{ row.borrowStatus === 0 ? '可借' : '已借出' }}
          </el-tag>
        </template>
      </el-table-column>

      <!-- 图书简介 Tooltip -->
      <el-table-column label="简介" align="center" width="150">
        <template #default="{ row }">
          <el-tooltip effect="dark" :content="row.description" placement="top">
            <div style="white-space: nowrap; overflow: hidden; text-overflow: ellipsis;">
              {{ row.description }}
            </div>
          </el-tooltip>
        </template>
      </el-table-column>

      <!-- 是否上架 Switch -->
      <el-table-column label="上架状态" width="150" align="center">
        <template #default="{ row }">
          <el-switch
            disabled
            v-model="row.isPublished"
            :active-value="1"
            :inactive-value="0"
            active-text="上架"
            inactive-text="下架"
            @change="val => handlePublishChange(row, val)"
          />
        </template>
      </el-table-column>

      <!-- 是否推荐 Switch -->
      <el-table-column label="推荐状态" width="150" align="center">
        <template #default="{ row }">
          <el-switch
            disabled
            v-model="row.isRecommended"
            :active-value="1"
            :inactive-value="0"
            active-text="推荐"
            inactive-text="普通"
            @change="val => handleRecommendChange(row, val)"
          />
        </template>
      </el-table-column>
      <!-- 图书封面图 -->
      <el-table-column label="图书封面" align="center" prop="coverUrl" width="100">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>

      <!-- 操作按钮 -->
      <el-table-column label="操作" fixed="right" width="130" align="center">
        <template #default="{ row }">
          <el-button
            v-hasPermi="['bookinfo:book:edit']"
            icon="el-icon-edit"
            size="mini"
            type="primary"
            @click="handleUpdate(row)"
          ></el-button>
          <el-button
            v-hasPermi="['bookinfo:book:remove']"
            icon="el-icon-delete"
            size="mini"
            type="danger"
            @click="handleDelete(row)"
          ></el-button>
        </template>
      </el-table-column>
    </el-table>


    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

    <!-- 添加或修改图书信息（无外键约束）对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="700px">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="图书标题" prop="title">
          <el-input v-model="form.title" placeholder="请输入图书标题"/>
        </el-form-item>
        <el-form-item label="作者" prop="author">
          <el-input v-model="form.author" placeholder="请输入作者"/>
        </el-form-item>
        <el-form-item label="出版社" prop="publisher">
          <el-input v-model="form.publisher" placeholder="请输入出版社"/>
        </el-form-item>
        <el-form-item label="出版日期" prop="publishDate">
          <el-date-picker v-model="form.publishDate"
                          clearable
                          placeholder="请选择出版日期"
                          type="date"
                          value-format="yyyy-MM-dd">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" placeholder="请输入价格"/>
        </el-form-item>
        <el-form-item label="库存数量" prop="stock">
          <el-input v-model="form.stock" type="number" placeholder="请输入库存数量"/>
        </el-form-item>
        <el-form-item label="图书简介" prop="description">
          <el-input v-model="form.description" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <!-- 是否上架 -->
        <el-form-item label="是否上架" prop="isPublished">
          <el-switch
            v-model="form.isPublished"
            :active-value="1"
            :inactive-value="0"
            active-text="上架"
            inactive-text="下架"
          />
        </el-form-item>

        <!-- 是否推荐 -->
        <el-form-item label="是否推荐" prop="isRecommended">
          <el-switch
            v-model="form.isRecommended"
            :active-value="1"
            :inactive-value="0"
            active-text="推荐"
            inactive-text="普通"
          />
        </el-form-item>
        <el-form-item label="图书封面" prop="coverUrl">
          <image-upload v-model="form.coverUrl"/>
        </el-form-item>
        <el-form-item label="逻辑删除" prop="isDeleted">
          <el-select v-model="form.isDeleted" placeholder="请选择逻辑删除状态" clearable>
            <el-option :label="'恢复数据'" :value="0" />
            <el-option :label="'删除数据'" :value="1" />
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addBook, delBook, getBook, listBook, updateBook} from "@/api/bookinfo/book";
import {countFrontListCategory} from "@/api/bookinfo/category";

export default {
  name: "Book",
  data() {
    return {
      bookCategoryList: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 图书信息（无外键约束）表格数据
      bookList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        title: null,
        author: null,
        publisher: null,
        publishDate: null,
        categoryId: null,
        categoryName: null,
        price: null,
        stock: null,
        borrowStatus: null,
        description: null,
        isPublished: null,
        isRecommended: null,
        coverUrl: null,
        isDeleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        title: [
          {required: true, message: "图书标题不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "分类ID不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCountFrontCategoryList();
  },
  methods: {
    getCountFrontCategoryList() {
      countFrontListCategory().then(response => {
        this.bookCategoryList = response.data
      })
    },
    /** 查询图书信息（无外键约束）列表 */
    getList() {
      this.loading = true;
      listBook(this.queryParams).then(response => {
        this.bookList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        title: null,
        author: null,
        publisher: null,
        publishDate: null,
        categoryId: null,
        categoryName: null,
        price: null,
        stock: null,
        borrowStatus: null,
        description: null,
        isPublished: null,
        isRecommended: null,
        coverUrl: null,
        createTime: null,
        updateTime: null,
        isDeleted: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书信息（无外键约束）";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getBook(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateBook(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addBook(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除图书信息编号为"' + ids + '"的数据项？').then(function () {
        return delBook(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('bookinfo/book/export', {
        ...this.queryParams
      }, `book_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
