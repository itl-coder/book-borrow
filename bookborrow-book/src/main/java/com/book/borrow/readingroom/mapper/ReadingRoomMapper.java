package com.book.borrow.readingroom.mapper;

import com.book.borrow.readingroom.domain.ReadingRoom;

import java.util.List;

/**
 * 阅览室信息Mapper接口
 *
 * @author bookborrow
 * @date 2025-05-03
 */
public interface ReadingRoomMapper {
    /**
     * 查询阅览室信息
     *
     * @param id 阅览室信息主键
     * @return 阅览室信息
     */
    public ReadingRoom selectReadingRoomById(Long id);

    /**
     * 查询阅览室信息列表
     *
     * @param readingRoom 阅览室信息
     * @return 阅览室信息集合
     */
    public List<ReadingRoom> selectReadingRoomList(ReadingRoom readingRoom);

    /**
     * 新增阅览室信息
     *
     * @param readingRoom 阅览室信息
     * @return 结果
     */
    public int insertReadingRoom(ReadingRoom readingRoom);

    /**
     * 修改阅览室信息
     *
     * @param readingRoom 阅览室信息
     * @return 结果
     */
    public int updateReadingRoom(ReadingRoom readingRoom);

    /**
     * 删除阅览室信息
     *
     * @param id 阅览室信息主键
     * @return 结果
     */
    public int deleteReadingRoomById(Long id);

    /**
     * 批量删除阅览室信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteReadingRoomByIds(Long[] ids);

    public List<ReadingRoom> selectReadingRoomCountFrontList();
}
