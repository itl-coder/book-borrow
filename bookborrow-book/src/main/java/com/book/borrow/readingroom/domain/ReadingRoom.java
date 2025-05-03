package com.book.borrow.readingroom.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.book.borrow.common.annotation.Excel;
import com.book.borrow.common.core.domain.BaseEntity;

/**
 * 阅览室信息对象 reading_room
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
public class ReadingRoom extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 阅览室ID */
    private Long id;

    /** 阅览室名称 */
    @Excel(name = "阅览室名称")
    private String roomName;

    /** 阅览室位置（如楼层、房间号等） */
    @Excel(name = "阅览室位置", readConverterExp = "如=楼层、房间号等")
    private String location;

    /** 阅览室座位总数 */
    @Excel(name = "阅览室座位总数")
    private Long totalSeats;

    /** 可用座位数 */
    @Excel(name = "可用座位数")
    private Long availableSeats;

    /** 阅览室是否开放，1为开放，0为关闭 */
    @Excel(name = "阅览室是否开放，1为开放，0为关闭")
    private Integer isOpen;

    /** 开放时间（如：9:00-18:00） */
    @Excel(name = "开放时间", readConverterExp = "如=：9:00-18:00")
    private String openingHours;

    /** 阅览室简介 */
    @Excel(name = "阅览室简介")
    private String description;

    /** 阅览室封面图URL */
    @Excel(name = "阅览室封面图URL")
    private String coverImageUrl;

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
    public void setRoomName(String roomName) 
    {
        this.roomName = roomName;
    }

    public String getRoomName() 
    {
        return roomName;
    }
    public void setLocation(String location) 
    {
        this.location = location;
    }

    public String getLocation() 
    {
        return location;
    }
    public void setTotalSeats(Long totalSeats) 
    {
        this.totalSeats = totalSeats;
    }

    public Long getTotalSeats() 
    {
        return totalSeats;
    }
    public void setAvailableSeats(Long availableSeats) 
    {
        this.availableSeats = availableSeats;
    }

    public Long getAvailableSeats() 
    {
        return availableSeats;
    }
    public void setIsOpen(Integer isOpen) 
    {
        this.isOpen = isOpen;
    }

    public Integer getIsOpen() 
    {
        return isOpen;
    }
    public void setOpeningHours(String openingHours) 
    {
        this.openingHours = openingHours;
    }

    public String getOpeningHours() 
    {
        return openingHours;
    }
    public void setDescription(String description) 
    {
        this.description = description;
    }

    public String getDescription() 
    {
        return description;
    }
    public void setCoverImageUrl(String coverImageUrl) 
    {
        this.coverImageUrl = coverImageUrl;
    }

    public String getCoverImageUrl() 
    {
        return coverImageUrl;
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
            .append("roomName", getRoomName())
            .append("location", getLocation())
            .append("totalSeats", getTotalSeats())
            .append("availableSeats", getAvailableSeats())
            .append("isOpen", getIsOpen())
            .append("openingHours", getOpeningHours())
            .append("description", getDescription())
            .append("coverImageUrl", getCoverImageUrl())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
