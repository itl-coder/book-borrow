import request from '@/utils/request'

// 查询图书分类列表
export function listCategory(query) {
  return request({
    url: '/bookinfo/category/list',
    method: 'get',
    params: query
  })
}

// 图书分类统计下拉框数据
export function countFrontListCategory() {
  return request({
    url: '/bookinfo/category/front/count/list',
    method: 'get',
  })
}

// 查询图书分类详细
export function getCategory(id) {
  return request({
    url: '/bookinfo/category/' + id,
    method: 'get'
  })
}

// 新增图书分类
export function addCategory(data) {
  return request({
    url: '/bookinfo/category',
    method: 'post',
    data: data
  })
}

// 修改图书分类
export function updateCategory(data) {
  return request({
    url: '/bookinfo/category',
    method: 'put',
    data: data
  })
}

// 删除图书分类
export function delCategory(id) {
  return request({
    url: '/bookinfo/category/' + id,
    method: 'delete'
  })
}
