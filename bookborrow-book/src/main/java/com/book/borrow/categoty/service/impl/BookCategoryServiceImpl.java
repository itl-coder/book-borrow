package com.book.borrow.categoty.service.impl;

import java.util.List;

import com.book.borrow.categoty.domain.vo.CountBookVO;
import com.book.borrow.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.borrow.categoty.mapper.BookCategoryMapper;
import com.book.borrow.categoty.domain.BookCategory;
import com.book.borrow.categoty.service.IBookCategoryService;

/**
 * 图书分类Service业务层处理
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@Service
public class BookCategoryServiceImpl implements IBookCategoryService 
{
    @Autowired
    private BookCategoryMapper bookCategoryMapper;

    /**
     * 查询图书分类
     * 
     * @param id 图书分类主键
     * @return 图书分类
     */
    @Override
    public BookCategory selectBookCategoryById(Long id)
    {
        return bookCategoryMapper.selectBookCategoryById(id);
    }

    /**
     * 查询图书分类列表
     * 
     * @param bookCategory 图书分类
     * @return 图书分类
     */
    @Override
    public List<BookCategory> selectBookCategoryList(BookCategory bookCategory)
    {
        return bookCategoryMapper.selectBookCategoryList(bookCategory);
    }

    /**
     * 新增图书分类
     * 
     * @param bookCategory 图书分类
     * @return 结果
     */
    @Override
    public int insertBookCategory(BookCategory bookCategory)
    {
        bookCategory.setCreateTime(DateUtils.getNowDate());
        return bookCategoryMapper.insertBookCategory(bookCategory);
    }

    /**
     * 修改图书分类
     * 
     * @param bookCategory 图书分类
     * @return 结果
     */
    @Override
    public int updateBookCategory(BookCategory bookCategory)
    {
        bookCategory.setUpdateTime(DateUtils.getNowDate());
        return bookCategoryMapper.updateBookCategory(bookCategory);
    }

    /**
     * 批量删除图书分类
     * 
     * @param ids 需要删除的图书分类主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryByIds(Long[] ids)
    {
        return bookCategoryMapper.deleteBookCategoryByIds(ids);
    }

    /**
     * 删除图书分类信息
     * 
     * @param id 图书分类主键
     * @return 结果
     */
    @Override
    public int deleteBookCategoryById(Long id)
    {
        return bookCategoryMapper.deleteBookCategoryById(id);
    }

    @Override
    public List<CountBookVO> selectCountBookCategoryList() {
        return bookCategoryMapper.selectCountBookCategoryList();
    }
}
