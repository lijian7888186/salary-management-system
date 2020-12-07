import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/salaryManager/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findDtList(data) {
  return ajax({
    url: '/common/findDtList',
    method: 'post',
    data,
    cType: 3
  })
}

export function findUserSalary(data) {
  return ajax({
    url: '/salaryManager/findUserSalary',
    method: 'post',
    data,
    cType: 1
  })
}

export function findUserSalaryInfo(data) {
  return ajax({
    url: '/salaryManager/findUserSalaryInfo',
    method: 'post',
    data,
    cType: 1
  })
}
