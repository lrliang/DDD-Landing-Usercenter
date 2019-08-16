import React from 'react'
import {Layout, Row} from 'antd'
import LeftMenu from "./LeftMenu";
const {Sider} = Layout
const {Header, Content} = Layout

class BasicLayout extends React.Component {
  render () {
    return (
      <Layout style={{background:'white', minHeight: '100vh'}}>
        <Header style={{background:'#ecf6fd'}}>
          <Row type='flex' justify='start'>
            <p>UserCenter</p>
          </Row>
        </Header>
        <Content style={{paddingTop:20}}>
          <Layout  style={{ background: '#fff' }}>
            <Sider width={200}>
              <LeftMenu />
            </Sider>
            <Content style={{ padding: '0 10px'}}>
              {this.props.children}
            </Content>
          </Layout>
        </Content>
      </Layout>)
  }
}

export default BasicLayout

