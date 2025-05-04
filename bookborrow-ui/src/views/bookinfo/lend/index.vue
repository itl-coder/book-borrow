<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="用户ID" prop="userId">
        <el-input
          v-model="queryParams.userId"
          placeholder="请输入用户ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="用户名" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="请输入用户名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书ID" prop="bookId">
        <el-input
          v-model="queryParams.bookId"
          placeholder="请输入图书ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="图书名称" prop="bookName">
        <el-input
          v-model="queryParams.bookName"
          placeholder="请输入图书名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类ID" prop="categoryId">
        <el-input
          v-model="queryParams.categoryId"
          placeholder="请输入分类ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类名称" prop="categoryName">
        <el-input
          v-model="queryParams.categoryName"
          placeholder="请输入分类名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="借出时间" prop="borrowTime">
        <el-date-picker clearable
          v-model="queryParams.borrowTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择借出时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="应还时间" prop="dueTime">
        <el-date-picker clearable
          v-model="queryParams.dueTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择应还时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="实际归还时间" prop="returnTime">
        <el-date-picker clearable
          v-model="queryParams.returnTime"
          type="date"
          value-format="yyyy-MM-dd"
          placeholder="请选择实际归还时间">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="逾期缴费金额" prop="lateFee">
        <el-input
          v-model="queryParams.lateFee"
          placeholder="请输入逾期缴费金额"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="逻辑删除：0正常，1删除" prop="deleted">
        <el-input
          v-model="queryParams.deleted"
          placeholder="请输入逻辑删除：0正常，1删除"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="预览图" prop="imgCover">
        <el-input
          v-model="queryParams.imgCover"
          placeholder="请输入预览图"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['bookinfo:lend:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['bookinfo:lend:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['bookinfo:lend:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bookinfo:lend:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="lendList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="主键ID" align="center" prop="id" />
      <el-table-column label="用户ID" align="center" prop="userId" />
      <el-table-column label="用户名" align="center" prop="userName" />
      <el-table-column label="图书ID" align="center" prop="bookId" />
      <el-table-column label="图书名称" align="center" prop="bookName" />
      <el-table-column label="分类ID" align="center" prop="categoryId" />
      <el-table-column label="分类名称" align="center" prop="categoryName" />
      <el-table-column label="借出时间" align="center" prop="borrowTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.borrowTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="应还时间" align="center" prop="dueTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="实际归还时间" align="center" prop="returnTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.returnTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="借阅状态：0借出中，1已归还，2逾期" align="center" prop="status" />
      <el-table-column label="逾期缴费金额" align="center" prop="lateFee" />
      <el-table-column label="备注信息" align="center" prop="remark" />
      <el-table-column label="逻辑删除：0正常，1删除" align="center" prop="deleted" />
      <el-table-column label="预览图" align="center" prop="imgCover" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bookinfo:lend:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bookinfo:lend:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改图书借阅关系对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="用户ID" prop="userId">
          <el-input v-model="form.userId" placeholder="请输入用户ID" />
        </el-form-item>
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" />
        </el-form-item>
        <el-form-item label="图书ID" prop="bookId">
          <el-input v-model="form.bookId" placeholder="请输入图书ID" />
        </el-form-item>
        <el-form-item label="图书名称" prop="bookName">
          <el-input v-model="form.bookName" placeholder="请输入图书名称" />
        </el-form-item>
        <el-form-item label="分类ID" prop="categoryId">
          <el-input v-model="form.categoryId" placeholder="请输入分类ID" />
        </el-form-item>
        <el-form-item label="分类名称" prop="categoryName">
          <el-input v-model="form.categoryName" placeholder="请输入分类名称" />
        </el-form-item>
        <el-form-item label="借出时间" prop="borrowTime">
          <el-date-picker clearable
            v-model="form.borrowTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择借出时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="应还时间" prop="dueTime">
          <el-date-picker clearable
            v-model="form.dueTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择应还时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="实际归还时间" prop="returnTime">
          <el-date-picker clearable
            v-model="form.returnTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择实际归还时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="逾期缴费金额" prop="lateFee">
          <el-input v-model="form.lateFee" placeholder="请输入逾期缴费金额" />
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入备注信息" />
        </el-form-item>
        <el-form-item label="逻辑删除：0正常，1删除" prop="deleted">
          <el-input v-model="form.deleted" placeholder="请输入逻辑删除：0正常，1删除" />
        </el-form-item>
        <el-form-item label="预览图" prop="imgCover">
          <el-input v-model="form.imgCover" placeholder="请输入预览图" />
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
import { listLend, getLend, delLend, addLend, updateLend } from "@/api/bookinfo/lend";

export default {
  name: "Lend",
  data() {
    return {
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
      // 图书借阅关系表格数据
      lendList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userId: null,
        userName: null,
        bookId: null,
        bookName: null,
        categoryId: null,
        categoryName: null,
        borrowTime: null,
        dueTime: null,
        returnTime: null,
        status: null,
        lateFee: null,
        deleted: null,
        imgCover: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "用户ID不能为空", trigger: "blur" }
        ],
        bookId: [
          { required: true, message: "图书ID不能为空", trigger: "blur" }
        ],
        categoryId: [
          { required: true, message: "分类ID不能为空", trigger: "blur" }
        ],
        categoryName: [
          { required: true, message: "分类名称不能为空", trigger: "blur" }
        ],
        borrowTime: [
          { required: true, message: "借出时间不能为空", trigger: "blur" }
        ],
        dueTime: [
          { required: true, message: "应还时间不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "借阅状态：0借出中，1已归还，2逾期不能为空", trigger: "change" }
        ],
        createTime: [
          { required: true, message: "创建时间不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "更新时间不能为空", trigger: "blur" }
        ],
        deleted: [
          { required: true, message: "逻辑删除：0正常，1删除不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询图书借阅关系列表 */
    getList() {
      this.loading = true;
      listLend(this.queryParams).then(response => {
        this.lendList = response.rows;
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
        userId: null,
        userName: null,
        bookId: null,
        bookName: null,
        categoryId: null,
        categoryName: null,
        borrowTime: null,
        dueTime: null,
        returnTime: null,
        status: null,
        lateFee: null,
        remark: null,
        createTime: null,
        updateTime: null,
        deleted: null,
        imgCover: null
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书借阅关系";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLend(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书借阅关系";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateLend(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addLend(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除图书借阅关系编号为"' + ids + '"的数据项？').then(function() {
        return delLend(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('bookinfo/lend/export', {
        ...this.queryParams
      }, `lend_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
