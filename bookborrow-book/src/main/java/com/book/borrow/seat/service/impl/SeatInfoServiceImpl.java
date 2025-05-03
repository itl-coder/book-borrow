package com.book.borrow.seat.service.impl;

import java.util.List;
import com.book.borrow.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.borrow.seat.mapper.SeatInfoMapper;
import com.book.borrow.seat.domain.SeatInfo;
import com.book.borrow.seat.service.ISeatInfoService;

/**
 * 座位信息Service业务层处理
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@Service
public class SeatInfoServiceImpl implements ISeatInfoService 
{
    @Autowired
    private SeatInfoMapper seatInfoMapper;

    /**
     * 查询座位信息
     * 
     * @param id 座位信息主键
     * @return 座位信息
     */
    @Override
    public SeatInfo selectSeatInfoById(Long id)
    {
        return seatInfoMapper.selectSeatInfoById(id);
    }

    /**
     * 查询座位信息列表
     * 
     * @param seatInfo 座位信息
     * @return 座位信息
     */
    @Override
    public List<SeatInfo> selectSeatInfoList(SeatInfo seatInfo)
    {
        return seatInfoMapper.selectSeatInfoList(seatInfo);
    }

    /**
     * 新增座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    @Override
    public int insertSeatInfo(SeatInfo seatInfo)
    {
        seatInfo.setCreateTime(DateUtils.getNowDate());
        return seatInfoMapper.insertSeatInfo(seatInfo);
    }

    /**
     * 修改座位信息
     * 
     * @param seatInfo 座位信息
     * @return 结果
     */
    @Override
    public int updateSeatInfo(SeatInfo seatInfo)
    {
        seatInfo.setUpdateTime(DateUtils.getNowDate());
        return seatInfoMapper.updateSeatInfo(seatInfo);
    }

    /**
     * 批量删除座位信息
     * 
     * @param ids 需要删除的座位信息主键
     * @return 结果
     */
    @Override
    public int deleteSeatInfoByIds(Long[] ids)
    {
        return seatInfoMapper.deleteSeatInfoByIds(ids);
    }

    /**
     * 删除座位信息信息
     * 
     * @param id 座位信息主键
     * @return 结果
     */
    @Override
    public int deleteSeatInfoById(Long id)
    {
        return seatInfoMapper.deleteSeatInfoById(id);
    }
}
