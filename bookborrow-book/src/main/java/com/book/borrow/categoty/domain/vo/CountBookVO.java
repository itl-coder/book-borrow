package com.book.borrow.categoty.domain.vo;

import com.book.borrow.common.annotation.Excel;
import lombok.Data;

@Data
public class CountBookVO {
    /** 分类ID */
    private Long id;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String categoryName;

}
