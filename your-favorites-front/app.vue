<script lang="ts" setup>
import '@/assets/styles/style.scss'

useSeoMeta({
  title: '资源导航',
  description: '整合作者最常用和最实用的资源导航',
  // 搜索关键词
  keywords: '资源导航 编程 资源',
})
</script>

<template>
  <NuxtPage :keepalive="{ max: 10 }" />
</template>
