package com.book.borrow.web.controller.bookborrow;

import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.book.borrow.categoty.domain.vo.CountBookVO;
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
import com.book.borrow.categoty.domain.BookCategory;
import com.book.borrow.categoty.service.IBookCategoryService;
import com.book.borrow.common.utils.poi.ExcelUtil;
import com.book.borrow.common.core.page.TableDataInfo;

/**
 * 图书分类Controller
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/bookinfo/category")
public class BookCategoryController extends BaseController
{
    @Autowired
    private IBookCategoryService bookCategoryService;

    /**
     * 查询图书分类列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:list')")
    @GetMapping("/list")
    public TableDataInfo list(BookCategory bookCategory)
    {
        startPage();
        List<BookCategory> list = bookCategoryService.selectBookCategoryList(bookCategory);
        return getDataTable(list);
    }

    @GetMapping("/front/count/list")
    public AjaxResult countFrontList()
    {
        List<CountBookVO> list = bookCategoryService.selectCountBookCategoryList();
        return success(list);
    }

    /**
     * 导出图书分类列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:export')")
    @Log(title = "图书分类", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, BookCategory bookCategory)
    {
        List<BookCategory> list = bookCategoryService.selectBookCategoryList(bookCategory);
        ExcelUtil<BookCategory> util = new ExcelUtil<BookCategory>(BookCategory.class);
        util.exportExcel(response, list, "图书分类数据");
    }

    /**
     * 获取图书分类详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(bookCategoryService.selectBookCategoryById(id));
    }

    /**
     * 新增图书分类
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:add')")
    @Log(title = "图书分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody BookCategory bookCategory)
    {
        return toAjax(bookCategoryService.insertBookCategory(bookCategory));
    }

    /**
     * 修改图书分类
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:edit')")
    @Log(title = "图书分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody BookCategory bookCategory)
    {
        return toAjax(bookCategoryService.updateBookCategory(bookCategory));
    }

    /**
     * 删除图书分类
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:category:remove')")
    @Log(title = "图书分类", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(bookCategoryService.deleteBookCategoryByIds(ids));
    }
}
