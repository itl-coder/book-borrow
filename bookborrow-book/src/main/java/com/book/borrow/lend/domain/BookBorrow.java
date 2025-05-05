package com.book.borrow.lend.domain;

import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图书借阅关系对象 book_borrow
 *
 * @author bookborrow
 * @date 2025-05-05
 */
@Data
public class BookBorrow extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 用户ID
     */
    @Excel(name = "用户ID")
    private Long userId;

    /**
     * 用户名（冗余字段）
     */
    @Excel(name = "用户名", readConverterExp = "冗=余字段")
    private String userName;

    /**
     * 图书ID
     */
    @Excel(name = "图书ID")
    private Long bookId;

    /**
     * 图书名称（冗余字段）
     */
    @Excel(name = "图书名称", readConverterExp = "冗=余字段")
    private String bookName;

    /**
     * 分类ID
     */
    @Excel(name = "分类ID")
    private Long categoryId;

    /**
     * 分类名称
     */
    @Excel(name = "分类名称")
    private String categoryName;

    /**
     * 借出时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "借出时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date borrowTime;

    /**
     * 应还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "应还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date dueTime;

    /**
     * 实际归还时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "实际归还时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date returnTime;

    /**
     * 借阅状态：0借出中，1已归还，2逾期
     */
    @Excel(name = "借阅状态：0借出中，1已归还，2逾期")
    private Long status;

    /**
     * 逾期缴费金额（当状态为逾期时使用）
     */
    @Excel(name = "逾期缴费金额", readConverterExp = "当=状态为逾期时使用")
    private BigDecimal lateFee;

    /**
     * 逻辑删除：0正常，1删除
     */
    @Excel(name = "逻辑删除：0正常，1删除")
    private Integer deleted;

    /**
     * 预览图
     */
    @Excel(name = "预览图")
    private String imgCover;
}
