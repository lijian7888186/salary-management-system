import axios from 'axios'
import {Message} from 'element-ui';
import Router from '@/router/index';
import qs from 'qs'

const CONTENT_TYPES = {
  1: 'application/json',
  2: 'multipart/form-data',
  3: 'application/x-www-form-urlencoded'
};
const checkLoginUrl = "/login/checkLogin";

export function checkLoginAjax() {
  axios.defaults.baseURL = process.env.NODE_ENV === 'development' ? '/api' : '';
  let method = 'post';
  let param = {
    url: '/login/checkLogin',
    method: 'post',
    headers: {
      'Content-type': CONTENT_TYPES['3']
    }
  };
  axios(param).then(function ({data}) {
    console.log(data);
    if (data.code == 200) {
      Router.replace({path: '/index'});
    }
  })
}

// 缓存正在请求的ajax
const pendings = [];

export function ajax({method = 'post', url, data, cType = 1, responseType = ''}) {
  axios.defaults.baseURL = process.env.NODE_ENV === 'development' ? '/api' : '';
  const key = `${method} ${url} ${JSON.stringify(data)}`;
  for (let i = 0, len = pendings.length; i < len; i++) {
    if (pendings[i] === key) {
      return Promise.resolve({
        success: false,
        errMessage: '操作太频繁，请稍后再试'
      })
    }
  }
  data = data || {};
  const opts = {
    url,
    method,
    responseType,
    headers: {
      'Content-type': CONTENT_TYPES[cType || '1']
    }
  };
  if (method === 'get') {
    opts.params = data
  } else {
    opts.data = data
    if (cType == 3) {
      opts.data = qs.stringify(data);
    }
  }
  return axios(opts).then(function ({data}) {
    console.log(data);
    if (data.code != 200) {
      if (data.code == 401) {
        Router.replace({path: '/login'});
      } else {
        Message({
          message: data.msg || 'Error',
          type: 'error',
          duration: 1000,
          customClass:'zZindex'
        })
      }
    }
    return data
  }).catch(function (error) {
    console.log(error);
    return {
      code: '-1',
      msg: error.message
    }
  }).finally(function () {
    setTimeout(function () {
      for (let i = 0, len = pendings.length; i < len; i++) {
        if (pendings[i] === key) {
          pendings.splice(i, 1);
          break
        }
      }
    }, 500)
  })
}
