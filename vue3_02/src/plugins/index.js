/**
 * plugins/index.js 中定义插件
 */
// 在插件中，可以引入 vue 组件，
import MyBanner from '@/components/MyBanner'

export default {
    /**
     * @param app 这个就是 Vue 对象
     * @param options 这是一个可选参数
     *
     * 当项目启动的时候，插件方法就会自动执行
     */
    install: (app, options) => {
        console.log("my first vue plugin")
        // 注册组件（这里的注册，就相当于全局注册）
        app.component('my-banner', MyBanner)
    }

}