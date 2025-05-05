<template>
  <div class="app-container">
    <el-card shadow="never" class="search-card">
      <div class="filter-container">
        <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="100px" size="medium">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="用户名" prop="userName" class="form-item">
                <el-input
                  v-model="queryParams.userName"
                  clearable
                  placeholder="请输入用户名"
                  @keyup.enter.native="handleQuery"
                  prefix-icon="el-icon-user"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="图书名称" prop="bookName" class="form-item">
                <el-input
                  v-model="queryParams.bookName"
                  clearable
                  placeholder="请输入图书名称"
                  @keyup.enter.native="handleQuery"
                  prefix-icon="el-icon-notebook-2"
                />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="分类名称" prop="categoryName" class="form-item">
                <el-select
                  v-model="queryParams.categoryName"
                  clearable
                  placeholder="请选择分类"
                  style="width: 100%"
                >
                  <el-option
                    v-for="item in bookCategoryList"
                    :key="item.id"
                    :label="item.categoryName"
                    :value="item.categoryName"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24" style="text-align: right; padding-right: 20px;">
              <el-button type="primary" icon="el-icon-search" size="medium" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh-left" size="medium" @click="resetQuery">重置</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-card>

    <el-card shadow="never" class="table-card">
      <div class="table-header">
        <el-row :gutter="10" class="mb8">
          <el-col :span="16" class="table-header-left">
            <el-button-group>
              <el-button
                v-hasPermi="['bookinfo:lend:add']"
                icon="el-icon-plus"
                size="medium"
                type="primary"
                @click="handleAdd"
              >新增
              </el-button>
              <el-button
                v-hasPermi="['bookinfo:lend:edit']"
                :disabled="single"
                icon="el-icon-edit"
                size="medium"
                type="success"
                @click="handleUpdate"
              >修改
              </el-button>
              <el-button
                v-hasPermi="['bookinfo:lend:remove']"
                :disabled="multiple"
                icon="el-icon-delete"
                size="medium"
                type="danger"
                @click="handleDelete"
              >删除
              </el-button>
            </el-button-group>
            <el-button
              v-hasPermi="['bookinfo:lend:export']"
              icon="el-icon-download"
              size="medium"
              plain
              @click="handleExport"
            >导出
            </el-button>
          </el-col>
          <el-col :span="8" class="table-header-right">
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-col>
        </el-row>
      </div>

      <el-table
        v-loading="loading"
        :data="lendList"
        @selection-change="handleSelectionChange"
        style="width: 100%"
        stripe
        border
        highlight-current-row
      >
        <el-table-column align="center" type="selection" width="55" fixed/>
        <el-table-column align="center" label="主键ID" prop="id" width="100"/>
        <el-table-column align="center" label="用户名" prop="userName" width="120"/>
        <el-table-column align="center" label="图书名称" prop="bookName" width="150" show-overflow-tooltip/>
        <el-table-column align="center" label="备注信息" prop="remark" width="150" show-overflow-tooltip/>
        <el-table-column align="center" label="分类名称" prop="categoryName" width="120"/>
        <el-table-column align="center" label="借出时间" prop="borrowTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.borrowTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="应还时间" prop="dueTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.dueTime, '{y}-{m}-{d}') }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="实际归还时间" prop="returnTime" width="180">
          <template slot-scope="scope">
            <span>{{ scope.row.returnTime ? parseTime(scope.row.returnTime, '{y}-{m}-{d}') : '未归还' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="借阅状态" prop="status" width="120">
          <template slot-scope="scope">
            <el-tag
              :type="getStatusTagType(scope.row.status)"
              effect="dark"
              :class="'status-tag status-' + scope.row.status"
            >
              {{ getStatusText(scope.row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column align="center" label="逾期金额" width="120" prop="lateFee">
          <template slot-scope="scope">
            <span>{{ scope.row.lateFee || '0.00' }} 元</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="状态" prop="deleted" width="100">
          <template slot-scope="scope">
            <el-tag
              :effect="scope.row.deleted === 0 ? 'dark' : 'plain'"
              :type="scope.row.deleted === 0 ? 'success' : 'danger'"
            >
              {{ scope.row.deleted === 0 ? '正常' : '已删除' }}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column align="center" label="封面" prop="imgCover" width="100">
          <template slot-scope="scope">
            <el-image
              :preview-src-list="[bookCoverUrl(scope.row.imgCover)]"
              :src="scope.row.imgCover | bookCoverUrl"
              fit="cover"
              class="book-cover"
            >
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </template>
        </el-table-column>

        <el-table-column align="center" label="操作" width="180" fixed="right">
          <template slot-scope="scope">
            <el-button
              v-hasPermi="['bookinfo:lend:edit']"
              icon="el-icon-edit"
              size="mini"
              type="text"
              class="action-btn edit-btn"
              @click="handleUpdate(scope.row)"
            >编辑
            </el-button>
            <el-button
              v-hasPermi="['bookinfo:lend:remove']"
              icon="el-icon-delete"
              size="mini"
              type="text"
              class="action-btn delete-btn"
              @click="handleDelete(scope.row)"
            >删除
            </el-button>
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
    </el-card>

    <!-- 添加或修改图书借阅对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      append-to-body
      width="900px"
      :close-on-click-modal="false"
      custom-class="lend-dialog"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" size="medium">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="用户名" prop="userName">
              <el-input v-model="form.userName" disabled prefix-icon="el-icon-user"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="图书名称" prop="bookName">
              <el-input v-model="form.bookName" placeholder="请输入图书名称" prefix-icon="el-icon-notebook-2"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="分类ID" prop="categoryId">
              <el-select
                v-model="form.categoryId"
                clearable
                disabled
                placeholder="请选择分类"
                @change="handleCategoryIdChange"
                style="width: 100%"
              >
                <el-option
                  v-for="item in bookCategoryList"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="分类名称" prop="categoryName">
              <el-select
                v-model="form.categoryName"
                clearable
                placeholder="请选择分类"
                @change="handleCategoryNameChange"
                style="width: 100%"
              >
                <el-option
                  v-for="item in bookCategoryList"
                  :key="item.id"
                  :label="item.categoryName"
                  :value="item.categoryName"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="借出时间" prop="borrowTime">
              <el-date-picker
                v-model="form.borrowTime"
                type="datetime"
                placeholder="请选择借出时间"
                style="width: 100%"
                value-format="yyyy-MM-dd HH:mm:ss"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="应还时间" prop="dueTime">
              <el-date-picker
                v-model="form.dueTime"
                type="datetime"
                placeholder="请选择应还时间"
                style="width: 100%"
                value-format="yyyy-MM-dd HH:mm:ss"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="实际归还时间" prop="returnTime">
              <el-date-picker
                v-model="form.returnTime"
                type="datetime"
                placeholder="请选择实际归还时间"
                style="width: 100%"
                value-format="yyyy-MM-dd HH:mm:ss"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="逾期金额" prop="lateFee">
              <el-input-number
                v-model="form.lateFee"
                placeholder="请输入逾期缴费金额"
                :min="0"
                :precision="2"
                style="width: 100%"
                controls-position="right"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="状态" prop="deleted">
              <el-select
                v-model="form.deleted"
                placeholder="请选择状态"
                style="width: 100%"
              >
                <el-option :value="0" label="正常"></el-option>
                <el-option :value="1" label="删除"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="封面预览" prop="imgCover">
              <el-image
                :preview-src-list="[bookCoverUrl(form.imgCover)]"
                :src="form.imgCover | bookCoverUrl"
                fit="cover"
                class="dialog-cover"
              >
                <div slot="error" class="image-slot">
                  <i class="el-icon-picture-outline"></i>
                  <span>暂无封面</span>
                </div>
              </el-image>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="备注信息" prop="remark">
          <el-input
            v-model="form.remark"
            type="textarea"
            :rows="3"
            placeholder="请输入备注信息"
            maxlength="200"
            show-word-limit
          />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" size="medium">取 消</el-button>
        <el-button type="primary" @click="submitForm" size="medium">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {addLend, delLend, getLend, listLend, updateLend} from "@/api/bookinfo/lend";
import {bookCoverUrl} from "@/utils/previewImg";
import {parseTime} from "../../../utils/ruoyi";
import {countFrontListCategory} from "@/api/bookinfo/category";

export default {
  name: "Lend",
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
      // 图书借阅表格数据
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
          {required: true, message: "用户ID不能为空", trigger: "blur"}
        ],
        bookId: [
          {required: true, message: "图书ID不能为空", trigger: "blur"}
        ],
        categoryId: [
          {required: true, message: "分类ID不能为空", trigger: "blur"}
        ],
        categoryName: [
          {required: true, message: "分类名称不能为空", trigger: "blur"}
        ],
        borrowTime: [
          {required: true, message: "借出时间不能为空", trigger: "blur"}
        ],
        dueTime: [
          {required: true, message: "应还时间不能为空", trigger: "blur"}
        ],
        status: [
          {required: true, message: "借阅状态：0借出中，1已归还，2逾期不能为空", trigger: "change"}
        ],
        createTime: [
          {required: true, message: "创建时间不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "更新时间不能为空", trigger: "blur"}
        ],
        deleted: [
          {required: true, message: "逻辑删除：0正常，1删除不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCountFrontCategoryList();
  },
  methods: {
    handleCategoryIdChange() {
      const selectedCategory = this.bookCategoryList.find(item => item.id === this.form.categoryId);
      if (selectedCategory) {
        this.form.categoryName = selectedCategory.categoryName;
      }
    },
    handleCategoryNameChange() {
      const selectedCategory = this.bookCategoryList.find(item => item.categoryName === this.form.categoryName);
      if (selectedCategory) {
        this.form.categoryId = selectedCategory.id;
      }
    },
    getCountFrontCategoryList() {
      countFrontListCategory().then(response => {
        this.bookCategoryList = response.data
      })
    },
    parseTime,
    bookCoverUrl,
    getStatusText(status) {
      switch (status) {
        case 0: return '借出中';
        case 1: return '已归还';
        case 2: return '逾期';
        default: return '未知';
      }
    },
    getStatusTagType(status) {
      switch (status) {
        case 0: return 'info';
        case 1: return 'success';
        case 2: return 'danger';
        default: return 'warning';
      }
    },
    /** 查询图书借阅列表 */
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加图书借阅";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getLend(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改图书借阅";
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
      this.$modal.confirm('是否确认删除图书借阅编号为"' + ids + '"的数据项？').then(function () {
        return delLend(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
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

<style lang="scss" scoped>
.app-container {
  padding: 20px;
  background-color: #f5f7fa;
}

.search-card {
  margin-bottom: 20px;

  .filter-container {
    padding: 20px 20px 0;
  }

  .form-item {
    margin-bottom: 20px;
  }
}

.table-card {
  border-radius: 4px;

  .table-header {
    padding: 15px 20px;
    border-bottom: 1px solid #ebeef5;

    .table-header-left {
      display: flex;
      align-items: center;
      gap: 10px;
    }

    .table-header-right {
      display: flex;
      justify-content: flex-end;
      align-items: center;
    }
  }

  .pagination-container {
    padding: 16px;
    text-align: right;
  }
}

.book-cover {
  width: 50px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #f5f7fa;
    color: #909399;
  }
}

.dialog-cover {
  width: 120px;
  height: 160px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

  .image-slot {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    background: #f5f7fa;
    color: #909399;

    i {
      font-size: 30px;
      margin-bottom: 10px;
    }
  }
}

.action-btn {
  padding: 5px 8px;

  &.edit-btn {
    color: #67C23A;

    &:hover {
      color: #85ce61;
    }
  }

  &.delete-btn {
    color: #F56C6C;

    &:hover {
      color: #f78989;
    }
  }
}

.status-tag {
  font-weight: bold;

  &.status-0 {
    background-color: #ecf5ff;
    border-color: #d9ecff;
    color: #409EFF;
  }

  &.status-1 {
    background-color: #f0f9eb;
    border-color: #e1f3d8;
    color: #67C23A;
  }

  &.status-2 {
    background-color: #fef0f0;
    border-color: #fde2e2;
    color: #F56C6C;
  }
}

.lend-dialog {
  .el-dialog__body {
    padding: 20px 30px;
  }

  .el-form-item {
    margin-bottom: 22px;
  }

  .dialog-footer {
    padding: 10px 20px 20px;
    text-align: right;
  }
}
.el-button-group .el-button{
  margin-right: 15px;
}
</style>
