package com.book.borrow.book.domain.vo;

import lombok.Data;

@Data
public class BookInfoCategoryVO {
    private Integer categoryId;
    private String categoryName;
    private String bookCount;
}
