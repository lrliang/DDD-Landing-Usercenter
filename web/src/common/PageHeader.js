import React from 'react'
import { PageHeader } from 'antd'

class Breadcrumb extends React.Component{

  render () {
    return <PageHeader title="Title" breadcrumb={{ routes }} />
  }
}