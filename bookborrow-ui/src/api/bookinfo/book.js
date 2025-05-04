import request from '@/utils/request'

// 查询图书信息（无外键约束）列表
export function listBook(query) {
  return request({
    url: '/bookinfo/book/list',
    method: 'get',
    params: query
  })
}

export function listFrontBook(query) {
  return request({
    url: '/bookinfo/book/front/list',
    method: 'get',
    params: query
  })
}

export function statusBook() {
  return request({
    url: '/bookinfo/book/status',
    method: 'get'
  })
}

export function categoryBookList() {
  return request({
    url: '/bookinfo/book/front/category',
    method: 'get'
  })
}


// 查询图书信息（无外键约束）详细
export function getBook(id) {
  return request({
    url: '/bookinfo/book/' + id,
    method: 'get'
  })
}

// 新增图书信息（无外键约束）
export function addBook(data) {
  return request({
    url: '/bookinfo/book',
    method: 'post',
    data: data
  })
}

// 修改图书信息（无外键约束）
export function updateBook(data) {
  return request({
    url: '/bookinfo/book',
    method: 'put',
    data: data
  })
}

// 删除图书信息（无外键约束）
export function delBook(id) {
  return request({
    url: '/bookinfo/book/' + id,
    method: 'delete'
  })
}
