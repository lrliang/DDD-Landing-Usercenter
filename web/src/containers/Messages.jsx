import { withAuth } from '@okta/okta-react'
import React, { Component } from 'react'
import config from '../config'

class Profile extends Component {
  constructor (props) {
    super(props)
    this.state = {message: null, failed: null}
  }

  componentDidMount () {
    this.getMessage()
  }

  async getMessage () {
    if (!this.state.users) {
      try {
        const accessToken = await this.props.auth.getAccessToken()
        const response = await fetch(config.resourceServer.usersUrl, {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        })
        if (response.status !== 200) {
          this.setState({failed: true})
          return
        }
        const data = await response.json()
        this.setState({data, failed: false})
      } catch (err) {
        this.setState({failed: true})
        console.error(err)
      }
    }
  }

  render () {
    return (
      <div>
        {this.state.failed === true &&
        <div>Failed to fetch users. Please verify the following:</div>}
        {this.state.failed === null && <p>Fetching Users..</p>}
        {this.state.messages &&
        <div>
          <p>This component makes a GET request to the resource server example,
            which must be running at <code>localhost:8000/api/users</code></p>
          <p>
            It attaches your current access token in
            the <code>Authorization</code> header on the request,
            and the resource server will attempt to authenticate this access
            token.
            If the token is valid the server will return a list of messages. If
            the token is not valid
            or the resource server is incorrectly configured, you will see a
            401 <code>Unauthorized response</code>.
          </p>
          <table>
            <thead>
            <tr>
              <th>Date</th>
              <th>Users</th>
            </tr>
            </thead>
            <tbody>
            {this.state.users.map(user =>
              <tr id={user.id} key={user.id}>
                <td>{user.date}</td>
                <td>{user.text}</td>
              </tr>)}
            </tbody>
          </table>
        </div>
        }
      </div>
    )
  }
}

export default withAuth(Profile)
