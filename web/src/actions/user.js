import HTTP_CODE from '../constant/http-code'
import * as request from '../constant/fetch-request'
import {message} from 'antd'

export const initUser = () => {
  if (request.getHeaderFromLocalStorage('jwt') !== null)
    return dispatch => {
      (async () => {
        const res = await request.get(`./api/users/${request.getHeaderFromLocalStorage('id')}`)
        if (res.status === HTTP_CODE.OK) {
          dispatch({
            type: 'GET_USER',
            data: res.body,
          })
        }else {
          window.location.href = 'https://www.baidu.com'
        }
      })()
    }
}

export const createUsers = (emails, callback) => {
  return () => {
    (async () => {
      const res = await request.post(`./api/users/emails?emails=${emails}`)
      if (res.status === HTTP_CODE.CREATED) {
        callback(res.body)
      }
    })()
  }
}
export const searchUsers = (emails) => {
  return (dispatch) => {
    (async () => {
      const res = await request.get(`./api/users/emails?emails=${emails}`)
      if (res.status === HTTP_CODE.OK) {
        dispatch({
          type: 'GET_USERS',
          data: res.body
        })
      }
    })()
  }
}
export const getRoles = () => {
  return (dispatch) => {
    (async () => {
      const res = await request.get(`./api/roles`)
      if (res.status === HTTP_CODE.OK) {
        dispatch({
          type: 'GET_ROLES',
          data: res.body
        })
      }
    })()
  }
}
export const updateRoles = (user,emails) => {
  return (dispatch) => {
    (async () => {
      const res = await request.update(`./api/users/${user.id}/roles`,user)
      if (res.status === HTTP_CODE.NO_CONTENT) {
        message.success('更新成功')
        dispatch(searchUsers(emails))
      }
    })()
  }
}
