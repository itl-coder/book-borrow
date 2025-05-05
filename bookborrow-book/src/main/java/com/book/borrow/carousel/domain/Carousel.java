package com.book.borrow.carousel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;

/**
 * 轮播图对象 carousel
 * 
 * @author bookborrow
 * @date 2025-05-05
 */
public class Carousel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 轮播图ID */
    private Long id;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 副标题 */
    @Excel(name = "副标题")
    private String subtitle;

    /** 图片地址 */
    @Excel(name = "图片地址")
    private String imageUrl;

    /** 跳转链接 */
    @Excel(name = "跳转链接")
    private String linkUrl;

    /** 排序值，越小越靠前 */
    @Excel(name = "排序值，越小越靠前")
    private Long sort;

    /** 状态（0=禁用，1=启用） */
    @Excel(name = "状态", readConverterExp = "0==禁用，1=启用")
    private Integer status;

    /** 是否已删除（0=未删除，1=已删除） */
    @Excel(name = "是否已删除", readConverterExp = "0==未删除，1=已删除")
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
    public void setSubtitle(String subtitle) 
    {
        this.subtitle = subtitle;
    }

    public String getSubtitle() 
    {
        return subtitle;
    }
    public void setImageUrl(String imageUrl) 
    {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() 
    {
        return imageUrl;
    }
    public void setLinkUrl(String linkUrl) 
    {
        this.linkUrl = linkUrl;
    }

    public String getLinkUrl() 
    {
        return linkUrl;
    }
    public void setSort(Long sort) 
    {
        this.sort = sort;
    }

    public Long getSort() 
    {
        return sort;
    }
    public void setStatus(Integer status) 
    {
        this.status = status;
    }

    public Integer getStatus() 
    {
        return status;
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
            .append("subtitle", getSubtitle())
            .append("imageUrl", getImageUrl())
            .append("linkUrl", getLinkUrl())
            .append("sort", getSort())
            .append("status", getStatus())
            .append("isDeleted", getIsDeleted())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
