package com.book.borrow.web.controller.bookborrow;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.book.borrow.seat.domain.SeatInfo;
import com.book.borrow.seat.service.ISeatInfoService;
import com.book.borrow.common.utils.poi.ExcelUtil;
import com.book.borrow.common.core.page.TableDataInfo;

/**
 * 座位信息Controller
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/bookinfo/seat")
public class SeatInfoController extends BaseController
{
    @Autowired
    private ISeatInfoService seatInfoService;

    /**
     * 查询座位信息列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:list')")
    @GetMapping("/list")
    public TableDataInfo list(SeatInfo seatInfo)
    {
        startPage();
        List<SeatInfo> list = seatInfoService.selectSeatInfoList(seatInfo);
        return getDataTable(list);
    }

    /**
     * 导出座位信息列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:export')")
    @Log(title = "座位信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SeatInfo seatInfo)
    {
        List<SeatInfo> list = seatInfoService.selectSeatInfoList(seatInfo);
        ExcelUtil<SeatInfo> util = new ExcelUtil<SeatInfo>(SeatInfo.class);
        util.exportExcel(response, list, "座位信息数据");
    }

    /**
     * 获取座位信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(seatInfoService.selectSeatInfoById(id));
    }

    /**
     * 新增座位信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:add')")
    @Log(title = "座位信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SeatInfo seatInfo)
    {
        return toAjax(seatInfoService.insertSeatInfo(seatInfo));
    }

    /**
     * 修改座位信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:edit')")
    @Log(title = "座位信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SeatInfo seatInfo)
    {
        return toAjax(seatInfoService.updateSeatInfo(seatInfo));
    }

    /**
     * 删除座位信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:seat:remove')")
    @Log(title = "座位信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(seatInfoService.deleteSeatInfoByIds(ids));
    }
}
