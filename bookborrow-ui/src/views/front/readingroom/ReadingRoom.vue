<template>
  <div class="library-container">
    <el-card class="library-card">
      <div slot="header" class="room-header">
        <span>{{ currentRoom.name }} ({{ currentRoom.location }})</span>
        <el-select
          v-model="selectedRoomId"
          placeholder="选择阅览室"
          @change="changeRoom"
          style="width: 200px; margin-left: 20px;"
        >
          <el-option
            v-for="room in rooms"
            :key="room.id"
            :label="room.name"
            :value="room.id"
            :disabled="room.status === 0"
          >
            <span style="float: left">{{ room.name }}</span>
            <span style="float: right; color: #8492a6; font-size: 13px">
              {{ room.status === 0 ? '维修中' : '可预约' }}
            </span>
          </el-option>
        </el-select>
      </div>

      <div class="canvas-container">
        <canvas
          ref="libraryCanvas"
          :width="canvasWidth"
          :height="canvasHeight"
          @click="showRoomInfo"
        ></canvas>
      </div>

      <el-dialog :title="currentRoom.name + ' - 详细信息'" :visible.sync="dialogVisible" width="50%">
        <div class="room-info">
          <div class="info-row">
            <span class="info-label">位置：</span>
            <span>{{ currentRoom.location }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">开放时间：</span>
            <span>{{ currentRoom.openHours }}</span>
          </div>
          <div class="info-row">
            <span class="info-label">状态：</span>
            <el-tag :type="currentRoom.status === 1 ? 'success' : 'danger'">
              {{ currentRoom.status === 1 ? '开放中' : '维修中' }}
            </el-tag>
          </div>
          <div class="info-row">
            <span class="info-label">描述：</span>
            <span>{{ currentRoom.description }}</span>
          </div>
          <div class="info-row" v-if="currentRoom.id === 4">
            <span class="info-label">特殊服务：</span>
            <el-tag type="warning">计算机使用</el-tag>
            <el-tag type="success" style="margin-left: 10px;">打印服务</el-tag>
          </div>
          <div class="room-image">
            <img :src="currentRoom.imageUrl" alt="阅览室图片" style="max-width: 100%; margin-top: 15px;">
          </div>
        </div>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'LibraryRoomModel',
  data() {
    return {
      rooms: [
        {
          id: 1,
          name: '第一阅览室',
          location: '一楼东侧',
          width: 50,
          height: 45,
          status: 1,
          openHours: '09:00-18:00',
          description: '适合安静阅读，提供免费Wi-Fi',
          imageUrl: 'https://example.com/images/reading_room_1.jpg'
        },
        {
          id: 2,
          name: '第二阅览室',
          location: '二楼西侧',
          width: 40,
          height: 30,
          status: 1,
          openHours: '09:00-18:00',
          description: '适合小组讨论和协作学习，提供投影仪',
          imageUrl: 'https://example.com/images/reading_room_2.jpg'
        },
        {
          id: 3,
          name: '第三阅览室',
          location: '三楼北侧',
          width: 60,
          height: 60,
          status: 0,
          openHours: '09:00-20:00',
          description: '阅览室目前关闭进行维修',
          imageUrl: 'https://example.com/images/reading_room_3.jpg'
        },
        {
          id: 4,
          name: '计算机阅览室',
          location: '一楼南侧',
          width: 25,
          height: 20,
          status: 1,
          openHours: '09:00-17:00',
          description: '专门提供计算机使用，支持打印服务',
          imageUrl: 'https://example.com/images/reading_room_4.jpg',
          computers: 25,
          printers: 2
        },
        {
          id: 5,
          name: '艺术阅览室',
          location: '四楼东侧',
          width: 20,
          height: 15,
          status: 1,
          openHours: '10:00-18:00',
          description: '艺术类书籍及画册专用阅览区',
          imageUrl: 'https://example.com/images/reading_room_5.jpg'
        }
      ],
      selectedRoomId: 4, // 默认显示计算机阅览室
      currentRoom: {},
      canvasWidth: 800,
      canvasHeight: 600,
      dialogVisible: false,
      ctx: null
    }
  },
  mounted() {
    this.currentRoom = this.rooms.find(room => room.id === this.selectedRoomId);
    this.initCanvas();
    this.drawRoom();
  },
  methods: {
    initCanvas() {
      const canvas = this.$refs.libraryCanvas;
      this.ctx = canvas.getContext('2d');
    },

    changeRoom() {
      this.currentRoom = this.rooms.find(room => room.id === this.selectedRoomId);
      this.drawRoom();
    },

    drawRoom() {
      const ctx = this.ctx;
      ctx.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

      // 设置缩放比例，使房间适应画布
      const scale = Math.min(
        (this.canvasWidth - 100) / this.currentRoom.width,
        (this.canvasHeight - 100) / this.currentRoom.height
      );

      const roomWidth = this.currentRoom.width * scale;
      const roomHeight = this.currentRoom.height * scale;
      const startX = (this.canvasWidth - roomWidth) / 2;
      const startY = (this.canvasHeight - roomHeight) / 2;

      // 绘制房间轮廓
      ctx.fillStyle = this.currentRoom.status === 1 ? '#f5f7fa' : '#fef0f0';
      ctx.fillRect(startX, startY, roomWidth, roomHeight);
      ctx.strokeStyle = this.currentRoom.status === 1 ? '#409EFF' : '#F56C6C';
      ctx.lineWidth = 3;
      ctx.strokeRect(startX, startY, roomWidth, roomHeight);

      // 绘制房间名称
      ctx.fillStyle = '#333';
      ctx.font = 'bold 20px Arial';
      ctx.textAlign = 'center';
      ctx.fillText(this.currentRoom.name, this.canvasWidth / 2, startY - 20);

      // 根据不同阅览室类型绘制不同布局
      if (this.currentRoom.id === 4) {
        this.drawComputerRoom(startX, startY, roomWidth, roomHeight);
      } else if (this.currentRoom.id === 2) {
        this.drawMeetingRoom(startX, startY, roomWidth, roomHeight);
      } else if (this.currentRoom.id === 5) {
        this.drawArtRoom(startX, startY, roomWidth, roomHeight);
      } else {
        this.drawStandardRoom(startX, startY, roomWidth, roomHeight);
      }

      // 如果是维修中的房间，添加维修标识
      if (this.currentRoom.status === 0) {
        ctx.fillStyle = 'rgba(245, 108, 108, 0.5)';
        ctx.fillRect(startX, startY, roomWidth, roomHeight);

        ctx.fillStyle = '#F56C6C';
        ctx.font = 'bold 30px Arial';
        ctx.textAlign = 'center';
        ctx.fillText('维修中', this.canvasWidth / 2, this.canvasHeight / 2);
      }
    },

    drawComputerRoom(startX, startY, width, height) {
      const ctx = this.ctx;
      const computerWidth = 15;
      const computerHeight = 10;
      const gap = 10;

      // 绘制计算机工作站
      for (let i = 0; i < 5; i++) {
        for (let j = 0; j < 5; j++) {
          const x = startX + gap + i * (computerWidth + gap);
          const y = startY + gap + j * (computerHeight + gap);

          // 计算机屏幕
          ctx.fillStyle = '#333';
          ctx.fillRect(x, y, computerWidth, computerHeight * 0.7);

          // 计算机底座
          ctx.fillStyle = '#666';
          ctx.fillRect(x + computerWidth * 0.3, y + computerHeight * 0.7,
            computerWidth * 0.4, computerHeight * 0.3);
        }
      }

      // 绘制打印机区域
      ctx.fillStyle = '#999';
      ctx.fillRect(startX + width - 30, startY + height / 2 - 15, 20, 30);

      // 添加标签
      ctx.fillStyle = '#409EFF';
      ctx.font = '12px Arial';
      ctx.textAlign = 'center';
      ctx.fillText('计算机区', startX + width / 4, startY + height + 20);
      ctx.fillText('打印区', startX + width - 20, startY + height / 2 + 40);
    },

    drawMeetingRoom(startX, startY, width, height) {
      const ctx = this.ctx;

      // 绘制会议桌
      ctx.fillStyle = '#D2B48C';
      ctx.fillRect(startX + width * 0.2, startY + height * 0.2, width * 0.6, height * 0.6);

      // 绘制投影仪
      ctx.fillStyle = '#333';
      ctx.fillRect(startX + width * 0.45, startY + 10, 10, 15);

      // 绘制投影屏幕
      ctx.strokeStyle = '#666';
      ctx.lineWidth = 2;
      ctx.strokeRect(startX + width * 0.3, startY + 30, width * 0.4, height * 0.15);
    },

    drawArtRoom(startX, startY, width, height) {
      const ctx = this.ctx;

      // 绘制书架
      ctx.fillStyle = '#8B4513';
      ctx.fillRect(startX + width * 0.1, startY + height * 0.1, width * 0.8, height * 0.1);
      ctx.fillRect(startX + width * 0.1, startY + height * 0.3, width * 0.8, height * 0.1);
      ctx.fillRect(startX + width * 0.1, startY + height * 0.5, width * 0.8, height * 0.1);

      // 绘制画架
      ctx.fillStyle = '#A0522D';
      for (let i = 0; i < 3; i++) {
        const x = startX + width * 0.2 + i * width * 0.3;
        const y = startY + height * 0.7;

        // 画架腿
        ctx.fillRect(x - 2, y, 4, 20);
        ctx.fillRect(x + 10, y, 4, 20);

        // 画板
        ctx.fillStyle = '#F5DEB3';
        ctx.fillRect(x, y - 15, 10, 15);
      }
    },

    drawStandardRoom(startX, startY, width, height) {
      const ctx = this.ctx;

      // 绘制书架
      ctx.fillStyle = '#8B4513';
      ctx.fillRect(startX + width * 0.1, startY + height * 0.1, width * 0.8, height * 0.1);
      ctx.fillRect(startX + width * 0.1, startY + height * 0.7, width * 0.8, height * 0.1);

      // 绘制阅读桌
      for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 2; j++) {
          const x = startX + width * 0.2 + i * width * 0.3;
          const y = startY + height * 0.3 + j * height * 0.3;

          ctx.fillStyle = '#D2B48C';
          ctx.fillRect(x, y, width * 0.2, height * 0.15);
        }
      }

      // 绘制Wi-Fi标志（如果是第一阅览室）
      if (this.currentRoom.id === 1) {
        ctx.fillStyle = '#409EFF';
        ctx.font = 'bold 16px Arial';
        ctx.textAlign = 'center';
        ctx.fillText('Wi-Fi', startX + width - 30, startY + 30);
      }
    },

    showRoomInfo() {
      this.dialogVisible = true;
    }
  }
}
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
</style>
