package com.book.borrow.lend.domain.vo;

import com.book.borrow.lend.domain.BookBorrow;
import lombok.Data;

@Data
public class BookBorrowVO extends BookBorrow {
    private Long stock;
}
