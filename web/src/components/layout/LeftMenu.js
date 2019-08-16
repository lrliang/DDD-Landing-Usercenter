import React, {Component} from 'react'
import {Icon, Menu} from 'antd'
import {Link, withRouter} from 'react-router-dom'

import defaultSidebars from '../../constant/sidebar'

class LeftMenu extends Component {
  state = {
    selectedKey: '0'
  }

  componentWillMount() {
    const {location} = this.props
    this.update(location)
  }

  componentDidMount() {
    const {history, location} = this.props
    history.listen(this.update.bind(this))
    this.update(location)
  }

  update(location) {
    const index = defaultSidebars.findIndex((item) => {
      return item.linkTo === location.pathname
    })

    if (index < 0) return
    const newIndex = '' + index
    if (this.state.selectedKey === newIndex) return
    this.setState({
      selectedKey: newIndex
    })
  }

  render() {
    return (
      <Menu
        mode='inline'
        selectedKeys={[this.state.selectedKey]}
        style={{height: '100%'}}
      >
        {
          defaultSidebars.map((item, key) => {
            return (
              <Menu.Item key={key}>
                <Link to={item.linkTo}>
                  {item.name}&nbsp;
                  <Icon type={item.icon}/>
                </Link>
              </Menu.Item>)
          })
        }
      </Menu>
    )
  }
}

export default withRouter(LeftMenu)
