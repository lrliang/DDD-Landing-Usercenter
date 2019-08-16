import React, {Component} from 'react'
import {connect} from 'react-redux'
import {withRouter} from 'react-router-dom'
import UserManagementBody from "../components/users/UserManagementBody";
import {createUsers} from "../actions/user";
import {message} from 'antd'

class UserManagement extends Component {
  state = {
    emails: []
  }

  handleUsernameChange = emails => {
    this.setState({
      emails: emails
        .split("\n")
        .map(val => val.trim())
        .filter(val => val.length > 0)
    })
  }
  createUsers = () => {
    const {emails} = this.state
    this.props.createUsers(emails.join(','), (data) => {
      if (data.length > 0) {
        message.info(data + ' 邮箱已存在')
      }
      message.success('添加成功')
    })
  }

  render() {
    return <div>
      <UserManagementBody
        handleUsernameChange={emails => this.handleUsernameChange(emails)}
        handleClick={this.createUsers}/>
    </div>
  }
}

const mapStateToProps = ({user, courses}) => ({
  courses, user
})

const mapDispatchToProps = dispatch => ({
  createUsers: (emails, callback) => dispatch(createUsers(emails, callback))
})

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(UserManagement))
