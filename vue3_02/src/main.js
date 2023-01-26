import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// 导入插件
import plugins from './plugins'

const app = createApp(App);

app.directive('onceClick2', {
    mounted: (el, binding) => {
        el.addEventListener('click', () => {
            if (!el.disabled) {
                el.disabled = true
                setTimeout(() => {
                    el.disabled = false
                }, binding.value||3000)
            }
        })
    }
})

app.directive('onceClick3', {
    mounted: (el, binding) => {
        el.addEventListener('click', () => {
            if (!el.disabled) {
                el.disabled = true
                let time = binding.value
                if (binding.arg == 's') {
                    time *= 1000
                }
                setTimeout(() => {
                    el.disabled = false
                }, time)
            }
        })
    }
})

const userPermissions = ['user']

app.directive('hasPermission', {
    mounted: (el, binding) => {
        // 获取组件需要的权限 <button v-hasPermission="['user:add']">添加用户</button>
        // value 则为 ['user:add']
        const {value} = binding // 等价于 const value = biding.value
        // 用户是否具备需要的权限中的一个或一些
        let flag = userPermissions.some(p => {
            return value.includes(p)
        })

        if (!flag) {
            el.parentNode && el.parentNode.removeChild(el)
        }
    }
})

app
    // 安装插件
    .use(plugins, {
        fontSize: {
            small: 6,
            large: 64
        }
    })
    .use(store)
    .use(router)
    .mount('#app')
