<script lang="ts" setup>
import Nav from '@/popup/components/Nav/Nav.vue'
</script>

<template>
  <div class="entry">
    <Nav />
    <router-view v-slot="{ Component }">
      <transition-slide :offset="[-16, 0]" mode="out-in">
        <component :is="Component" />
      </transition-slide>
    </router-view>
  </div>
</template>

<style lang="scss" scoped>
.entry {
  display: flex;
  flex-direction: column;
  height: 100%;
  background: linear-gradient(#f48c8d, #f4c58d);
}

.main-container {
  position: relative;
  flex: 1;
}
</style>
