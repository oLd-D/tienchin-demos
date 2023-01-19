<template>
  <div>hello boy</div>
  <h1>{{msg}}</h1>
  <input type="text" v-model="msg">
  <button @click="doLogin('zhangsan','123')">登录</button>
  <div>{{currentTime}}</div>
</template>

<script>
import {computed, onMounted, ref, watch} from "vue";
export default {
  name: "My02",
  setup() {
    // 不使用 ref 定义的变量不是一个响应式的
    // let msg = "hello vue3"
    let msg = ref("hello vue3")
    let age = ref(10)
    const doLogin = (username, password) => {
      console.log(username)
      console.log(password)
      age.value++
      msg.value = 'hello there'
    };

    // 钩子函数无需返回
    onMounted(() => {
      console.log("My02 初始化了")
    });
    const currentTime = computed(() => {
      // age.value++
      return Date.now()
    });
    watch(age, (newValue, oldValue) => {
      console.log(oldValue)
      console.log(newValue)
    });
    return {msg, doLogin, currentTime}
  }
}
</script>

<style scoped>

</style>