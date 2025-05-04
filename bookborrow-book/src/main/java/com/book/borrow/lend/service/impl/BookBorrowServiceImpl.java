package com.book.borrow.lend.service.impl;


import com.book.borrow.common.utils.DateUtils;
import com.book.borrow.lend.domain.BookBorrow;
import com.book.borrow.lend.mapper.BookBorrowMapper;
import com.book.borrow.lend.service.IBookBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图书借阅关系Service业务层处理
 *
 * @author bookborrow
 * @date 2025-05-05
 */
@Service
public class BookBorrowServiceImpl implements IBookBorrowService {
    @Autowired
    private BookBorrowMapper bookBorrowMapper;

    /**
     * 查询图书借阅关系
     *
     * @param id 图书借阅关系主键
     * @return 图书借阅关系
     */
    @Override
    public BookBorrow selectBookBorrowById(Long id) {
        return bookBorrowMapper.selectBookBorrowById(id);
    }

    /**
     * 查询图书借阅关系列表
     *
     * @param bookBorrow 图书借阅关系
     * @return 图书借阅关系
     */
    @Override
    public List<BookBorrow> selectBookBorrowList(BookBorrow bookBorrow) {
        return bookBorrowMapper.selectBookBorrowList(bookBorrow);
    }

    /**
     * 新增图书借阅关系
     *
     * @param bookBorrow 图书借阅关系
     * @return 结果
     */
    @Override
    public int insertBookBorrow(BookBorrow bookBorrow) {
        bookBorrow.setCreateTime(DateUtils.getNowDate());
        return bookBorrowMapper.insertBookBorrow(bookBorrow);
    }

    /**
     * 修改图书借阅关系
     *
     * @param bookBorrow 图书借阅关系
     * @return 结果
     */
    @Override
    public int updateBookBorrow(BookBorrow bookBorrow) {
        bookBorrow.setUpdateTime(DateUtils.getNowDate());
        return bookBorrowMapper.updateBookBorrow(bookBorrow);
    }

    /**
     * 批量删除图书借阅关系
     *
     * @param ids 需要删除的图书借阅关系主键
     * @return 结果
     */
    @Override
    public int deleteBookBorrowByIds(Long[] ids) {
        return bookBorrowMapper.deleteBookBorrowByIds(ids);
    }

    /**
     * 删除图书借阅关系信息
     *
     * @param id 图书借阅关系主键
     * @return 结果
     */
    @Override
    public int deleteBookBorrowById(Long id) {
        return bookBorrowMapper.deleteBookBorrowById(id);
    }
}
