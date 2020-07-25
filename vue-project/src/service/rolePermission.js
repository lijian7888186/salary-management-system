import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/rolePermission/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findRolePermissions(data) {
  return ajax({
    url: '/rolePermission/findRolePermissions',
    method: 'post',
    data,
    cType: 1
  })
}

export function addRolePermission(data) {
  return ajax({
    url: '/rolePermission/addRolePermission',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteRolePermission(data) {
  return ajax({
    url: '/rolePermission/deleteRolePermission',
    method: 'post',
    data,
    cType: 1
  })
}
