<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="128px">
      <el-form-item label="是否可用" prop="isAvailable">
        <el-select
          v-model="queryParams.isAvailable"
          placeholder="请选择座位是否可用"
          clearable
        >
          <el-option :label="'可用'" :value="1" />
          <el-option :label="'不可用'" :value="0" />
        </el-select>
      </el-form-item>

      <el-form-item label="是否预约" prop="isReserved">
        <el-select
          v-model="queryParams.isReserved"
          placeholder="请选择座位是否已预约"
          clearable
        >
          <el-option :label="'已预约'" :value="1" />
          <el-option :label="'未预约'" :value="0" />
        </el-select>
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
          v-hasPermi="['bookinfo:seat:add']"
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
          v-hasPermi="['bookinfo:seat:edit']"
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
          v-hasPermi="['bookinfo:seat:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['bookinfo:seat:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="seatList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="45" align="center" />
      <el-table-column label="座位ID" align="center" prop="id" show-overflow-tooltip/>
      <el-table-column label="阅览室" align="center" prop="roomName" show-overflow-tooltip/>
      <el-table-column label="座位排号" align="center" prop="rowNum" show-overflow-tooltip/>
      <el-table-column label="座位列号" align="center" prop="colNum" show-overflow-tooltip/>
      <el-table-column label="是否可用" align="center" prop="isAvailable">
        <template #default="{ row }">
          <el-tag
            :type="row.isAvailable === 1 ? 'success' : 'info'"
            disable-transitions
          >
            {{ row.isAvailable === 1 ? '可用' : '不可用' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="是否已预约" align="center" prop="isReserved" show-overflow-tooltip/>
      <el-table-column label="座位描述" align="center" prop="description" show-overflow-tooltip/>
      <el-table-column label="逻辑删除标志" align="center" prop="isDeleted" show-overflow-tooltip/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['bookinfo:seat:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['bookinfo:seat:remove']"
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

    <!-- 添加或修改座位信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="阅览室ID" prop="readingRoomId">
          <el-input v-model="form.readingRoomId" placeholder="请输入阅览室ID" />
        </el-form-item>
        <el-form-item label="座位排号" prop="rowNum">
          <el-input v-model="form.rowNum" placeholder="请输入座位排号" />
        </el-form-item>
        <el-form-item label="座位列号" prop="colNum">
          <el-input v-model="form.colNum" placeholder="请输入座位列号" />
        </el-form-item>
        <el-form-item label="座位是否可用，1为可用，0为不可用" prop="isAvailable">
          <el-input v-model="form.isAvailable" placeholder="请输入座位是否可用，1为可用，0为不可用" />
        </el-form-item>
        <el-form-item label="座位是否已预约，0为未预约，1为已预约" prop="isReserved">
          <el-input v-model="form.isReserved" placeholder="请输入座位是否已预约，0为未预约，1为已预约" />
        </el-form-item>
        <el-form-item label="座位描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="逻辑删除标志，0为未删除，1为已删除" prop="isDeleted">
          <el-input v-model="form.isDeleted" placeholder="请输入逻辑删除标志，0为未删除，1为已删除" />
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
import { listSeat, getSeat, delSeat, addSeat, updateSeat } from "@/api/bookinfo/seat";

export default {
  name: "Seat",
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
      // 座位信息表格数据
      seatList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        readingRoomId: null,
        rowNum: null,
        colNum: null,
        isAvailable: null,
        isReserved: null,
        description: null,
        isDeleted: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        readingRoomId: [
          { required: true, message: "阅览室ID不能为空", trigger: "blur" }
        ],
        rowNum: [
          { required: true, message: "座位排号不能为空", trigger: "blur" }
        ],
        colNum: [
          { required: true, message: "座位列号不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询座位信息列表 */
    getList() {
      this.loading = true;
      listSeat(this.queryParams).then(response => {
        console.log("listSeat: ",response)
        this.seatList = response.rows;
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
        readingRoomId: null,
        rowNum: null,
        colNum: null,
        isAvailable: null,
        isReserved: null,
        description: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加座位信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getSeat(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改座位信息";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSeat(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addSeat(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除座位信息编号为"' + ids + '"的数据项？').then(function() {
        return delSeat(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('bookinfo/seat/export', {
        ...this.queryParams
      }, `seat_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
