import request from '@/utils/request'

// 查询图书借阅关系列表
export function listLend(query) {
  return request({
    url: '/bookinfo/lend/list',
    method: 'get',
    params: query
  })
}

// 查询图书借阅关系详细
export function getLend(id) {
  return request({
    url: '/bookinfo/lend/' + id,
    method: 'get'
  })
}

// 新增图书借阅关系
export function addLend(data) {
  return request({
    url: '/bookinfo/lend',
    method: 'post',
    data: data
  })
}

// 修改图书借阅关系
export function updateLend(data) {
  return request({
    url: '/bookinfo/lend',
    method: 'put',
    data: data
  })
}

// 删除图书借阅关系
export function delLend(id) {
  return request({
    url: '/bookinfo/lend/' + id,
    method: 'delete'
  })
}
