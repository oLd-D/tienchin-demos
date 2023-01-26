<script setup>
// import HelloWorld from './components/HelloWorld.vue'
// import {getCurrentInstance} from "vue";
const {proxy} = getCurrentInstance()
// import {useRouter} from 'vue-router'

const router = useRouter()

function goUserMana() {
  // proxy.$router.push('/um')
  router.push('/um')
}
function goLogMana() {
  // proxy.$router.push('/lm')
  router.push('/lm')
}


</script>

<template>
  <div>
    <a href="https://vitejs.dev" target="_blank">
      <img src="/vite.svg" class="logo" alt="Vite logo" />
    </a>
    <a href="https://vuejs.org/" target="_blank">
      <img src="./assets/vue.svg" class="logo vue" alt="Vue logo" />
    </a>
  </div>
  <button @click="goUserMana">用户管理</button>
  <button @click="goLogMana">日志管理</button>
  <router-view/>
<!--  <HelloWorld msg="Vite + Vue" />-->
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
