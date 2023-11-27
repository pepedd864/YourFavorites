<script lang="ts" setup>
import { ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '@/stores/index'
// route钩子，返回当前的路由地址
const route = useRoute()
// router钩子，返回路由器实例
const router = useRouter()
// 获取当前路由path
const activePath = ref(route.path)
const userStore = useUserStore()
// Tab组件控制路由跳转
const onTabChange = (gotoPath: string) => {
  activePath.value = gotoPath
  router.push(gotoPath)
}

// 退出到Login页面
const onExit = () => {
  router.push('/login')
  userStore.logout()
}
</script>

<template>
  <div class="nav">
    <a-tabs
      v-model:active-key="activePath"
      :tabBarGutter="50"
      centered
      class="top-nav"
      @tabClick="onTabChange"
    >
      <a-tab-pane key="/home" tab="首页"></a-tab-pane>
      <a-tab-pane key="/user" tab="用户"></a-tab-pane>
    </a-tabs>
    <a-button class="btn-exit" type="primary" @click="onExit">退出</a-button>
  </div>
</template>

<style lang="scss" scoped>
.nav {
  position: relative;
  z-index: 999;
  background: #fff;
}

.top-nav {
  margin: 0 auto;
  width: 200px;

  :deep(.ant-tabs-nav) {
    margin-bottom: 0;
  }
}

.btn-exit {
  position: absolute;
  top: 8px;
  right: 6px;
}
</style>
