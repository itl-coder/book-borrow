package com.book.borrow.readingroom.service.impl;

import java.util.List;
import com.book.borrow.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.borrow.readingroom.mapper.ReadingRoomMapper;
import com.book.borrow.readingroom.domain.ReadingRoom;
import com.book.borrow.readingroom.service.IReadingRoomService;

/**
 * 阅览室信息Service业务层处理
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@Service
public class ReadingRoomServiceImpl implements IReadingRoomService 
{
    @Autowired
    private ReadingRoomMapper readingRoomMapper;

    /**
     * 查询阅览室信息
     * 
     * @param id 阅览室信息主键
     * @return 阅览室信息
     */
    @Override
    public ReadingRoom selectReadingRoomById(Long id)
    {
        return readingRoomMapper.selectReadingRoomById(id);
    }

    /**
     * 查询阅览室信息列表
     * 
     * @param readingRoom 阅览室信息
     * @return 阅览室信息
     */
    @Override
    public List<ReadingRoom> selectReadingRoomList(ReadingRoom readingRoom)
    {
        return readingRoomMapper.selectReadingRoomList(readingRoom);
    }

    /**
     * 新增阅览室信息
     * 
     * @param readingRoom 阅览室信息
     * @return 结果
     */
    @Override
    public int insertReadingRoom(ReadingRoom readingRoom)
    {
        readingRoom.setCreateTime(DateUtils.getNowDate());
        return readingRoomMapper.insertReadingRoom(readingRoom);
    }

    /**
     * 修改阅览室信息
     * 
     * @param readingRoom 阅览室信息
     * @return 结果
     */
    @Override
    public int updateReadingRoom(ReadingRoom readingRoom)
    {
        readingRoom.setUpdateTime(DateUtils.getNowDate());
        return readingRoomMapper.updateReadingRoom(readingRoom);
    }

    /**
     * 批量删除阅览室信息
     * 
     * @param ids 需要删除的阅览室信息主键
     * @return 结果
     */
    @Override
    public int deleteReadingRoomByIds(Long[] ids)
    {
        return readingRoomMapper.deleteReadingRoomByIds(ids);
    }

    /**
     * 删除阅览室信息信息
     * 
     * @param id 阅览室信息主键
     * @return 结果
     */
    @Override
    public int deleteReadingRoomById(Long id)
    {
        return readingRoomMapper.deleteReadingRoomById(id);
    }

    @Override
    public List<ReadingRoom> selectReadingRoomCountFrontList() {
        return readingRoomMapper.selectReadingRoomCountFrontList();
    }
}
