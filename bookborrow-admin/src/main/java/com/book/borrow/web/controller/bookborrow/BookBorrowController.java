package com.book.borrow.web.controller.bookborrow;

import com.book.borrow.common.annotation.Log;
import com.book.borrow.common.core.controller.BaseController;
import com.book.borrow.common.core.domain.AjaxResult;
import com.book.borrow.common.core.page.TableDataInfo;
import com.book.borrow.common.enums.BusinessType;
import com.book.borrow.common.utils.poi.ExcelUtil;
import com.book.borrow.lend.domain.BookBorrow;
import com.book.borrow.lend.domain.vo.BookBorrowVO;
import com.book.borrow.lend.service.IBookBorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 图书借阅关系Controller
 *
 * @author bookborrow
 * @date 2025-05-05
 */
@RestController
@RequestMapping("/bookinfo/lend")
public class BookBorrowController extends BaseController {
    @Autowired
    private IBookBorrowService bookBorrowService;

    /**
     * 查询图书借阅关系列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookBorrow bookBorrow) {
        startPage();
        List<BookBorrow> list = bookBorrowService.selectBookBorrowList(bookBorrow);
        return getDataTable(list);
    }

    /**
     * 导出图书借阅关系列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:export')")
    @Log(title = "图书借阅关系", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookBorrow bookBorrow) {
        List<BookBorrow> list = bookBorrowService.selectBookBorrowList(bookBorrow);
        ExcelUtil<BookBorrow> util = new ExcelUtil<BookBorrow>(BookBorrow.class);
        util.exportExcel(response, list, "图书借阅关系数据");
    }

    /**
     * 获取图书借阅关系详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return success(bookBorrowService.selectBookBorrowById(id));
    }

    /**
     * 新增图书借阅关系
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:add')")
    @Log(title = "图书借阅关系", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookBorrowVO bookBorrow) {
        int borrowFlag = bookBorrowService.bookBorrowed(bookBorrow);
        return toAjax(borrowFlag);
    }

    /**
     * 修改图书借阅关系
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:edit')")
    @Log(title = "图书借阅关系", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookBorrow bookBorrow) {
        return toAjax(bookBorrowService.updateBookBorrow(bookBorrow));
    }

    /**
     * 删除图书借阅关系
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:lend:remove')")
    @Log(title = "图书借阅关系", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(bookBorrowService.deleteBookBorrowByIds(ids));
    }
}
