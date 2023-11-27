<script lang="ts" setup>
import { nextTick, ref } from 'vue'
import { getBookmarks } from '@/background'
import mockBookmarks from '@/popup/mock/bookmarks'
import Dialog from '@/popup/components/Dialog/Dialog.vue'
import { useTagsStore } from '@/stores/modules/tags'

const isShowDialog = ref(false)
const bookmarks = ref()
const tagsStore = useTagsStore()
let tags = []
nextTick(async () => {
  if (import.meta.env.MODE === 'production')
    bookmarks.value = await getBookmarks()
  else bookmarks.value = mockBookmarks
})
</script>

<template>
  <div class="home">
    <a-tree :tree-data="bookmarks" show-line>
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
    <Dialog
      :visible="isShowDialog"
      title="修改"
      @on-close="
        () => {
          isShowDialog = false
        }
      "
      @on-confirm="
        () => {
          console.log('ok')
          isShowDialog = false
        }
      "
    >
      <a-input />
    </Dialog>
  </div>
</template>

<style lang="scss" scoped>
.home {
  .tree-title {
    //overflow: hidden;
    //text-overflow: ellipsis;
    //white-space: nowrap
  }
}
</style>
