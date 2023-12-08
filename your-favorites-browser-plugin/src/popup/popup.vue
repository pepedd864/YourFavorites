<script lang="ts" setup>
// import '@/content'
import zhCN from 'ant-design-vue/es/locale/zh_CN'
</script>

<template>
  <a-config-provider :locale="zhCN">
    <router-view v-slot="{ Component }">
      <transition-scale mode="out-in">
        <component :is="Component" />
      </transition-scale>
    </router-view>
  </a-config-provider>
</template>

<style lang="scss">
body {
  position: relative;
  width: 380px;
  height: 550px !important;
}
</style>
