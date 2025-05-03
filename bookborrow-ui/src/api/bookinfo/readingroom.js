import request from '@/utils/request'

// 查询阅览室信息列表
export function listReadingroom(query) {
  return request({
    url: '/bookinfo/readingroom/list',
    method: 'get',
    params: query
  })
}

// 查询阅览室信息详细
export function getReadingroom(id) {
  return request({
    url: '/bookinfo/readingroom/' + id,
    method: 'get'
  })
}

// 新增阅览室信息
export function addReadingroom(data) {
  return request({
    url: '/bookinfo/readingroom',
    method: 'post',
    data: data
  })
}

// 修改阅览室信息
export function updateReadingroom(data) {
  return request({
    url: '/bookinfo/readingroom',
    method: 'put',
    data: data
  })
}

// 删除阅览室信息
export function delReadingroom(id) {
  return request({
    url: '/bookinfo/readingroom/' + id,
    method: 'delete'
  })
}
