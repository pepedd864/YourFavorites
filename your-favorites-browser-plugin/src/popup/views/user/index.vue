<script lang="ts" setup>
import Icon from '@/popup/components/Icon/Icon.vue'
import { useUserStore } from '@/stores'

const userStore = useUserStore()
const { username, nickname, phone, email, createTime } = userStore.userInfo
</script>

<template>
  <div class="user">
    <a-avatar :size="80" style="margin: 0 auto">
      <template #icon>
        <Icon icon="UserOutlined" />
      </template>
    </a-avatar>
    <div class="user-item">
      <span>用户名：</span><span>{{ username }}</span>
    </div>
    <div class="user-item">
      <span>昵称：</span><span>{{ nickname }}</span>
    </div>
    <div class="user-item">
      <span>手机号：</span><span>{{ phone }}</span>
    </div>
    <div class="user-item">
      <span>邮箱：</span><span>{{ email }}</span>
    </div>
    <div class="user-item">
      <span>创建时间：</span><span>{{ createTime }}</span>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.user {
  display: flex;
  padding: 30px 0 10px 0;
  align-items: center;
  flex-direction: column;

  .user-item {
    width: 80%;
    padding: 7px;
    display: flex;
    color: #fff;
    border-bottom: 1px #fff solid;
    justify-content: space-between;
    font:
      16px 'Microsoft YaHei',
      sans-serif;
  }
}
</style>
