import {ajax} from '@/utils/req'

export function toLogin(data) {
  return ajax({
    url: '/login/toLogin',
    method: 'post',
    data,
    cType: 1
  })
}
