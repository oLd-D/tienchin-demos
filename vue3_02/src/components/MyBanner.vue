<template>
  <div>
    <div>
      <a href="http://giznet.site">guo</a>
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
  },
  methods: {
    btnClick() {
    }
  }
}
</script>

<style scoped>
</style>
