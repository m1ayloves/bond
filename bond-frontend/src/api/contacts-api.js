import request from '@/utils/request'

export function listContact(params) {
  return request({
    url: '/contacts/list',
    method: 'get',
    params,
  })
}

export function getContactInfo(id) {
  return request({
    url: `/contacts/${id}`,
    method: 'get',
  })
}

export function saveContact(data) {
  return request({
    url: `/contacts`,
    method: 'post',
    data,
  })
}

export function updateContact(data) {
  return request({
    url: `/contacts`,
    method: 'put',
    data,
  })
}

export function deleteContact(id) {
  return request({
    url: `/contacts/${id}`,
    method: 'delete',
  })
}
