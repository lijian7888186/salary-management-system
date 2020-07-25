import {ajax, checkLoginAjax} from '@/utils/req'

export function toLogin(data) {
  return ajax({
    url: '/login/toLogin',
    method: 'post',
    data,
    cType: 1
  })
}

export function checkLogin() {
  return checkLoginAjax();
}

export function getLoginUser(data) {
  return ajax({
    url: '/login/getLoginUser',
    method: 'post',
    data,
    cType: 3
  })
}

export function logout(data) {
  return ajax({
    url: '/login/logout',
    method: 'post',
    data,
    cType: 3
  })
}
