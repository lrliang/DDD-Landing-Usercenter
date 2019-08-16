import React from 'react'
import {Button, Form, Input, Card, Table, Checkbox, Tag, Divider} from "antd";

const FormItem = Form.Item
const {TextArea} = Input
const RoleManagementBody = ({handleClick,searchAll, handleUsernameChange, usersRole, roles, updateRoles}) => {
  const columns = [{
    title: 'ID',
    dataIndex: 'id'
  }, {
    title: '用户名',
    dataIndex: 'username'
  }, {
    title: '邮箱',
    dataIndex: 'email'
  }, {
    title: '角色',
    dataIndex: 'roles',
    render: (text, record) => {
      return roles.map((item, index) => <span key={index}>
        <Checkbox onChange={e => updateRoles(e.target.value, e.target.checked)}
                  value={{
                    role: item,
                    user: record
                  }}
                  checked={!!text.find(role => role.id === item.id)}>
          <Tag>{item.title}</Tag>
        </Checkbox>
      </span>)
    }
  }]


  return <Card title={'角色管理'}>
    <FormItem label={'email,每行写一个'}>
            <TextArea
              autosize={{minRows: 2, maxRows: 10}}
              onChange={e => handleUsernameChange(e.target.value)}
            />
    </FormItem>
    <Button
      type='primary'
      onClick={handleClick}>
      查找
    </Button>
    <Divider type='vertical'/>
    <Button
      type='primary'
      onClick={searchAll}>
      全查
    </Button>
    <Table columns={columns}
           dataSource={usersRole}
           size='middle' pagination={false}/>
  </Card>
}

export default RoleManagementBody