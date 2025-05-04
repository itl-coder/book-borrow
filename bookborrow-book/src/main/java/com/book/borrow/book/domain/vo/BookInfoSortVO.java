package com.book.borrow.book.domain.vo;

import com.book.borrow.book.domain.BookInfo;
import lombok.Data;

@Data
public class BookInfoSortVO extends BookInfo {
    private String sortField; // 排序的字段
    private String sortOrder; // 排序方式
}
