import request from '@/utils/request'

export function listTag() {
  return request({
    url: '/user/tag',
    method: 'get',
  })
}

export function appendTag(params) {
  return request({
    url: '/user/tag',
    method: 'post',
    params,
  })
}
