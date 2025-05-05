<template>
  <div class="app-container">
    <!-- 搜索表单 -->
    <div class="search-card">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="标题" prop="title">
          <el-input v-model="queryParams.title" placeholder="请输入标题" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item label="副标题" prop="subtitle">
          <el-input v-model="queryParams.subtitle" placeholder="请输入副标题" clearable @keyup.enter.native="handleQuery" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 操作按钮区域 -->
    <div class="operation-card">
      <div class="left-actions">
        <el-button type="primary" icon="el-icon-plus" @click="handleAdd" v-hasPermi="['bookinfo:carousel:add']">新增</el-button>
        <el-button type="success" icon="el-icon-edit" :disabled="single" @click="handleUpdate" v-hasPermi="['bookinfo:carousel:edit']">修改</el-button>
        <el-button type="danger" icon="el-icon-delete" :disabled="multiple" @click="handleDelete" v-hasPermi="['bookinfo:carousel:remove']">删除</el-button>
        <el-button type="warning" icon="el-icon-download" @click="handleExport" v-hasPermi="['bookinfo:carousel:export']">导出</el-button>
      </div>
      <div class="right-tools">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </div>
    </div>

    <!-- 数据表格 -->
    <div class="table-card">
      <el-table
        v-loading="loading"
        :data="carouselList"
        @selection-change="handleSelectionChange"
        border
        stripe
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="ID" prop="id" width="80" align="center" />
        <el-table-column label="标题" prop="title" min-width="150" show-overflow-tooltip />
        <el-table-column label="副标题" prop="subtitle" min-width="150" show-overflow-tooltip />
        <el-table-column label="图片" width="150" align="center">
          <template slot-scope="scope">
            <image-preview :src="scope.row.imageUrl" :width="120" :height="80" />
          </template>
        </el-table-column>
        <el-table-column label="跳转链接" prop="linkUrl" min-width="200" show-overflow-tooltip />
        <el-table-column label="排序" prop="sort" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.sort <= 3 ? 'success' : ''">{{ scope.row.sort }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="状态" width="100" align="center">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status === '1' ? 'success' : 'danger'">
              {{ scope.row.status === '1' ? '启用' : '停用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" align="center" fixed="right">
          <template slot-scope="scope">
            <el-button type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['bookinfo:carousel:edit']">编辑</el-button>
            <el-button type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" style="color: #f56c6c" v-hasPermi="['bookinfo:carousel:remove']">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination-card">
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- 添加/编辑对话框 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="680px"
      append-to-body
      :close-on-click-modal="false"
      class="form-dialog"
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="标题：" prop="title">
          <el-input v-model="form.title" placeholder="请输入标题" clearable />
        </el-form-item>

        <el-form-item label="副标题：" prop="subtitle">
          <el-input v-model="form.subtitle" placeholder="请输入副标题" clearable />
        </el-form-item>

        <el-form-item label="图片：" prop="imageUrl">
          <image-upload v-model="form.imageUrl" :limit="1" />
          <div class="form-tip">建议尺寸：800×400像素，大小不超过2MB</div>
        </el-form-item>

        <el-form-item label="跳转链接：" prop="linkUrl">
          <el-input v-model="form.linkUrl" placeholder="请输入完整的URL链接" clearable />
        </el-form-item>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="排序值：" prop="sort">
              <el-input-number v-model="form.sort" :min="1" :max="999" controls-position="right" style="width: 100%" />
              <div class="form-tip">数字越小排序越靠前</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="启用状态：" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio :label="1">启用</el-radio>
            <el-radio :label="0">停用</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="是否删除：" prop="isDeleted">
          <el-radio-group v-model="form.isDeleted">
            <el-radio :label="1">已删除</el-radio>
            <el-radio :label="0">正常</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注：" prop="remark">
          <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注信息" maxlength="200" show-word-limit />
        </el-form-item>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm" :loading="submitLoading">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCarousel, getCarousel, delCarousel, addCarousel, updateCarousel } from "@/api/bookinfo/carousel";

export default {
  name: "Carousel",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      carouselList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        subtitle: null,
        imageUrl: null,
        linkUrl: null,
        sort: null,
        status: null,
        isDeleted: null,
      },
      form: {
        status: "1",
        sort: 99,
        isDeleted: "0"
      },
      rules: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
          { max: 50, message: "标题长度不能超过50个字符", trigger: "blur" }
        ],
        imageUrl: [
          { required: true, message: "请上传轮播图片", trigger: "blur" }
        ],
        sort: [
          { required: true, message: "请输入排序值", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listCarousel(this.queryParams).then(response => {
        this.carouselList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null,
        title: null,
        subtitle: null,
        imageUrl: null,
        linkUrl: null,
        sort: 99,
        status: "1",
        isDeleted: "0",
        remark: null
      };
      this.resetForm("form");
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加轮播图";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getCarousel(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改轮播图";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const promise = this.form.id ? updateCarousel(this.form) : addCarousel(this.form);
          promise.then(() => {
            this.$message.success(this.form.id ? "修改成功" : "新增成功");
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm('是否确认删除选中的轮播图？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true;
        return delCarousel(ids);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    },
    handleExport() {
      this.download('bookinfo/carousel/export', {
        ...this.queryParams
      }, `轮播图数据_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>

<style lang="scss" scoped>
.app-container {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.search-card,
.operation-card,
.table-card,
.pagination-card {
  background: #fff;
  border-radius: 4px;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
  padding: 16px;
}

.operation-card {
  display: flex;
  justify-content: space-between;
  align-items: center;

  .left-actions {
    display: flex;
    gap: 10px;
  }
}

.el-table {
  ::v-deep .el-table__header th {
    background-color: #f5f7fa;
    font-weight: bold;
    color: #333;
  }
}

.form-dialog {
  ::v-deep .el-dialog__header {
    border-bottom: 1px solid #eee;
    padding: 16px 20px;
    margin-right: 0;
  }

  ::v-deep .el-dialog__body {
    padding: 20px;
  }

  .el-form-item {
    margin-bottom: 18px;

    ::v-deep .el-form-item__label {
      color: #606266;
    }
  }

  .form-tip {
    font-size: 12px;
    color: #999;
    margin-top: 4px;
    line-height: 1.5;
  }
}

.dialog-footer {
  padding: 16px 20px;
  border-top: 1px solid #eee;
  text-align: right;

  .el-button {
    width: 90px;
  }
}
</style>
