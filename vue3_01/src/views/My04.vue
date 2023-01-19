<template>
  <div>
    <div>{{ age }}</div>
    <div>{{ book.name }}</div>
    <div>{{ book.author }}</div>
    <div>{{ name }}</div>
    <div>{{ author }}</div>
    <button @click="updateBookInfo">更新图书信息</button>
    <button @click="btnClick">ClickMe</button>
    <button @click="go">Go My03</button>
    <button @click="btnClick2">StoreDemo</button>
  </div>
</template>

<script setup>

import {getCurrentInstance, onMounted, reactive, ref, toRefs} from "vue";

  //来自该方法的 proxy 对象则相当于之前的 this
  const {proxy} = getCurrentInstance();

  const age = ref(99)
  const book = reactive({
    name: "水浒传",
    author: "施耐庵"
  })
  const updateBookInfo = () => {
    book.name = '三国演义'
    book.author = '罗贯中'
  };
  //展开的变量
  const {name, author} = toRefs(book);
  onMounted(() => {
    console.log(this);
  })
  const btnClick = () => {
    //想在这里调用全局方法
    proxy.sayHello();
  }
  function go() {
    proxy.$router.push('/my03');
  }
  function btnClick2() {
    console.log(proxy.$store.state.name)
    proxy.$store.commit('SET_NAME', '江南一点雨');
    console.log(proxy.$store.state.name)
  }
</script>

<style scoped>

</style>