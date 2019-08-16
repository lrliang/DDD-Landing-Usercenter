import React, { Component } from 'react'
import { Breadcrumb, PageHeader } from 'antd'
import breadcrumbs from '../../constant/breadcrumb'
import { withRouter } from 'react-router-dom'
import UrlPattern from 'url-pattern'

class BreadCrumb extends Component {
  state = {
    breadCrumbs: []
  }

  componentDidMount () {
    const {history, location} = this.props
    history.listen(this.update.bind(this))
    this.update(location)
  }

  update (location) {
    const currentBreadcrumb = breadcrumbs.find(breadcrumb => {
      const pattern = new UrlPattern(breadcrumb.linkTo)
      return pattern.match(location.pathname) !== null
    })

    this.setState({
      breadCrumbs: currentBreadcrumb.breadCrumbs
    })
  }

  render () {
    const {breadCrumbs} = this.state
    if (breadCrumbs.length === 0) return <div/>
    return (
      <PageHeader onBack={() => this.props.history.goBack()} title={
        <Breadcrumb separator=">" style={{display: 'inline-block', margin: '6px 0', fontSize: '15px'}}>
          {
            breadCrumbs.map((item, key) => {
              return (
                <Breadcrumb.Item key={key}>
                  {
                    key === breadCrumbs.length - 1
                      ? <span>{item.name}</span>
                      : <a onClick={() => this.props.history.push(item.linkTo)}>
                        {item.name}
                      </a>
                  }
                </Breadcrumb.Item>
              )
            })
          }
        </Breadcrumb>
      }/>

    )
  }
}

export default withRouter(BreadCrumb)
