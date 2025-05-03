package com.book.borrow.seat.service;

import java.util.List;
import com.book.borrow.seat.domain.SeatInfo;

/**
 * 座位信息Service接口
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
public interface ISeatInfoService 
{
    /**
     * 查询座位信息
     * 
     * @param id 座位信息主键
     * @return 座位信息
     */
    public SeatInfo selectSeatInfoById(Long id);

    /**
     * 查询座位信息列表
     * 
     * @param seatInfo 座位信息
     * @return 座位信息集合
     */
    public List<SeatInfo> selectSeatInfoList(SeatInfo seatInfo);

    /**
     * 新增座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    public int insertSeatInfo(SeatInfo seatInfo);

    /**
     * 修改座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    public int updateSeatInfo(SeatInfo seatInfo);

    /**
     * 批量删除座位信息
     * 
     * @param ids 需要删除的座位信息主键集合
     * @return 结果
     */
    public int deleteSeatInfoByIds(Long[] ids);

    /**
     * 删除座位信息信息
     * 
     * @param id 座位信息主键
     * @return 结果
     */
    public int deleteSeatInfoById(Long id);
}
