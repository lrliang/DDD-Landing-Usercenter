const USER_ROLE = {
  ADMIN: 'ADMIN',
  PM: 'PM',
  STUDENT: 'STUDENT',
  MENTOR: 'MENTOR',
  INSTRUCTOR: 'INSTRUCTOR',
}

const isAdmin = (user) => {
  const {roles: roles = []} = user
  const role = roles.find(role => role.title === USER_ROLE.ADMIN)
  return !!role
}

const isPM = (user) => {
  const {roles: roles = []} = user
  const role = roles.find(role => role.title === USER_ROLE.PM)
  return !!role
}

const isStudent = (user) => {
  const {roles: roles = []} = user
  const role = roles.find(role => role.title === USER_ROLE.STUDENT)
  return !!role
}

const isMentor = (user) => {
  const {roles: roles = []} = user
  const role = roles.find(role => role.title === USER_ROLE.MENTOR)
  return !!role
}

const isInstructor = (user) => {
  const {roles: roles = []} = user
  const role = roles.find(role => role.title === USER_ROLE.INSTRUCTOR)
  return !!role
}

export default {isAdmin, isInstructor, isMentor, isPM, isStudent}
