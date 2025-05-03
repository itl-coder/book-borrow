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
import com.book.borrow.readingroom.domain.ReadingRoom;
import com.book.borrow.readingroom.service.IReadingRoomService;
import com.book.borrow.common.utils.poi.ExcelUtil;
import com.book.borrow.common.core.page.TableDataInfo;

/**
 * 阅览室信息Controller
 * 
 * @author bookborrow
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/bookinfo/readingroom")
public class ReadingRoomController extends BaseController
{
    @Autowired
    private IReadingRoomService readingRoomService;

    /**
     * 查询阅览室信息列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:list')")
    @GetMapping("/list")
    public TableDataInfo list(ReadingRoom readingRoom)
    {
        startPage();
        List<ReadingRoom> list = readingRoomService.selectReadingRoomList(readingRoom);
        return getDataTable(list);
    }

    /**
     * 导出阅览室信息列表
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:export')")
    @Log(title = "阅览室信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ReadingRoom readingRoom)
    {
        List<ReadingRoom> list = readingRoomService.selectReadingRoomList(readingRoom);
        ExcelUtil<ReadingRoom> util = new ExcelUtil<ReadingRoom>(ReadingRoom.class);
        util.exportExcel(response, list, "阅览室信息数据");
    }

    /**
     * 获取阅览室信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(readingRoomService.selectReadingRoomById(id));
    }

    /**
     * 新增阅览室信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:add')")
    @Log(title = "阅览室信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ReadingRoom readingRoom)
    {
        return toAjax(readingRoomService.insertReadingRoom(readingRoom));
    }

    /**
     * 修改阅览室信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:edit')")
    @Log(title = "阅览室信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ReadingRoom readingRoom)
    {
        return toAjax(readingRoomService.updateReadingRoom(readingRoom));
    }

    /**
     * 删除阅览室信息
     */
    @PreAuthorize("@ss.hasPermi('bookinfo:readingroom:remove')")
    @Log(title = "阅览室信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(readingRoomService.deleteReadingRoomByIds(ids));
    }
}
