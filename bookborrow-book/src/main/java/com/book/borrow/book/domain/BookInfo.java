package com.book.borrow.book.domain;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;

/**
 * 图书信息（无外键约束）对象 book_info
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
public class BookInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图书ID */
    private Long id;

    /** 图书标题 */
    @Excel(name = "图书标题")
    private String title;

    /** 作者 */
    @Excel(name = "作者")
    private String author;

    /** 出版社 */
    @Excel(name = "出版社")
    private String publisher;

    /** 出版日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出版日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date publishDate;

    /** 分类ID（关联 book_category.id，无外键约束） */
    @Excel(name = "分类ID", readConverterExp = "关=联,b=ook_category.id，无外键约束")
    private Long categoryId;

    /** 分类名称冗余字段，便于查询展示 */
    @Excel(name = "分类名称冗余字段，便于查询展示")
    private String categoryName;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 库存数量 */
    @Excel(name = "库存数量")
    private Long stock;

    /** 借阅状态，0为可借，1为已借出 */
    @Excel(name = "借阅状态，0为可借，1为已借出")
    private Integer borrowStatus;

    /** 图书简介 */
    @Excel(name = "图书简介")
    private String description;

    /** 是否上架，1为上架，0为下架 */
    @Excel(name = "是否上架，1为上架，0为下架")
    private Integer isPublished;

    /** 是否推荐，1为推荐，0为普通 */
    @Excel(name = "是否推荐，1为推荐，0为普通")
    private Integer isRecommended;

    /** 图书封面URL */
    @Excel(name = "图书封面URL")
    private String coverUrl;

    /** 逻辑删除标志，0为未删除，1为已删除 */
    @Excel(name = "逻辑删除标志，0为未删除，1为已删除")
    private Integer isDeleted;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setAuthor(String author) 
    {
        this.author = author;
    }

    public String getAuthor() 
    {
        return author;
    }
    public void setPublisher(String publisher) 
    {
        this.publisher = publisher;
    }

    public String getPublisher() 
    {
        return publisher;
    }
    public void setPublishDate(Date publishDate) 
    {
        this.publishDate = publishDate;
    }

    public Date getPublishDate() 
    {
        return publishDate;
    }
    public void setCategoryId(Long categoryId) 
    {
        this.categoryId = categoryId;
    }

    public Long getCategoryId() 
    {
        return categoryId;
    }
    public void setCategoryName(String categoryName) 
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() 
    {
        return categoryName;
    }
    public void setPrice(BigDecimal price) 
    {
        this.price = price;
    }

    public BigDecimal getPrice() 
    {
        return price;
    }
    public void setStock(Long stock) 
    {
        this.stock = stock;
    }

    public Long getStock() 
    {
        return stock;
    }
    public void setBorrowStatus(Integer borrowStatus) 
    {
        this.borrowStatus = borrowStatus;
    }

    public Integer getBorrowStatus() 
    {
        return borrowStatus;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setIsPublished(Integer isPublished) 
    {
        this.isPublished = isPublished;
    }

    public Integer getIsPublished() 
    {
        return isPublished;
    }
    public void setIsRecommended(Integer isRecommended) 
    {
        this.isRecommended = isRecommended;
    }

    public Integer getIsRecommended() 
    {
        return isRecommended;
    }
    public void setCoverUrl(String coverUrl) 
    {
        this.coverUrl = coverUrl;
    }

    public String getCoverUrl() 
    {
        return coverUrl;
    }
    public void setIsDeleted(Integer isDeleted) 
    {
        this.isDeleted = isDeleted;
    }

    public Integer getIsDeleted() 
    {
        return isDeleted;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("author", getAuthor())
            .append("publisher", getPublisher())
            .append("publishDate", getPublishDate())
            .append("categoryId", getCategoryId())
            .append("categoryName", getCategoryName())
            .append("price", getPrice())
            .append("stock", getStock())
            .append("borrowStatus", getBorrowStatus())
            .append("description", getDescription())
            .append("isPublished", getIsPublished())
            .append("isRecommended", getIsRecommended())
            .append("coverUrl", getCoverUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
