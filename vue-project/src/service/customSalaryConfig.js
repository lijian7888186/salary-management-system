import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/customSalaryConfig/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function addConfig(data) {
  return ajax({
    url: '/customSalaryConfig/addConfig',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteConfig(data) {
  return ajax({
    url: '/customSalaryConfig/deleteConfig',
    method: 'post',
    data,
    cType: 1
  })
}

