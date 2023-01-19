<template>
  <div>hello boy</div>
  <h1>{{msg}}</h1>
</template>

<script>
export default {
  name: "My01",
  data() {
    return {
      msg: "hello world!"
    }
  },
  mounted() {
    console.log("=== vue2 === mounted")
  }
}
</script>

<style scoped>

</style>