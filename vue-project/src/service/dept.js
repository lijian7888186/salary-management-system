import {ajax} from '@/utils/req'

export function findDeptOption(data) {
  return ajax({
    url: '/dept/findDeptOption',
    method: 'post',
    data,
    cType: 1
  })
}

export function findList(data) {
  return ajax({
    url: '/dept/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function addDept(data) {
  return ajax({
    url: '/dept/addDept',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteDept(data) {
  return ajax({
    url: '/dept/deleteDept',
    method: 'post',
    data,
    cType: 1
  })
}
