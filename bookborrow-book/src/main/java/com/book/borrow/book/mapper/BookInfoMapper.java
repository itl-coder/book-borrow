package com.book.borrow.book.mapper;

import com.book.borrow.book.domain.BookInfo;
import com.book.borrow.book.domain.vo.BookInfoStatusVO;

import java.util.List;

/**
 * 图书信息（无外键约束）Mapper接口
 *
 * @author bookborrow
 * @date 2025-05-03
 */
public interface BookInfoMapper {
    /**
     * 查询图书信息（无外键约束）
     *
     * @param id 图书信息（无外键约束）主键
     * @return 图书信息（无外键约束）
     */
    public BookInfo selectBookInfoById(Long id);

    /**
     * 查询图书信息（无外键约束）列表
     *
     * @param bookInfo 图书信息（无外键约束）
     * @return 图书信息（无外键约束）集合
     */
    public List<BookInfo> selectBookInfoList(BookInfo bookInfo);

    /**
     * 新增图书信息（无外键约束）
     *
     * @param bookInfo 图书信息（无外键约束）
     * @return 结果
     */
    public int insertBookInfo(BookInfo bookInfo);

    /**
     * 修改图书信息（无外键约束）
     *
     * @param bookInfo 图书信息（无外键约束）
     * @return 结果
     */
    public int updateBookInfo(BookInfo bookInfo);

    /**
     * 删除图书信息（无外键约束）
     *
     * @param id 图书信息（无外键约束）主键
     * @return 结果
     */
    public int deleteBookInfoById(Long id);

    /**
     * 批量删除图书信息（无外键约束）
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookInfoByIds(Long[] ids);

    public BookInfoStatusVO selectBookInfoStatus();
}
