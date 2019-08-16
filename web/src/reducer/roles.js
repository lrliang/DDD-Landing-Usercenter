
export default (state = [], action) => {
  switch (action.type) {
    case 'GET_ROLES':
      return action.data
    default:
      return state
  }
}
