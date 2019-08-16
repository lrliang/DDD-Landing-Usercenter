
export default (state = {id: -1, currentOrganizationId: -1}, action) => {
  switch (action.type) {
    case 'GET_USER':
      return action.data
    default:
      return state
  }
}
