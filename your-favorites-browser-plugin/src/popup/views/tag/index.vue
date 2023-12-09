<script lang="ts" setup>
import { nextTick, ref } from 'vue'
import { useTagsStore } from '@/stores'

const isShowDialog = ref(false)
const tagsStore = useTagsStore()
const tags = ref()
nextTick(() => {
  tags.value = tagsStore.tags
})
</script>

<template>
  <div class="tag">
    <a-tree :tree-data="tags" show-line>
      <template #title="{ key: treeKey, title }">
        <a-dropdown :trigger="['contextmenu']">
          <span class="tree-title">{{ title }}</span>
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="isShowDialog = true"
                >编辑
              </a-menu-item>
              <a-menu-item key="2">删除</a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </template>
    </a-tree>
  </div>
</template>

<style lang="scss" scoped></style>
