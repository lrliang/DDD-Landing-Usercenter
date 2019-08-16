import { combineReducers } from 'redux'

import user from './user'
import users from './users'
import roles from './roles'

export default combineReducers({
  user,
  users,
  roles
})
