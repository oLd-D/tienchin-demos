<template>
  <div>
    <div>
      <!--使用自定义指令指定文本的大小, 指令的名字就是 font-size-->
      <a href="http://giznet.site" v-font-size:small>guo</a>
    </div>
    <div>
      <a href="https://www.baidu.com" v-font-size:large>gl</a>
    </div>
    <div>
      <button @click="btnClick" v-once-click2="10000">ClickMe1</button>
      <hr>
      <button @click="btnClick" v-once-click3="10000">ClickMe3</button>
    </div>
  </div>
</template>

<script>
import {inject} from 'vue';
export default {
  name: "MyBanner",
  mounted() {
    //注入 clickMe 函数
    const clickMe = inject('clickMe');
    clickMe();
  }
}
</script>

<style scoped>
</style>
