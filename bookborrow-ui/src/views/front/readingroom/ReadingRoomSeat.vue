<template>
  <div class="room-container">
    <el-card class="room-card" shadow="hover">
      <div slot="header">
        <span>{{ room.name }}</span>
        <el-button
          style="float: right; padding: 3px 0"
          type="text"
          @click="refreshRoom"
        >
          刷新
        </el-button>
      </div>

      <div class="canvas-container">
        <canvas
          ref="roomCanvas"
          :width="room.width"
          :height="room.height"
          @click="handleCanvasClick"
        ></canvas>
      </div>

      <div class="legend">
        <div class="legend-item">
          <div class="color-box available"></div>
          <span>可用座位</span>
        </div>
        <div class="legend-item">
          <div class="color-box occupied"></div>
          <span>已占用</span>
        </div>
        <div class="legend-item">
          <div class="color-box selected"></div>
          <span>已选择</span>
        </div>
        <div class="legend-item">
          <div class="color-box bookshelf"></div>
          <span>书架</span>
        </div>
        <div class="legend-item">
          <div class="color-box aisle"></div>
          <span>走道</span>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: 'LibraryRoom',
  props: {
    roomId: {
      type: String,
      default: 'room1'
    }
  },
  data() {
    return {
      room: {
        id: '',
        name: '',
        width: 1000,
        height: 600,
        seats: [],
        bookshelves: [],
        aisles: []
      },
      selectedSeat: null,
      ctx: null,
      scale: 1.0
    }
  },
  mounted() {
    this.initCanvas();
    this.$nextTick(() => {
      this.generateLibraryLayout();
    });
  },
  methods: {
    initCanvas() {
      const canvas = this.$refs.roomCanvas;
      this.ctx = canvas.getContext('2d');
      this.adjustCanvasSize();
      window.addEventListener('resize', this.adjustCanvasSize);
    },

    adjustCanvasSize() {
      const container = this.$el.querySelector('.canvas-container');
      const canvas = this.$refs.roomCanvas;

      const containerWidth = container.clientWidth;
      this.scale = Math.min(containerWidth / this.room.width, 1.0);

      canvas.style.width = `${this.room.width * this.scale}px`;
      canvas.style.height = `${this.room.height * this.scale}px`;
    },

    generateLibraryLayout() {
      this.room = {
        id: this.roomId,
        name: `${this.roomId.replace('room', '')}号阅览室`,
        width: 1000,
        height: 700,
        seats: [],
        bookshelves: [],
        aisles: []
      };

      // 添加书架
      this.addBookshelves();

      // 添加阅读区
      this.addReadingAreas();

      // 添加走道
      this.addAisles();

      this.drawRoom();
    },

    addBookshelves() {
      // 四周的书架
      this.room.bookshelves.push(
        { x: 0, y: 0, width: 40, height: this.room.height }, // 左侧
        { x: this.room.width - 40, y: 0, width: 40, height: this.room.height }, // 右侧
        { x: 40, y: 0, width: this.room.width - 80, height: 30 }, // 上方
        { x: 40, y: this.room.height - 30, width: this.room.width - 80, height: 30 } // 下方
      );

      // 中间的书架 (垂直)
      for (let i = 0; i < 3; i++) {
        this.room.bookshelves.push({
          x: 300 + i * 200,
          y: 40,
          width: 30,
          height: this.room.height - 70
        });
      }
    },

    addReadingAreas() {
      // 左侧阅读区
      this.addReadingTables(50, 40, 240, this.room.height - 70, 4, 8);

      // 中间阅读区 (书架之间)
      this.addReadingTables(340, 40, 160, this.room.height - 70, 2, 8);
      this.addReadingTables(540, 40, 160, this.room.height - 70, 2, 8);

      // 右侧阅读区
      this.addReadingTables(740, 40, 220, this.room.height - 70, 4, 8);
    },

    addReadingTables(startX, startY, areaWidth, areaHeight, cols, rows) {
      const tableWidth = 80;
      const tableHeight = 50;
      const gapX = (areaWidth - cols * tableWidth) / (cols + 1);
      const gapY = 20;

      for (let row = 0; row < rows; row++) {
        for (let col = 0; col < cols; col++) {
          this.room.seats.push({
            id: `seat-${startX}-${startY}-${row}-${col}`,
            name: `${this.room.seats.length + 1}`,
            x: startX + col * (tableWidth + gapX) + gapX,
            y: startY + row * (tableHeight + gapY) + gapY,
            width: tableWidth,
            height: tableHeight,
            occupied: Math.random() > 0.7,
            studentId: Math.random() > 0.7 ? `stu-${Math.floor(Math.random() * 10000)}` : null
          });
        }
      }
    },

    addAisles() {
      // 主走道 (水平)
      this.room.aisles.push({
        x: 40,
        y: 350,
        width: this.room.width - 80,
        height: 20
      });

      // 垂直走道 (书架之间)
      for (let i = 0; i < 2; i++) {
        this.room.aisles.push({
          x: 330 + i * 200,
          y: 40,
          width: 10,
          height: this.room.height - 70
        });
      }
    },

    drawRoom() {
      this.ctx.clearRect(0, 0, this.room.width, this.room.height);

      // 绘制背景 (地板)
      this.ctx.fillStyle = '#f8f4e9';
      this.ctx.fillRect(0, 0, this.room.width, this.room.height);

      // 绘制走道
      this.room.aisles.forEach(aisle => {
        this.ctx.fillStyle = '#e0d8c0';
        this.ctx.fillRect(aisle.x, aisle.y, aisle.width, aisle.height);
        this.ctx.strokeStyle = '#c0b8a0';
        this.ctx.strokeRect(aisle.x, aisle.y, aisle.width, aisle.height);
      });

      // 绘制书架
      this.room.bookshelves.forEach(shelf => {
        this.ctx.fillStyle = '#8B4513'; // 棕色书架
        this.ctx.fillRect(shelf.x, shelf.y, shelf.width, shelf.height);

        // 书架细节 (书)
        this.ctx.fillStyle = '#5D4037';
        for (let i = 0; i < 10; i++) {
          const bookHeight = shelf.height - 10;
          const bookWidth = 5 + Math.random() * 3;
          const bookX = shelf.x + 5 + i * (shelf.width / 10);
          this.ctx.fillRect(bookX, shelf.y + 5, bookWidth, bookHeight);
        }
      });

      // 绘制座位
      this.room.seats.forEach(seat => {
        // 桌子
        this.ctx.fillStyle = '#D2B48C'; // 浅木色桌子
        this.ctx.fillRect(seat.x, seat.y, seat.width, seat.height);
        this.ctx.strokeStyle = '#8B4513';
        this.ctx.lineWidth = 1;
        this.ctx.strokeRect(seat.x, seat.y, seat.width, seat.height);

        // 椅子/座位状态
        const chairSize = 15;
        const chairX = seat.x + seat.width / 2 - chairSize / 2;
        const chairY = seat.y - chairSize / 2;

        if (seat === this.selectedSeat) {
          this.ctx.fillStyle = '#67C23A'; // 已选择
        } else if (seat.occupied) {
          this.ctx.fillStyle = '#F56C6C'; // 已占用
        } else {
          this.ctx.fillStyle = '#409EFF'; // 可用
        }

        this.ctx.beginPath();
        this.ctx.arc(chairX + chairSize/2, chairY + chairSize/2, chairSize/2, 0, Math.PI * 2);
        this.ctx.fill();
        this.ctx.strokeStyle = '#333';
        this.ctx.stroke();

        // 座位编号
        this.ctx.fillStyle = '#333';
        this.ctx.font = '12px Arial';
        this.ctx.textAlign = 'center';
        this.ctx.fillText(seat.name, seat.x + seat.width / 2, seat.y + seat.height / 2 + 5);
      });

      // 绘制房间标题
      this.ctx.fillStyle = '#333';
      this.ctx.font = 'bold 24px Arial';
      this.ctx.textAlign = 'center';
      this.ctx.fillText(this.room.name, this.room.width / 2, 30);

      // 绘制入口标记
      this.ctx.fillStyle = '#333';
      this.ctx.font = 'bold 16px Arial';
      this.ctx.textAlign = 'left';
      this.ctx.fillText('入口', 50, 370);
    },

    handleCanvasClick(event) {
      const canvas = this.$refs.roomCanvas;
      const rect = canvas.getBoundingClientRect();

      const x = (event.clientX - rect.left) / this.scale;
      const y = (event.clientY - rect.top) / this.scale;

      const clickedSeat = this.room.seats.find(seat =>
        x >= seat.x && x <= seat.x + seat.width &&
        y >= seat.y && y <= seat.y + seat.height
      );

      if (clickedSeat) {
        if (clickedSeat.occupied) {
          this.$message.warning(`座位 ${clickedSeat.name} 已被 ${clickedSeat.studentId} 占用`);
        } else {
          this.selectedSeat = clickedSeat;
          this.drawRoom();
          this.$emit('seat-selected', clickedSeat);
        }
      }
    },

    refreshRoom() {
      this.selectedSeat = null;
      this.generateLibraryLayout();
    }
  },

  beforeDestroy() {
    window.removeEventListener('resize', this.adjustCanvasSize);
  }
}
</script>

<style scoped>
.room-container {
  margin: 20px;
}

.room-card {
  width: 100%;
}

.canvas-container {
  width: 100%;
  overflow: auto;
  border: 1px solid #ebeef5;
  margin-bottom: 20px;
  text-align: center;
  background-color: #f8f4e9;
}

canvas {
  background-color: #f8f4e9;
  margin: 0 auto;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.legend {
  display: flex;
  justify-content: center;
  margin-top: 15px;
  flex-wrap: wrap;
}

.legend-item {
  display: flex;
  align-items: center;
  margin: 0 15px 10px;
}

.color-box {
  width: 20px;
  height: 20px;
  margin-right: 8px;
  border: 1px solid #333;
}

.available {
  background-color: #409EFF;
}

.occupied {
  background-color: #F56C6C;
}

.selected {
  background-color: #67C23A;
}

.bookshelf {
  background-color: #8B4513;
}

.aisle {
  background-color: #e0d8c0;
}
</style>
