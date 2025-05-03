import request from '@/utils/request'

// 查询座位信息列表
export function listSeat(query) {
  return request({
    url: '/bookinfo/seat/list',
    method: 'get',
    params: query
  })
}

// 查询座位信息详细
export function getSeat(id) {
  return request({
    url: '/bookinfo/seat/' + id,
    method: 'get'
  })
}

// 新增座位信息
export function addSeat(data) {
  return request({
    url: '/bookinfo/seat',
    method: 'post',
    data: data
  })
}

// 修改座位信息
export function updateSeat(data) {
  return request({
    url: '/bookinfo/seat',
    method: 'put',
    data: data
  })
}

// 删除座位信息
export function delSeat(id) {
  return request({
    url: '/bookinfo/seat/' + id,
    method: 'delete'
  })
}
