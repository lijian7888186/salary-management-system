import {ajax} from '@/utils/req'

export function findList(data) {
  return ajax({
    url: '/user/findList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findUserLevelList(data) {
  return ajax({
    url: '/common/findUserLevelList',
    method: 'post',
    data,
    cType: 1
  })
}

export function findUserById(data) {
  return ajax({
    url: '/user/findUserById',
    method: 'post',
    data,
    cType: 1
  })
}

export function addUser(data) {
  return ajax({
    url: '/user/addUser',
    method: 'post',
    data,
    cType: 1
  })
}

export function deleteUser(data) {
  return ajax({
    url: '/user/deleteUser',
    method: 'post',
    data,
    cType: 1
  })
}

export function updateUser(data) {
  return ajax({
    url: '/user/updateUser',
    method: 'post',
    data,
    cType: 1
  })
}

export function updatePassword(data) {
  return ajax({
    url: '/user/updatePassword',
    method: 'post',
    data,
    cType: 1
  })
}

export function findUserOption(data) {
  return ajax({
    url: '/user/findUserOption',
    method: 'post',
    data,
    cType: 3
  })
}


