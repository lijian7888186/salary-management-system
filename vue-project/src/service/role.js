import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/role/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function addRole(data) {
  return ajax({
    url: '/role/addRole',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteRole(data) {
  return ajax({
    url: '/role/deleteRole',
    method: 'post',
    data,
    cType: 1
  })
}

export function findRoleOption(data) {
  return ajax({
    url: '/role/findRoleOption',
    method: 'post',
    data,
    cType: 3
  })
}
