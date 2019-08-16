import React from 'react'
import {Button, Form, Input, Card} from "antd";

const FormItem = Form.Item
const {TextArea} = Input
const UserManagementBody = ({handleClick, handleUsernameChange}) => {
  return <Card title={'添加用户'}>
      <FormItem label={'email,每行写一个'}>
            <TextArea
              autosize={{minRows: 2, maxRows: 10}}
              onChange={e => handleUsernameChange(e.target.value)}
            />
      </FormItem>
      <Button
        type='primary'
        onClick={handleClick}>
        添加
      </Button>
  </Card>
}

export default UserManagementBody