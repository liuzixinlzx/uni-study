import Vue from 'vue'
import App from './App'

Vue.config.productionTip = false

if(process.env.NODE_ENV === 'development'){
    Vue.prototype.serverurl = 'http://192.168.3.69:8088';
}else{
    Vue.prototype.serverurl = 'http://lzxplay.top';
}

App.mpType = 'app'

const app = new Vue({
    ...App
})
app.$mount()
