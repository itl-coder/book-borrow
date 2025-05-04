package com.book.borrow.lend.mapper;


import com.book.borrow.lend.domain.BookBorrow;

import java.util.List;

/**
 * 图书借阅关系Mapper接口
 *
 * @author bookborrow
 * @date 2025-05-05
 */
public interface BookBorrowMapper {
    /**
     * 查询图书借阅关系
     *
     * @param id 图书借阅关系主键
     * @return 图书借阅关系
     */
    public BookBorrow selectBookBorrowById(Long id);

    /**
     * 查询图书借阅关系列表
     *
     * @param bookBorrow 图书借阅关系
     * @return 图书借阅关系集合
     */
    public List<BookBorrow> selectBookBorrowList(BookBorrow bookBorrow);

    /**
     * 新增图书借阅关系
     *
     * @param bookBorrow 图书借阅关系
     * @return 结果
     */
    public int insertBookBorrow(BookBorrow bookBorrow);

    /**
     * 修改图书借阅关系
     *
     * @param bookBorrow 图书借阅关系
     * @return 结果
     */
    public int updateBookBorrow(BookBorrow bookBorrow);

    /**
     * 删除图书借阅关系
     *
     * @param id 图书借阅关系主键
     * @return 结果
     */
    public int deleteBookBorrowById(Long id);

    /**
     * 批量删除图书借阅关系
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteBookBorrowByIds(Long[] ids);
}
