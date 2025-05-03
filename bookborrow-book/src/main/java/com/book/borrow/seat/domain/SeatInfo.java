package com.book.borrow.seat.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;

/**
 * 座位信息对象 seat_info
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
public class SeatInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 座位ID */
    private Long id;

    /** 阅览室ID（关联 reading_room.id） */
    @Excel(name = "阅览室ID", readConverterExp = "关=联,r=eading_room.id")
    private Long readingRoomId;

    /** 座位排号（行号） */
    @Excel(name = "座位排号", readConverterExp = "行=号")
    private Long rowNum;

    /** 座位列号（列号） */
    @Excel(name = "座位列号", readConverterExp = "列=号")
    private Long colNum;

    /** 座位是否可用，1为可用，0为不可用 */
    @Excel(name = "座位是否可用，1为可用，0为不可用")
    private Integer isAvailable;

    /** 座位是否已预约，0为未预约，1为已预约 */
    @Excel(name = "座位是否已预约，0为未预约，1为已预约")
    private Integer isReserved;

    /** 座位描述（如靠窗、靠近插座等） */
    @Excel(name = "座位描述", readConverterExp = "如=靠窗、靠近插座等")
    private String description;

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
    public void setReadingRoomId(Long readingRoomId) 
    {
        this.readingRoomId = readingRoomId;
    }

    public Long getReadingRoomId() 
    {
        return readingRoomId;
    }
    public void setRowNum(Long rowNum) 
    {
        this.rowNum = rowNum;
    }

    public Long getRowNum() 
    {
        return rowNum;
    }
    public void setColNum(Long colNum) 
    {
        this.colNum = colNum;
    }

    public Long getColNum() 
    {
        return colNum;
    }
    public void setIsAvailable(Integer isAvailable) 
    {
        this.isAvailable = isAvailable;
    }

    public Integer getIsAvailable() 
    {
        return isAvailable;
    }
    public void setIsReserved(Integer isReserved) 
    {
        this.isReserved = isReserved;
    }

    public Integer getIsReserved() 
    {
        return isReserved;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
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
            .append("readingRoomId", getReadingRoomId())
            .append("rowNum", getRowNum())
            .append("colNum", getColNum())
            .append("isAvailable", getIsAvailable())
            .append("isReserved", getIsReserved())
            .append("description", getDescription())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
