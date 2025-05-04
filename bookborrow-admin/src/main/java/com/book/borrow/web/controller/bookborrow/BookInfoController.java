package com.book.borrow.web.controller.bookborrow;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.book.borrow.book.domain.vo.BookInfoCategoryVO;
import com.book.borrow.book.domain.vo.BookInfoStatusVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.book.borrow.common.annotation.Log;
import com.book.borrow.common.core.controller.BaseController;
import com.book.borrow.common.core.domain.AjaxResult;
import com.book.borrow.common.enums.BusinessType;
import com.book.borrow.book.domain.BookInfo;
import com.book.borrow.book.service.IBookInfoService;
import com.book.borrow.common.utils.poi.ExcelUtil;
import com.book.borrow.common.core.page.TableDataInfo;

/**
 * 图书信息（无外键约束）Controller
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/bookinfo/book")
public class BookInfoController extends BaseController
{
    @Autowired
    private IBookInfoService bookInfoService;

    /**
     * 查询图书信息（无外键约束）列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookInfo bookInfo)
    {
        startPage();
        List<BookInfo> list = bookInfoService.selectBookInfoList(bookInfo);
        return getDataTable(list);
    }

    @GetMapping("/front/list")
    public TableDataInfo frontList(BookInfo bookInfo)
    {
        startPage();
        List<BookInfo> list = bookInfoService.selectBookInfoFrontList(bookInfo);
        return getDataTable(list);
    }

    @PreAuthorize("@ss.hasPermi('bookinfo:book:list')")
    @GetMapping("/status")
    public AjaxResult statusList()
    {
        BookInfoStatusVO bookInfoStatusVO = bookInfoService.selectBookInfoStatus();
        return success(bookInfoStatusVO);
    }


    @GetMapping("/front/category")
    public AjaxResult frontCategoryList()
    {
        List<BookInfoCategoryVO> bookInfoCategoryVOList = bookInfoService.selectBookInfoCategory();
        return success(bookInfoCategoryVOList);
    }


    /**
     * 导出图书信息（无外键约束）列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:export')")
    @Log(title = "图书信息（无外键约束）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookInfo bookInfo)
    {
        List<BookInfo> list = bookInfoService.selectBookInfoList(bookInfo);
        ExcelUtil<BookInfo> util = new ExcelUtil<BookInfo>(BookInfo.class);
        util.exportExcel(response, list, "图书信息（无外键约束）数据");
    }

    /**
     * 获取图书信息（无外键约束）详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookInfoService.selectBookInfoById(id));
    }

    /**
     * 新增图书信息（无外键约束）
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:add')")
    @Log(title = "图书信息（无外键约束）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookInfo bookInfo)
    {
        return toAjax(bookInfoService.insertBookInfo(bookInfo));
    }

    /**
     * 修改图书信息（无外键约束）
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:edit')")
    @Log(title = "图书信息（无外键约束）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookInfo bookInfo)
    {
        return toAjax(bookInfoService.updateBookInfo(bookInfo));
    }

    /**
     * 删除图书信息（无外键约束）
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:book:remove')")
    @Log(title = "图书信息（无外键约束）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookInfoService.deleteBookInfoByIds(ids));
    }
}
