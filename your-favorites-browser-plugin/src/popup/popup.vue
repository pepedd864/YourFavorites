<script lang="ts" setup>
// import '@/content'
</script>

<template>
  <router-view v-slot="{ Component }">
    <transition-scale mode="out-in">
      <component :is="Component" />
    </transition-scale>
  </router-view>
</template>

<style lang="scss">
body {
  position: relative;
  width: 380px;
  height: 550px !important;
}
</style>
