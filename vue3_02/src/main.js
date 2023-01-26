import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 导入插件
import plugins from './plugins'

const app = createApp(App);

app
    // 安装插件
    .use(plugins)
    .use(store)
    .use(router)
    .mount('#app')
