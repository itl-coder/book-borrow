<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <h2>图书馆阅览室座位系统</h2>
        <el-select v-model="currentRoomId" placeholder="请选择阅览室" @change="changeRoom">
          <el-option
            v-for="room in roomOptions"
            :key="room.id"
            :label="room.name"
            :value="room.id"
          ></el-option>
        </el-select>

        <reading-room-seat
          ref="roomCanvas"
          :room-id="currentRoomId"
          @seat-selected="handleSeatSelected"
        />
      </el-col>
    </el-row>

    <el-dialog
      title="座位预约"
      :visible.sync="dialogVisible"
      width="30%"
    >
      <p>您选择了: {{ selectedSeat ? selectedSeat.name : '' }}号座位</p>
      <el-form label-width="80px">
        <el-form-item label="预约时间">
          <el-date-picker
            v-model="reservationTime"
            type="datetime"
            placeholder="选择预约时间"
            value-format="yyyy-MM-dd HH:mm:ss"
          ></el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirmReservation">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ReadingRoomSeat from '@/views/front/readingroom/ReadingRoomSeat.vue'

export default {
  name: 'LibraryRoom',
  components: {
    ReadingRoomSeat
  },
  data() {
    return {
      currentRoomId: 'room1',
      roomOptions: [
        { id: 'room1', name: '第一阅览室' },
        { id: 'room2', name: '第二阅览室' },
        { id: 'room3', name: '第三阅览室' }
      ],
      selectedSeat: null,
      dialogVisible: false,
      reservationTime: ''
    }
  },
  methods: {
    handleSeatSelected(seat) {
      this.selectedSeat = seat;
      this.dialogVisible = true;
      this.reservationTime = this.getDefaultTime();
    },

    getDefaultTime() {
      const now = new Date();
      // 默认预约当前时间+30分钟
      now.setMinutes(now.getMinutes() + 30);
      return now.toISOString().replace('T', ' ').substring(0, 19);
    },

    confirmReservation() {
      // 这里应该是调用API预约座位
      // 现在使用假数据模拟
      this.$message.success(`成功预约 ${this.selectedSeat.name} 号座位，时间: ${this.reservationTime}`);

      // 标记座位为已占用
      this.$refs.roomCanvas.markSeatAsOccupied(this.selectedSeat.id);

      this.dialogVisible = false;
    },

    changeRoom() {
      this.$refs.roomCanvas.refreshRoom();
    }
  }
}
</script>

<style scoped>
.app-container {
  padding: 20px;
}

.el-select {
  margin: 20px 0;
}
</style>

