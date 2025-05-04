package com.book.borrow.book.domain;

import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 图书信息（无外键约束）对象 book_info
 *
 * @author bookborrow
 * @date 2025-05-03
 */
@Data
public class BookInfo extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 图书ID
     */
    private Long id;
    @Excel(name = "ISBN")
    private String isbn;
    // 借出次数
    private Integer borrowTime;

    /**
     * 图书标题
     */
    @Excel(name = "图书标题")
    private String title;

    /**
     * 作者
     */
    @Excel(name = "作者")
    private String author;

    /**
     * 出版社
     */
    @Excel(name = "出版社")
    private String publisher;

    /**
     * 出版日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /**
     * 分类ID（关联 book_category.id，无外键约束）
     */
    @Excel(name = "分类ID", readConverterExp = "关=联,b=ook_category.id，无外键约束")
    private Long categoryId;

    /**
     * 分类名称冗余字段，便于查询展示
     */
    @Excel(name = "分类名称冗余字段，便于查询展示")
    private String categoryName;

    /**
     * 价格
     */
    @Excel(name = "价格")
    private BigDecimal price;

    /**
     * 库存数量
     */
    @Excel(name = "库存数量")
    private Long stock;

    /**
     * 借阅状态，0为可借，1为已借出
     */
    @Excel(name = "借阅状态，0为可借，1为已借出")
    private Integer borrowStatus;

    /**
     * 图书简介
     */
    @Excel(name = "图书简介")
    private String description;

    /**
     * 是否上架，1为上架，0为下架
     */
    @Excel(name = "是否上架，1为上架，0为下架")
    private Integer isPublished;

    /**
     * 是否推荐，1为推荐，0为普通
     */
    @Excel(name = "是否推荐，1为推荐，0为普通")
    private Integer isRecommended;

    /**
     * 图书封面URL
     */
    @Excel(name = "图书封面URL")
    private String coverUrl;

    /**
     * 逻辑删除标志，0为未删除，1为已删除
     */
    @Excel(name = "逻辑删除标志，0为未删除，1为已删除")
    private Integer isDeleted;
}
