import axios from 'axios'
import { ElMessage } from 'element-plus'
import router from '../router'

//创建axios实例对象
const request = axios.create({
  baseURL: '/api',
  timeout: 600000
})

//axios的请求 request 拦截器, 每次请求获取localStorage中的loginUser, 从中获取到token, 在请求头token中携带到服务端
request.interceptors.request.use(
  (config) => {
    let loginUser = JSON.parse(localStorage.getItem('loginUser'))
    console.log(localStorage.getItem('loginUser'))
    if (loginUser) {
      config.headers.token = loginUser.token
    }
    return config
  }
)

//axios的响应 response 拦截器
request.interceptors.response.use(
  (response) => { //成功回调
    return response.data
  },
  (error) => { //失败回调
        //如果响应的状态码为401, 则路由到登录页面
    if (error.response.status === 401) {
      ElMessage.error('登录失效, 请重新登录')
      router.push('/login')
    }else{
      ElMessage.success('接口访问异常')
    }
    return Promise.reject(error)
  }
)

export default request