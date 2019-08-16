import React, { Component } from 'react'
import ReactMarkdown from 'react-markdown'
import CodeBlock from './CodeBlock'
import '../../../style/react-markdown.css'
import 'github-markdown-css/github-markdown.css'
class ReactMarkdownPreview extends Component {
  render () {
    const markdownSrc = this.props.source || ''
    const videoTag = markdownSrc.includes('<video')
    const isEscapeHtml = !videoTag

    return (
      <ReactMarkdown className="markdown-body markdown-init "
                     source={this.props.source} escapeHtml={isEscapeHtml}
                     renderers={{code: CodeBlock}}/>
    )
  }
}

export default ReactMarkdownPreview