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
        // 自定义指令, 第一个参数是其的名称, 第二个参数是逻辑
        // el 表示添加这个自定义指令的节点
        // binding 中包含了自定义指令的参数
        app.directive('font-size', (el, binding, vnode) => {
            let size = 18;
            //binding.arg 获取到的就是 small 或者 large
            switch (binding.arg) {
                case "small":
                    size = options.fontSize.small;
                    break;
                case "large":
                    size = options.fontSize.large;
                    break;
                default:
                    break;
            }
            //为使用了 v-font-size 指令的标签设置 font-size 的大小
            el.style.fontSize = size + 'px';

        })

        const clickMe = () => {
            console.log("============clickMe=========")
        }
        // 注册 clickMe 方法
        app.provide("clickMe", clickMe)
    }

}