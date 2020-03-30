import Axios from 'axios'
import NProgress from 'nprogress'
import 'nprogress/nprogress'


let axiosProvider = Axios.create({
    // baseURL: 'coin.xfdmao.com',
    timeout: 30000000
})
// 请求拦截
axiosProvider.interceptors.request.use(config => {
        NProgress.start()
        return config
    }, err => {
        return Promise.reject(err)
    }

)
// 请求响应
axiosProvider.interceptors.response.use(res =>{
    NProgress.done()
    return res
},error =>{
    NProgress.done()
    return Promise.reject(error)
});

export default axiosProvider


