import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/salaryConfig/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function addConfig(data) {
  return ajax({
    url: '/salaryConfig/addConfig',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteConfig(data) {
  return ajax({
    url: '/salaryConfig/deleteConfig',
    method: 'post',
    data,
    cType: 1
  })
}

