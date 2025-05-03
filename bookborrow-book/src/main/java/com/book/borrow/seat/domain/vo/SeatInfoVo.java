package com.book.borrow.seat.domain.vo;

import com.book.borrow.seat.domain.SeatInfo;
import lombok.Data;

@Data
public class SeatInfoVo extends SeatInfo {
    private String roomName;
}
