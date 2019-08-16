import React from 'react'
import { Col, Input, Row, Tabs } from 'antd'
import ReactMarkdown from 'react-markdown'
import CodeBlock from './CodeBlock'
import '../../css/react-markdown.css'
import 'github-markdown-css/github-markdown.css'

const {TextArea} = Input
const {TabPane} = Tabs

export default class MarkdownEditor extends React.Component {

  state = {
    markdownSrc: this.props.value || '',
    tabKey: 'edit'
  }

  componentWillReceiveProps= nextProps=> {
    if (nextProps.value !== this.props.value) {
      this.setState({markdownSrc: nextProps.value})
    }
  }

  handleMarkdownChange= markdownSrc=> {
    this.setState({markdownSrc}, () => {
      this.props.onChange(this.state.markdownSrc)
    })
  }
  render () {
    let markdownSrc = this.state.markdownSrc || ''
    const videoTag = markdownSrc.toString().includes('<video')
    const isEscapeHtml = !videoTag

    return <div>
      <Tabs onChange={tabKey => this.setState({tabKey})}
            type="card">
        <TabPane tab="编辑" key="edit">
              <TextArea id='markdown-textArea'
                        autosize={{minRows: 8}}
                        value={this.state.markdownSrc}
                        onChange={(e) => this.handleMarkdownChange(e.target.value)}/>
        </TabPane>
        <TabPane tab="预览" key="preview">
          <div className="result-pane">
            <ReactMarkdown className=" markdown-body markdown-init" source={this.state.markdownSrc}
                           escapeHtml={isEscapeHtml}
                           renderers={{code: CodeBlock}}/>
          </div>
        </TabPane>
        <TabPane tab="实时" key="realTime">
          <Row className="react-markdown-editor">
            <Col span={12}>
              <div className="editor-pane">
                        <TextArea id='markdown-textArea' value={this.state.markdownSrc}
                                  onChange={(e) => this.handleMarkdownChange(e.target.value)}/>
              </div>
            </Col>
            <Col span={12}>
              <div className="result-pane">
                <ReactMarkdown className=" markdown-body markdown-init" source={this.state.markdownSrc}
                               escapeHtml={isEscapeHtml}
                               renderers={{code: CodeBlock}}/>
              </div>
            </Col>
          </Row>
        </TabPane>
      </Tabs>

    </div>
  }
}
