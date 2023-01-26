<template>
  <div>
    <hr>
    <div>{{a}}</div>
    <button @click="btnClick" v-once-click3:[timeUnit]="1000" v-hasPermission="['user', 'user2']">ClickMe</button>
  </div>
</template>

<script>
export default {
  name: "Hello01",
  directives: {

  }
}
</script>
<script setup>
import {ref} from "vue";

const a = ref(1)
const btnClick = () => {
  a.value++
}
const timeUnit = ref('s')

// 自定义指令, vue3 语法糖, v+大写开头的会识别成指令
const vOnceClick = {
  /**
   * @param el 就是自定义指令所绑定的元素
   * @param binding 各种传递的参数都在 binding 上
   * @param vNode Vue 编译生成的虚拟节点
   */
  mounted: (el, binding, vNode) => {
    el.addEventListener('click', () => {
      if (!el.disabled) {
        el.disabled = true
        setTimeout(() => {
          el.disabled = false
        }, binding.value || 3000)
      }
    })
  }
}
</script>

<style scoped>

</style>