<script lang="ts" setup>
import '@/assets/styles/style.scss'

useSeoMeta({
  title: '收藏夹导航',
  description: '管理、分类并可视化你的浏览器收藏夹，快捷导航至收藏夹网站',
  // 搜索关键词
  keywords: '收藏夹 导航 编程 资源',
})
</script>

<template>
  <NuxtPage :keepalive="{ max: 10 }" />
</template>
