import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/userRole/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findUserRoles(data) {
  return ajax({
    url: '/userRole/findUserRoles',
    method: 'post',
    data,
    cType: 1
  })
}

export function addUserRole(data) {
  return ajax({
    url: '/userRole/addUserRole',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteUserRole(data) {
  return ajax({
    url: '/userRole/deleteUserRole',
    method: 'post',
    data,
    cType: 1
  })
}
