package com.book.borrow.carousel.service;

import com.book.borrow.carousel.domain.Carousel;

import java.util.List;

/**
 * 轮播图Service接口
 *
 * @author bookborrow
 * @date 2025-05-05
 */
public interface ICarouselService {
    /**
     * 查询轮播图
     *
     * @param id 轮播图主键
     * @return 轮播图
     */
    public Carousel selectCarouselById(Long id);

    /**
     * 查询轮播图列表
     *
     * @param carousel 轮播图
     * @return 轮播图集合
     */
    public List<Carousel> selectCarouselList(Carousel carousel);

    /**
     * 新增轮播图
     *
     * @param carousel 轮播图
     * @return 结果
     */
    public int insertCarousel(Carousel carousel);

    /**
     * 修改轮播图
     *
     * @param carousel 轮播图
     * @return 结果
     */
    public int updateCarousel(Carousel carousel);

    /**
     * 批量删除轮播图
     *
     * @param ids 需要删除的轮播图主键集合
     * @return 结果
     */
    public int deleteCarouselByIds(Long[] ids);

    /**
     * 删除轮播图信息
     *
     * @param id 轮播图主键
     * @return 结果
     */
    public int deleteCarouselById(Long id);

    public List<Carousel> selectCarouselFrontList();
}
