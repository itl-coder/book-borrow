<template>
  <div class="app-container">
    <book-header/>
    <el-card shadow="never">
      <el-row :gutter="20">
        <el-col :span="24">
          <reading-room :roomOptions="roomOptions"/>
        </el-col>
      </el-row>
    </el-card>
    <YFooter/>
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

