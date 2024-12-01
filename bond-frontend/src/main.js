import NProgress from 'nprogress'
import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import Antd from 'ant-design-vue'

import './assets/style.css'
import 'ant-design-vue/es/style'

import 'nprogress/nprogress.css' // progress bar style
NProgress.configure({ showSpinner: false }) // 禁⽤进度环 若为true右侧会出现一个进度环
const app = createApp(App)
app.use(router)
app.use(Antd)
app.mount('#app')
