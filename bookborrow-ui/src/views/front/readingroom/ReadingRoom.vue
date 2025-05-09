<template>
  <div class="library-container">
    <el-card class="library-card">
      <div slot="header" class="room-header">
        <span v-if="chooseRoomFlag"
        >当前选择的是: {{ currentRoom.roomName }} 位置: ({{
            currentRoom.location
          }})</span
        >
        <el-select
          v-model="selectedRoomId"
          placeholder="选择阅览室"
          style="width: 200px; margin-left: 20px"
          @change="changeRoom"
        >
          <el-option
            v-for="room in roomOptions"
            :key="room.id"
            :disabled="room.isOpen === 0"
            :label="room.roomName"
            :value="room.id"
          >
            <span style="float: left">{{ room.roomName }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ room.isOpen === 0 ? "维修中" : "可预约" }}
            </span>
          </el-option>
        </el-select>
      </div>

      <div class="canvas-container">
        <canvas
          ref="libraryCanvas"
          :height="canvasHeight"
          :width="canvasWidth"
          @click="showRoomInfo"
        ></canvas>
      </div>

      <el-dialog
        :fullscreen="isFullscreen"
        :title="currentRoom.roomName + ' - 详细信息'"
        :visible.sync="dialogVisible"
        class="room-dialog"
        width="70%"
      >
        <div slot="title">
          <div class="fullscreen-mode">
            {{ currentRoom.roomName }} - 详细信息
            <el-tooltip
              :content="isFullscreen ? '退出全屏' : '进入全屏' "
              class="item"
              effect="light"
              placement="top"
            >
              <el-button
                :icon="
                isFullscreen ? 'el-icon-switch-button' : 'el-icon-full-screen'
              "
                class="fullscreen-btn"
                type="text"
                @click="toggleFullscreen"
              ></el-button>
            </el-tooltip>
          </div>
        </div>

        <div class="room-info">
          <!-- 基本信息展示 -->
          <div class="info-row">
            <span class="info-label">位置：</span>
            <span>{{ currentRoom.location }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">开放时间：</span>
            <span>{{ currentRoom.openingHours }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">状态：</span>
            <el-tag :type="currentRoom.status === 1 ? 'success' : 'danger'">
              {{ currentRoom.status === 1 ? "开放中" : "维修中" }}
            </el-tag>
          </div>
          <div class="info-row">
            <span class="info-label">描述：</span>
            <span>{{ currentRoom.description }}</span>
          </div>
          <div v-if="currentRoom.id === 4" class="info-row">
            <span class="info-label">精选服务：</span>
            <el-tag type="warning">计算机使用</el-tag>
            <el-tag style="margin-left: 10px" type="success">打印服务</el-tag>
          </div>
          <div class="room-image">
            <img
              :src="currentRoom.coverImageUrl | bookCoverUrl"
              :style="{
                'max-width': isFullscreen ? '100%' : '80%',
                'margin-top': '15px',
                cursor: 'pointer',
                transition: 'all 0.3s',
              }"
              alt="阅览室图片"
              @click="toggleFullscreen"
            />
          </div>

          <!-- 新增的座位选择组件 -->
          <div style="margin-top: 20px">
            <reading-room-seat
              v-if="dialogVisible && currentRoom.id"
              :room-id="'room' + currentRoom.id"
              @seat-selected="handleSeatSelected"
            />
          </div>
        </div>

        <template v-if="selectedSeat">
          <div class="info-row" style="margin-top: 15px">
            <span class="info-label">当前选择座位：</span>
            <el-tag type="info">{{ selectedSeat.name }}</el-tag>
          </div>
        </template>

        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button
            :disabled="!selectedSeat"
            type="primary"
            @click="confirmSeatSelection"
          >
            确认选择
          </el-button>
        </span>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import ReadingRoomSeat from "@/views/front/readingroom/ReadingRoomSeat.vue";

export default {
  name: "ReadingRoom",
  components: {ReadingRoomSeat},
  props: {
    roomOptions: {
      type: Array,
      required: true,
      default: () => [],
    },
  },
  data() {
    return {
      isFullscreen: false,
      selectedSeat: null,
      chooseRoomFlag: false,
      selectedRoomId: null, // 延迟设置
      currentRoom: {},
      canvasWidth: 800,
      canvasHeight: 600,
      dialogVisible: false,
      ctx: null,
    };
  },
  watch: {
    roomOptions: {
      handler(newVal) {
        if (Array.isArray(newVal) && newVal.length > 0) {
          // 补全缺失字段
          const processed = newVal.map((room) => ({
            ...room,
            width: room.width || 200, // 默认宽度
            height: room.height || 150, // 默认高度
          }));

          const defaultRoom =
            processed.find((room) => room.isOpen === 1) || processed[0];
          this.selectedRoomId = defaultRoom.id;
          this.currentRoom = defaultRoom;
          this.chooseRoomFlag = true;
          this.$nextTick(() => {
            this.initCanvas();
            this.drawRoom();
          });
        }
      },
      immediate: true,
    },
  },
  methods: {
    showDialog(room) {
      this.currentRoom = room;
      this.selectedSeat = null;
      this.isFullscreen = false;
      this.dialogVisible = true;
    },
    toggleFullscreen() {
      this.isFullscreen = !this.isFullscreen;
    },
    handleSeatSelected(seat) {
      this.selectedSeat = seat;
      this.$message.success(`你选择了座位：${seat.name}`);
    },
    initCanvas() {
      const canvas = this.$refs.libraryCanvas;
      this.ctx = canvas.getContext("2d");
    },

    changeRoom() {
      const rawRoom = this.roomOptions.find(
        (room) => room.id === this.selectedRoomId
      );

      // 兼容性补全字段
      const patchedRoom = {
        ...rawRoom,
        width: rawRoom.width || 200,
        height: rawRoom.height || 150,
      };

      this.currentRoom = patchedRoom;
      this.$nextTick(() => {
        this.drawRoom();
      });
    },
    drawRoom() {
      if (!this.currentRoom || !this.ctx) return;
      const ctx = this.ctx;
      ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

      const scale = Math.min(
        (this.canvasWidth - 100) / this.currentRoom.width,
        (this.canvasHeight - 100) / this.currentRoom.height
      );

      const roomWidth = this.currentRoom.width * scale;
      const roomHeight = this.currentRoom.height * scale;
      const startX = (this.canvasWidth - roomWidth) / 2;
      const startY = (this.canvasHeight - roomHeight) / 2;

      ctx.fillStyle = this.currentRoom.status === 1 ? "#f5f7fa" : "#fef0f0";
      ctx.fillRect(startX, startY, roomWidth, roomHeight);
      ctx.strokeStyle = this.currentRoom.status === 1 ? "#409EFF" : "#F56C6C";
      ctx.lineWidth = 3;
      ctx.strokeRect(startX, startY, roomWidth, roomHeight);

      ctx.fillStyle = "#333";
      ctx.font = "bold 20px Arial";
      ctx.textAlign = "center";
      ctx.fillText(
        this.currentRoom.roomName,
        this.canvasWidth / 2,
        startY - 20
      );

      if (this.currentRoom.id === 4) {
        this.drawComputerRoom(startX, startY, roomWidth, roomHeight);
      } else if (this.currentRoom.id === 2) {
        this.drawMeetingRoom(startX, startY, roomWidth, roomHeight);
      } else if (this.currentRoom.id === 5) {
        this.drawArtRoom(startX, startY, roomWidth, roomHeight);
      } else {
        this.drawStandardRoom(startX, startY, roomWidth, roomHeight);
      }

      if (this.currentRoom.status === 0) {
        ctx.fillStyle = "rgba(245, 108, 108, 0.5)";
        ctx.fillRect(startX, startY, roomWidth, roomHeight);

        ctx.fillStyle = "#F56C6C";
        ctx.font = "bold 30px Arial";
        ctx.textAlign = "center";
        ctx.fillText("维修中", this.canvasWidth / 2, this.canvasHeight / 2);
      }
    },

    drawComputerRoom(startX, startY, width, height) {
      const ctx = this.ctx;
      const computerWidth = 15;
      const computerHeight = 10;
      const gap = 10;

      for (let i = 0; i < 5; i++) {
        for (let j = 0; j < 5; j++) {
          const x = startX + gap + i * (computerWidth + gap);
          const y = startY + gap + j * (computerHeight + gap);

          ctx.fillStyle = "#333";
          ctx.fillRect(x, y, computerWidth, computerHeight * 0.7);
          ctx.fillStyle = "#666";
          ctx.fillRect(
            x + computerWidth * 0.3,
            y + computerHeight * 0.7,
            computerWidth * 0.4,
            computerHeight * 0.3
          );
        }
      }

      ctx.fillStyle = "#999";
      ctx.fillRect(startX + width - 30, startY + height / 2 - 15, 20, 30);

      ctx.fillStyle = "#409EFF";
      ctx.font = "12px Arial";
      ctx.textAlign = "center";
      ctx.fillText("计算机区", startX + width / 4, startY + height + 20);
      ctx.fillText("打印区", startX + width - 20, startY + height / 2 + 40);
    },

    drawMeetingRoom(startX, startY, width, height) {
      const ctx = this.ctx;
      ctx.fillStyle = "#D2B48C";
      ctx.fillRect(
        startX + width * 0.2,
        startY + height * 0.2,
        width * 0.6,
        height * 0.6
      );

      ctx.fillStyle = "#333";
      ctx.fillRect(startX + width * 0.45, startY + 10, 10, 15);

      ctx.strokeStyle = "#666";
      ctx.lineWidth = 2;
      ctx.strokeRect(
        startX + width * 0.3,
        startY + 30,
        width * 0.4,
        height * 0.15
      );
    },

    drawArtRoom(startX, startY, width, height) {
      const ctx = this.ctx;
      ctx.fillStyle = "#8B4513";
      ctx.fillRect(
        startX + width * 0.1,
        startY + height * 0.1,
        width * 0.8,
        height * 0.1
      );
      ctx.fillRect(
        startX + width * 0.1,
        startY + height * 0.3,
        width * 0.8,
        height * 0.1
      );
      ctx.fillRect(
        startX + width * 0.1,
        startY + height * 0.5,
        width * 0.8,
        height * 0.1
      );

      ctx.fillStyle = "#A0522D";
      for (let i = 0; i < 3; i++) {
        const x = startX + width * 0.2 + i * width * 0.3;
        const y = startY + height * 0.7;

        ctx.fillRect(x - 2, y, 4, 20);
        ctx.fillRect(x + 10, y, 4, 20);

        ctx.fillStyle = "#F5DEB3";
        ctx.fillRect(x, y - 15, 10, 15);
      }
    },

    drawStandardRoom(startX, startY, width, height) {
      const ctx = this.ctx;
      ctx.fillStyle = "#8B4513";
      ctx.fillRect(
        startX + width * 0.1,
        startY + height * 0.1,
        width * 0.8,
        height * 0.1
      );
      ctx.fillRect(
        startX + width * 0.1,
        startY + height * 0.7,
        width * 0.8,
        height * 0.1
      );

      for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 2; j++) {
          const x = startX + width * 0.2 + i * width * 0.3;
          const y = startY + height * 0.3 + j * height * 0.3;

          ctx.fillStyle = "#D2B48C";
          ctx.fillRect(x, y, width * 0.2, height * 0.15);
        }
      }

      if (this.currentRoom.id === 1) {
        ctx.fillStyle = "#409EFF";
        ctx.font = "bold 16px Arial";
        ctx.textAlign = "center";
        ctx.fillText("Wi-Fi", startX + width - 30, startY + 30);
      }
    },

    showRoomInfo() {
      this.dialogVisible = true;
    },
  },
};
</script>

<style scoped>
.library-container {
  margin: 20px;
}

.library-card {
  width: 100%;
}

.room-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.canvas-container {
  width: 100%;
  height: 600px;
  border: 1px solid #ebeef5;
  margin-bottom: 20px;
  background-color: #f5f7fa;
}

.room-info {
  padding: 10px;
}

.info-row {
  margin-bottom: 15px;
  display: flex;
  align-items: center;
}

.info-label {
  font-weight: bold;
  width: 100px;
}

.room-image {
  text-align: center;
  margin-top: 15px;
}

.room-info {
  padding: 0 10px;
}

.info-row {
  margin-bottom: 12px;
  line-height: 1.6;
}

.info-label {
  display: inline-block;
  width: 80px;
  font-weight: bold;
  color: #606266;
}

.room-image {
  text-align: center;
  margin: 20px 0;
}

.room-image img:hover {
  transform: scale(1.02);
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.fullscreen-btn {
  float: right;
  padding: 0;
  margin-left: 20px;
}

/* 对话框自定义样式 */
::v-deep .room-dialog {
  overflow: hidden;

  .fullscreen-mode {
    display: flex;
    justify-content: space-between;

    .fullscreen-btn {
      margin-right: 30px;
    }
  }
}


::v-deep .room-dialog .el-dialog__body {
  padding: 20px;
  max-height: calc(100vh - 200px);
  overflow-y: auto;
}

::v-deep .room-dialog .el-dialog__footer {
  border-top: 1px solid #eee;
  padding: 15px 20px;
  text-align: right;
}

/* 全屏模式下的样式调整 */
::v-deep .room-dialog.is-fullscreen {
  width: 100%;
  margin-top: 0 !important;
  height: 100%;
  overflow: auto;
}

::v-deep .room-dialog.is-fullscreen .el-dialog__body {
  max-height: none;
}


</style>
