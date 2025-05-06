<template>
  <div class="app-container">
    <book-header/>
    <el-card shadow="never">
      <el-row :gutter="20">
        <el-col :span="24">
          <reading-room :roomOptions="roomOptions"/>
          <!--          <reading-room-seat-->
          <!--            ref="roomCanvas"-->
          <!--            :room-id="currentRoomId"-->
          <!--            @seat-selected="handleSeatSelected"-->
          <!--          />-->
        </el-col>
      </el-row>
    </el-card>
    <YFooter/>
    <el-dialog
      :visible.sync="dialogVisible"
      title="座位预约"
      width="30%"
    >
      <p>您选择了: {{ selectedSeat ? selectedSeat.name : '' }}号座位</p>
      <el-form label-width="80px">
        <el-form-item label="预约时间">
          <el-date-picker
            v-model="reservationTime"
            placeholder="选择预约时间"
            type="datetime"
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
import BookHeader from "@/views/front/book/BookHeader.vue";
import YFooter from "@/views/components/footer/YFooter.vue";
import {countFrontListReadingroom} from "@/api/bookinfo/readingroom";
import ReadingRoom from "@/views/front/readingroom/ReadingRoom.vue";

export default {
  name: 'LibraryRoom',
  components: {
    ReadingRoom,
    BookHeader,
    ReadingRoomSeat,
    YFooter
  },
  data() {
    return {
      currentRoomId: '',
      roomOptions: [],
      selectedSeat: null,
      dialogVisible: false,
      reservationTime: ''
    }
  },
  created() {
    this.getReadRoomList()
  },
  methods: {
    getReadRoomList() {
      countFrontListReadingroom().then(res => {
        console.log("countFrontListReadingroom: ", res)
        this.roomOptions = res.data
      })
    },
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

