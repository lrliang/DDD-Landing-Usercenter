import HTTP_METHOD from './http-method'
import {message} from 'antd'

export const getHeaderFromLocalStorage = (key) => {
  return window.localStorage.getItem(key)
}

function errHandler (res) {
  res.json().then(body =>
    message.error(body.message)
  )
  return {status: res.status}
}

export const get = async (url) => {
  try {
    const res = await fetch(url, {
      method: HTTP_METHOD.GET,
      credentials: 'include',
      headers: new Headers({
        'Accept': 'application/json;charset=utf-8',
        id: getHeaderFromLocalStorage('id'),
        token: getHeaderFromLocalStorage('jwt'),
        sessionId: getHeaderFromLocalStorage('sessionId')
      })
    })
    const status = res.status

    if (!res.ok) {
      return errHandler(res)
    }
    const body = await res.json()

    return Object.assign({}, {body}, {status})
  } catch (ex) {
    return {status: ex.status}
  }
}

export const del = async (url) => {
  try {
    const res = await fetch(url, {
      method: HTTP_METHOD.DELETE,
      credentials: 'include',
      headers: new Headers({
        'Accept': 'application/json;charset=utf-8',
        'id': 2,
        'userName': 'aaa',
        'roles': [1, 2, 9],
        token: getHeaderFromLocalStorage('jwt'),
        sessionId: getHeaderFromLocalStorage('sessionId')
      })
    })
    const {status} = res

    return {status: status}
  } catch (ex) {
    return {status: ex.status}
  }
}

export const post = async (url, data) => {
  try {
    const res = await fetch(url, {
      method: HTTP_METHOD.POST,
      credentials: 'include',
      headers: new Headers({
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json',
        'id': 2,
        'userName': 'aaa',
        'roles': [1, 2, 9],
        token: getHeaderFromLocalStorage('jwt'),
        sessionId: getHeaderFromLocalStorage('sessionId')
      }),
      body: JSON.stringify(data)
    })

    if (!res.ok) {
      return errHandler(res)
    }
    const body = await res.json()
    const {status} = res

    return Object.assign({}, {body}, {status})
  } catch (ex) {
    return {status: ex.status}
  }
}

export const update = async (url, data) => {
  try {
    const res = await fetch(url, {
      method: HTTP_METHOD.PUT,
      credentials: 'include',
      headers: new Headers({
        'Content-Type': 'application/json;charset=utf-8',
        'Accept': 'application/json',
        'id': 2,
        'userName': 'aaa',
        'roles': [1, 2, 9],
        token: getHeaderFromLocalStorage('jwt'),
        sessionId: getHeaderFromLocalStorage('sessionId')
      }),
      body: JSON.stringify(data)
    })

    if (!res.ok) {
      return errHandler(res)
    }
    const {status} = res

    return {status: status}
  } catch (ex) {
    return {status: ex.status}
  }
}
