<script lang="ts" setup>
import { onMounted, ref, watch } from 'vue'
import type { TreeProps } from 'ant-design-vue'
import type { Favorites } from '@/types/favorites'
import { addKeyProperty } from '@/database/favorites'
import FavoriteGraph from '@/components/Favorite/FavoriteGraph'
import type { TreeGraph } from '@antv/g6'
import RegisterFn from '@/components/Favorite/registerFn'

let treeData: TreeProps['treeData'] = []

const favoriteData = (await (await fetch('api/favorites')).json()) as Favorites

function removeLeafNodes(data: any) {
  return data.map((item: any, index: number) => {
    const newItem = { ...item }
    if (item?.children) {
      if (item.children[0]?.children === undefined) {
        delete newItem.children
        return newItem
      }
      newItem.children = removeLeafNodes(item.children)
    }
    return newItem
  })
}

let favoriteDataWithKey = addKeyProperty(favoriteData)
// debugger
treeData = removeLeafNodes(favoriteDataWithKey) as TreeProps['treeData']

let favoritesDataSelected

const selectedKeys = ref<string[]>([])
let originalData = {
  title: '资源',
  desc: '资源导航',
  children: [...favoriteData],
}
let favoriteGraph: FavoriteGraph
let graph: TreeGraph

// 注册自定义节点和曲线
RegisterFn()

function createFavoriteGraph(data: any) {
  if (favoriteGraph) favoriteGraph.destroy()
  const container = document.querySelector('#container') as HTMLDivElement
  favoriteGraph = new FavoriteGraph(data, 'container', container)
  graph = favoriteGraph.getInstance()
  if (typeof window !== 'undefined')
    window.onresize = () => {
      if (!graph || graph.get('destroyed')) return
      if (!container || !container.offsetWidth || !container.offsetHeight)
        return
      graph.changeSize(container.offsetWidth, container.offsetHeight)
    }
}

watch(selectedKeys, () => {
  favoritesDataSelected = findElement(
    favoriteDataWithKey,
    'key',
    selectedKeys.value,
  )
  if (selectedKeys.value.length === 0) favoritesDataSelected = originalData
  createFavoriteGraph(favoritesDataSelected)
})

onMounted(() => createFavoriteGraph(originalData))
</script>

<template>
  <div class="favorite-graph">
    <a-tree
      v-model:selectedKeys="selectedKeys"
      :tree-data="treeData"
      show-line
      style="padding: 10px"
    >
      <template #title="{ title, key }">
        <span v-if="key === '0-0-1-0'" style="color: #1890ff">{{ title }}</span>
        <template v-else>{{ title }}</template>
      </template>
    </a-tree>
    <div id="container" ref="container"></div>
  </div>
</template>

<style lang="scss" scoped>
.favorite-graph {
  margin: 0 auto;
  padding: 10px;
  width: calc(100vw - 5vw * 2);
  max-height: 85vh;
  display: flex;
  justify-content: center;
  border-radius: 5px;
  border: 1px #ddd solid;

  @media screen and (max-width: 600px) {
    flex-direction: column;
  }

  #container {
    width: 100%;
    height: 55vh;
    box-sizing: border-box;
    overflow: hidden;
    border-left: 1px #ddd solid;
    border-top: none;

    @media screen and (max-width: 600px) {
      height: 70vh;
      border-left: none;
      border-top: 1px #ddd solid;
    }
  }
}
</style>
