<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="90px" size="small">
      <el-form-item label="阅览室名称" prop="roomName">
        <el-input
          v-model="queryParams.roomName"
          clearable
          placeholder="请输入阅览室名称"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="开放状态" prop="isOpen">
        <el-select
          v-model="queryParams.isOpen"
          clearable
          placeholder="请选择阅览室开放状态"
          @change="handleQuery"
        >
          <el-option :label="'开放'" :value="1"/>
          <el-option :label="'关闭'" :value="0"/>
        </el-select>
      </el-form-item>
      <el-form-item label="开放时间" prop="openingHours">
        <el-input
          v-model="queryParams.openingHours"
          clearable
          placeholder="请输入开放时间"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['bookinfo:readingroom:add']"
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
          v-hasPermi="['bookinfo:readingroom:edit']"
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
          v-hasPermi="['bookinfo:readingroom:remove']"
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
          v-hasPermi="['bookinfo:readingroom:export']"
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

    <el-table v-loading="loading" :data="readingroomList" @selection-change="handleSelectionChange">
      <el-table-column align="center" type="selection" width="45"/>
      <el-table-column align="center" label="ID" prop="id"/>
      <el-table-column align="center" label="阅览室名称" width="120" prop="roomName" show-overflow-tooltip/>
      <el-table-column align="center" label="位置" prop="location" show-overflow-tooltip/>
      <el-table-column align="center" label="座位总数" prop="totalSeats" show-overflow-tooltip/>
      <el-table-column align="center" label="可用座位数" width="120" prop="availableSeats" show-overflow-tooltip/>
      <el-table-column label="开放状态" align="center" prop="isOpen" width="100">
        <template #default="{ row }">
          <el-tag
            :type="row.isOpen === 1 ? 'success' : 'info'"
            disable-transitions
          >
            {{ row.isOpen === 1 ? '开放' : '关闭' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" label="开放时间" prop="openingHours" show-overflow-tooltip/>
      <el-table-column align="center" label="简介" prop="description" show-overflow-tooltip/>
      <el-table-column align="center" label="封面图">
        <template slot-scope="scope">
          <image-preview :src="scope.row.coverImageUrl" :width="50" :height="50"/>
        </template>
      </el-table-column>
      <el-table-column label="逻辑删除" align="center" prop="isDeleted" width="120">
        <template #default="{ row }">
          <el-tag
            :type="row.isDeleted === 0 ? 'success' : 'danger'"
            disable-transitions
          >
            {{ row.isDeleted === 0 ? '未删除' : '已删除' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column align="center" class-name="small-padding fixed-width"  fixed="right" width="130" label="操作">
        <template slot-scope="scope">
          <el-button
            v-hasPermi="['bookinfo:readingroom:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['bookinfo:readingroom:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
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

    <!-- 添加或修改阅览室信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="700px">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="阅览室名称" prop="roomName">
          <el-input v-model="form.roomName" placeholder="请输入阅览室名称"/>
        </el-form-item>
        <el-form-item label="阅览室位置" prop="location">
          <el-input v-model="form.location" placeholder="请输入阅览室位置"/>
        </el-form-item>
        <el-form-item label="阅览室座位总数" prop="totalSeats">
          <el-input v-model="form.totalSeats" placeholder="请输入阅览室座位总数"/>
        </el-form-item>
        <el-form-item label="可用座位数" prop="availableSeats">
          <el-input v-model="form.availableSeats" placeholder="请输入可用座位数"/>
        </el-form-item>
        <el-form-item label="开放状态" prop="isOpen">
          <el-select
            v-model="form.isOpen"
            placeholder="请选择阅览室开放状态"
            clearable
          >
            <el-option :label="'开放'" :value="1" />
            <el-option :label="'关闭'" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="开放时间" prop="openingHours">
          <el-input v-model="form.openingHours" placeholder="请输入开放时间"/>
        </el-form-item>
        <el-form-item label="阅览室简介" prop="description">
          <el-input v-model="form.description" placeholder="请输入内容" type="textarea"/>
        </el-form-item>
        <el-form-item label="阅览室封面图" prop="coverImageUrl">
          <image-upload v-model="form.coverImageUrl"/>
        </el-form-item>
        <el-form-item label="逻辑删除" prop="isDeleted">
          <el-switch
            v-model="form.isDeleted"
            :active-value="1"
            :inactive-value="0"
            active-text="数据删除"
            inactive-text="数据恢复"
          />
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
import {
  addReadingroom,
  delReadingroom,
  getReadingroom,
  listReadingroom,
  updateReadingroom
} from "@/api/bookinfo/readingroom";

export default {
  name: "Readingroom",
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
      // 阅览室信息表格数据
      readingroomList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roomName: null,
        location: null,
        totalSeats: null,
        availableSeats: null,
        isOpen: null,
        openingHours: null,
        description: null,
        coverImageUrl: null,
        isDeleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        roomName: [
          {required: true, message: "阅览室名称不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询阅览室信息列表 */
    getList() {
      this.loading = true;
      listReadingroom(this.queryParams).then(response => {

        this.readingroomList = response.rows;
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
        roomName: null,
        location: null,
        totalSeats: null,
        availableSeats: null,
        isOpen: null,
        openingHours: null,
        description: null,
        coverImageUrl: null,
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
      this.title = "添加阅览室信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getReadingroom(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改阅览室信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateReadingroom(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addReadingroom(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除阅览室信息编号为"' + ids + '"的数据项？').then(function () {
        return delReadingroom(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('bookinfo/readingroom/export', {
        ...this.queryParams
      }, `readingroom_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
