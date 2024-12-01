import { globalConfig } from '@/global-config.js'
import axios from 'axios'
import NProgress from 'nprogress'
import { message } from 'ant-design-vue'
import { tansParams } from '@/utils/trans-params'
import { useUserStore } from '@/store/use-user-store.js'

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// 对应国际化资源文件后缀
axios.defaults.headers['Content-Language'] = 'zh-CN,en;q=0.5'
// 创建axios实例
const service = axios.create({
  // axios中请求配置有baseURL选项，表示请求URL公共部分
  baseURL: globalConfig.baseURL,
  // 超时
  timeout: 10000,
})

// request拦截器
service.interceptors.request.use(
  config => {
    NProgress.inc()
    config.headers['Authorization'] = 'Bearer ' + useUserStore().getToken()
    // get请求映射params参数
    if (config.method === 'get' && config.params) {
      let url = config.url + '?' + tansParams(config.params)
      url = url.slice(0, -1)
      config.params = {}
      config.url = url
    }
    return config
  },
  error => {
    NProgress.done()
    console.log(error)
    Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    NProgress.done()
    // 未设置状态码则默认成功状态
    const code = response.data.code
    // 获取错误信息
    const msg = response.data.msg
    // 二进制数据则直接返回
    if (
      response.request.responseType === 'blob' ||
      response.request.responseType === 'arraybuffer'
    ) {
      return response
    } else if (code === 401) {
      const msg =
        response.data.msg || '无效的会话，或者会话已过期，请重新登录。'
      message.error(msg)
      useUserStore().logout()
      window.location.href = '/login'
      return Promise.reject(msg)
    } else if (code === 0) {
      message.error(msg)
      return Promise.reject(msg || '服务端异常')
    } else {
      return Promise.resolve(response.data)
    }
  },
  error => {
    NProgress.done()
    console.error('err', error)
    let { message: msg } = error
    if (msg === 'Network Error') {
      msg = '后端接口连接异常'
    } else if (msg.includes('timeout')) {
      msg = '系统接口请求超时'
    } else if (msg.includes('Request failed with status code')) {
      msg = '系统接口' + msg.substr(msg.length - 3) + '异常'
    }
    message.error(msg)
    return Promise.reject(error)
  }
)

/**
 * 通用文件下载方法
 * @param {string} url - 接口地址
 * @param {string} defaultFilename - 默认文件名（从服务端获取不到时使用）
 */
export const downloadFile = async (url, defaultFilename) => {
  try {
    const response = await service.get(url, {
      responseType: 'blob', // 确保返回的是二进制数据
    })

    // 从响应头获取文件名（如果未提供）
    const contentDisposition = response.headers['content-disposition']
    const extractedFileName = contentDisposition
      ? contentDisposition.split('filename=')[1].replace(/['"]/g, '') // 去除引号
      : defaultFilename

    const finalFileName = extractedFileName || 'downloaded_file'

    // 创建 Blob 对象并生成 URL
    const blob = new Blob([response.data])
    const urlObject = URL.createObjectURL(blob)

    // 创建 a 标签触发下载
    const link = document.createElement('a')
    link.href = urlObject
    link.download = finalFileName
    document.body.appendChild(link)
    link.click()

    // 清理 URL 对象和 DOM
    URL.revokeObjectURL(urlObject)
    document.body.removeChild(link)

    console.log(`${finalFileName} 下载完成`)
  } catch (error) {
    console.error('下载失败', error)
  }
}

export default service
