import React, { Component } from 'react'
import { BrowserRouter as Router, Route } from 'react-router-dom'

import UserManagement from '../containers/UserManagement'
import BasicLayout from './layout'
import config from '../config'
import { ImplicitCallback, SecureRoute, Security } from '@okta/okta-react'
import Profile from '../containers/Profile'
import Messages from '../containers/Messages'
import Home from '../containers/Home'

class App extends Component {
  render () {
    return (
      <Router basename='user-center'>
        <Security
          issuer={config.oidc.issuer}
          client_id={config.oidc.clientId}
          redirect_uri={config.oidc.redirectUri}
        >
          <BasicLayout>
            <Route exact path='/' component={Home}/>
            <Route path="/implicit" component={ImplicitCallback}/>
            <SecureRoute exact path='/users' component={UserManagement}/>
            <SecureRoute path="/messages" component={Messages}/>
            <SecureRoute path="/profile" component={Profile}/>
          </BasicLayout>
        </Security>
      </Router>
    )
  }
}

export default App

