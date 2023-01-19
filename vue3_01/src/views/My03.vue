<template>
  <h1>My03</h1>
  <div>{{age}}</div>
  <div>{{name}}</div>
  <div>{{author}}</div>
  <button @click="updateBookInfo">更新图书信息</button>
</template>

<script>

import {reactive, ref, toRefs} from "vue";

export default {
  name: "My03",
  setup() {
    const age = ref(99)
    const book = reactive({
      name: "水浒传",
      author: "施耐庵"
    })
    const updateBookInfo = () => {
      book.name = '三国演义'
      book.author = '罗贯中'
    };
    return {age, ...toRefs(book), updateBookInfo}
  }
}
</script>

<style scoped>

</style>