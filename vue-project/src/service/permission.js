import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/permission/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findPermissionOption(data) {
  return ajax({
    url: '/permission/findPermissionOption',
    method: 'post',
    data,
    cType: 3
  })
}
