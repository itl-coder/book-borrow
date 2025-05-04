package com.book.borrow.book.service.impl;

import java.util.List;

import com.book.borrow.book.domain.vo.BookInfoCategoryVO;
import com.book.borrow.book.domain.vo.BookInfoStatusVO;
import com.book.borrow.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.book.borrow.book.mapper.BookInfoMapper;
import com.book.borrow.book.domain.BookInfo;
import com.book.borrow.book.service.IBookInfoService;

/**
 * 图书信息（无外键约束）Service业务层处理
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@Service
public class BookInfoServiceImpl implements IBookInfoService 
{
    @Autowired
    private BookInfoMapper bookInfoMapper;

    /**
     * 查询图书信息（无外键约束）
     * 
     * @param id 图书信息（无外键约束）主键
     * @return 图书信息（无外键约束）
     */
    @Override
    public BookInfo selectBookInfoById(Long id)
    {
        return bookInfoMapper.selectBookInfoById(id);
    }

    /**
     * 查询图书信息（无外键约束）列表
     * 
     * @param bookInfo 图书信息（无外键约束）
     * @return 图书信息（无外键约束）
     */
    @Override
    public List<BookInfo> selectBookInfoList(BookInfo bookInfo)
    {
        return bookInfoMapper.selectBookInfoList(bookInfo);
    }

    /**
     * 新增图书信息（无外键约束）
     * 
     * @param bookInfo 图书信息（无外键约束）
     * @return 结果
     */
    @Override
    public int insertBookInfo(BookInfo bookInfo)
    {
        bookInfo.setCreateTime(DateUtils.getNowDate());
        return bookInfoMapper.insertBookInfo(bookInfo);
    }

    /**
     * 修改图书信息（无外键约束）
     * 
     * @param bookInfo 图书信息（无外键约束）
     * @return 结果
     */
    @Override
    public int updateBookInfo(BookInfo bookInfo)
    {
        bookInfo.setUpdateTime(DateUtils.getNowDate());
        return bookInfoMapper.updateBookInfo(bookInfo);
    }

    /**
     * 批量删除图书信息（无外键约束）
     * 
     * @param ids 需要删除的图书信息（无外键约束）主键
     * @return 结果
     */
    @Override
    public int deleteBookInfoByIds(Long[] ids)
    {
        return bookInfoMapper.deleteBookInfoByIds(ids);
    }

    /**
     * 删除图书信息（无外键约束）信息
     * 
     * @param id 图书信息（无外键约束）主键
     * @return 结果
     */
    @Override
    public int deleteBookInfoById(Long id)
    {
        return bookInfoMapper.deleteBookInfoById(id);
    }

    @Override
    public BookInfoStatusVO selectBookInfoStatus() {
        return bookInfoMapper.selectBookInfoStatus();
    }

    @Override
    public List<BookInfoCategoryVO> selectBookInfoCategory() {
        return bookInfoMapper.selectBookInfoCategory();
    }

    @Override
    public List<BookInfo> selectBookInfoFrontList(BookInfo bookInfo) {
        return bookInfoMapper.selectBookInfoFrontList(bookInfo);
    }
}
