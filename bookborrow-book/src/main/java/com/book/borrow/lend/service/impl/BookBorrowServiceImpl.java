package com.book.borrow.lend.service.impl;


import com.book.borrow.book.domain.BookInfo;
import com.book.borrow.book.mapper.BookInfoMapper;
import com.book.borrow.common.utils.DateUtils;
import com.book.borrow.lend.domain.BookBorrow;
import com.book.borrow.lend.domain.vo.BookBorrowVO;
import com.book.borrow.lend.mapper.BookBorrowMapper;
import com.book.borrow.lend.service.IBookBorrowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 图书借阅关系Service业务层处理
 *
 * @author bookborrow
 * @date 2025-05-05
 */
@Service
public class BookBorrowServiceImpl implements IBookBorrowService {
    @Resource
    private BookBorrowMapper bookBorrowMapper;
    @Resource
    private BookInfoMapper bookInfoMapper;

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
        List<BookBorrow> list = bookBorrowMapper.selectBookBorrowList(bookBorrow);
        Date now = new Date();

        for (BookBorrow borrow : list) {
            // 如果未归还（returnTime 为空）且当前时间大于应还时间，则为逾期
            if (borrow.getReturnTime() == null && borrow.getDueTime() != null && now.after(borrow.getDueTime())) {
                borrow.setStatus(2L); // 逾期状态

                // 计算逾期小时数
                long diffInMillis = now.getTime() - borrow.getDueTime().getTime();
                long hoursOverdue = diffInMillis / (1000 * 60 * 60);

                // 计算逾期费用：0.3 元/小时
                BigDecimal feePerHour = new BigDecimal("0.3");
                BigDecimal lateFee = feePerHour.multiply(BigDecimal.valueOf(hoursOverdue));

                // 设置逾期费用（保留两位小数）
                borrow.setLateFee(lateFee.setScale(2, BigDecimal.ROUND_HALF_UP));
            }
        }

        return list;
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

    @Override
    public int countRepeatBorrow(Long userId, Long bookId) {
        return bookBorrowMapper.countRepeatBorrow(userId, bookId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int bookBorrowed(BookBorrowVO bookBorrow) {
        // 参数基本校验
        if (bookBorrow == null || bookBorrow.getUserId() == null || bookBorrow.getBookId() == null) {
            throw new RuntimeException("借阅信息不完整！");
        }

        // 检查是否重复借阅
        int repeatFlag = bookBorrowMapper.countRepeatBorrow(bookBorrow.getUserId(), bookBorrow.getBookId());
        if (repeatFlag > 0) {
            throw new RuntimeException(bookBorrow.getBookName() + " 已经借阅，请勿重复借阅！");
        }

        // 查询图书库存
        BookInfo dbBookInfo = bookInfoMapper.selectBookInfoById(bookBorrow.getBookId());
        if (dbBookInfo == null) {
            throw new RuntimeException("图书不存在！");
        }
        if (dbBookInfo.getStock() <= 0) {
            throw new RuntimeException("图书《" + bookBorrow.getBookName() + "》库存不足，无法借阅！");
        }

        // 更新图书库存（减一）
        dbBookInfo.setStock(dbBookInfo.getStock() - 1);
        int updateCount = bookInfoMapper.updateBookInfo(dbBookInfo);
        if (updateCount == 0) {
            throw new RuntimeException("库存更新失败，请重试！");
        }

        // 插入借阅记录
        return bookBorrowMapper.insertBookBorrow(bookBorrow);
    }

}
