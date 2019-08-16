import React from 'react'
import ReactDOM from 'react-dom'
import App from './components/App'
import { Provider } from 'react-redux'
import { applyMiddleware, createStore } from 'redux'
import thunkMiddleware from 'redux-thunk'
import logger from 'redux-logger';
import './css/index.css'
import './css/react-markdown.css'
import reducer from './reducer/index'

const store = createStore(reducer, applyMiddleware(thunkMiddleware,logger))

ReactDOM.render(
  <Provider store={store}>
    <App/>
  </Provider>,
  document.getElementById('root'),
)
