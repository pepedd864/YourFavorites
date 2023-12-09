<script lang="ts" setup>
import Nav from '@/popup/components/Nav/nav.vue'
import type { Ref } from 'vue'
import { nextTick, ref } from 'vue'
import mockBookmarks from '@/popup/mock/bookmarks'
import { getBookmarks } from '@/background'
import { useFavoritesStore, useTagsStore } from '@/stores'

const bookmarks = ref()
const tagsStore = useTagsStore()
const favoritesStore = useFavoritesStore()
let tags = ref<any[]>([])
let favorites = ref<any[]>([])

/**
 * 获取树节点
 * @param node 树节点
 * @param result 结果
 */
function getLeafNode(node: any, result: Ref<any[]>) {
  // 判断是否有"children"属性
  if (!('children' in node)) {
    result.value.push(node)
  } else if (node.children.length > 0) {
    for (let child of node.children) {
      getLeafNode(child, result)
    }
  }
}

/**
 * 获取非叶子节点
 * @param tree 树
 * @returns {any[]} 非叶子节点
 */
function getNonLeafNode(tree: any) {
  return tree.map((item: any, index: number) => {
    const newItem = { ...item }
    if (item?.children) {
      if (item.children[0]?.children === undefined) {
        delete newItem.children
        return newItem
      }
      newItem.children = getNonLeafNode(item.children)
    }
    return newItem
  })
}

/**
 * 获取非叶子节点作为数组
 * @param tree 树
 * @returns {any[]} 非叶子节点
 */
function getNonLeafNodeAsArray(tree: any) {
  const nodes = [] as any[]

  function traverse(node: any) {
    if (node.children) {
      nodes.push(node)
      node.children.forEach(traverse)
    }
  }

  traverse(tree)
  return nodes
}

/**
 * 同步收藏夹
 */
async function syncBookmarks() {
  if (import.meta.env.MODE === 'production') {
    bookmarks.value = await getBookmarks()
    bookmarks.value = bookmarks.value[0].children
  } else bookmarks.value = mockBookmarks[0].children

  getLeafNode(bookmarks.value[0], favorites)
  favoritesStore.setFavorites(favorites.value)
  console.log('bookmarks: ', bookmarks.value[0])
  console.log('favorites: ', favorites.value)
  tags.value = getNonLeafNode(bookmarks.value)
  tagsStore.setTags(tags.value)
  console.log('tags: ', tags.value)
}

nextTick(async () => {
  await syncBookmarks()
})
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
