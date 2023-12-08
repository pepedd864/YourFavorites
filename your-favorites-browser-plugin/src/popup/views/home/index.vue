<script lang="ts" setup>
import type { Ref } from 'vue'
import { nextTick, reactive, ref } from 'vue'
import { getBookmarks } from '@/background'
import mockBookmarks from '@/popup/mock/bookmarks'
import Dialog from '@/popup/components/Dialog/Dialog.vue'
import Icon from '@/popup/components/Icon/Icon.vue'
import { useFavoritesStore, useTagsStore } from '@/stores'
import { removeUrlPrefix } from '@/utils/utils'

const isShowDialog = ref(false)
const bookmarks = ref()
const selectedIndex = ref()
const selectedItem = ref()
const tagsStore = useTagsStore()
const favoritesStore = useFavoritesStore()
let tags = ref<any[]>([])
let favorites = ref<any[]>([])
const formState = reactive({
  title: '',
  url: '',
  desc: '',
  tags: [],
})
const rules = []

/**
 * 获取树节点
 * @param node 树节点
 * @param result 结果
 */
function getTreeNode(node: any, result: Ref<any[]>) {
  // 判断是否有"children"属性
  if (!('children' in node)) {
    result.value.push(node)
  } else if (node.children.length > 0) {
    for (let child of node.children) {
      getTreeNode(child, result)
    }
  }
}

/**
 * 编辑收藏夹
 * @param index 收藏夹项索引
 */
function editFavorite(index: number) {
  isShowDialog.value = true
  selectedIndex.value = index
  selectedItem.value = favorites.value[index]
  formState.title = selectedItem.value.title
  formState.url = selectedItem.value.url
  formState.desc = selectedItem.value.desc
}

nextTick(async () => {
  if (import.meta.env.MODE === 'production') {
    bookmarks.value = await getBookmarks()
    bookmarks.value = bookmarks.value[0].children
  } else bookmarks.value = mockBookmarks[0].children

  getTreeNode(bookmarks.value[0], favorites)
  console.log(favorites.value)
})
</script>

<template>
  <div class="home">
    <!--<a-tree :tree-data="bookmarks" show-line>-->
    <!--  <template #title="{ key: treeKey, title }">-->
    <!--    <a-dropdown :trigger="['contextmenu']">-->
    <!--      <span class="tree-title">{{ title }}</span>-->
    <!--      <template #overlay>-->
    <!--        <a-menu>-->
    <!--          <a-menu-item key="1" @click="isShowDialog = true"-->
    <!--            >编辑-->
    <!--          </a-menu-item>-->
    <!--          <a-menu-item key="2">删除</a-menu-item>-->
    <!--        </a-menu>-->
    <!--      </template>-->
    <!--    </a-dropdown>-->
    <!--  </template>-->
    <!--</a-tree>-->
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
      <a-form :label-col="{ span: 2 }" :model="formState" layout="horizontal">
        <a-form-item label="图标">
          <img
            :src="`https://api.iowen.cn/favicon/${removeUrlPrefix(
              formState.url,
            )}.png`"
            alt="logo"
            onerror="this.src='https://api.iowen.cn/favicon/www.google.com.png';this.onerror=null;"
            width="60"
          />
        </a-form-item>
        <a-form-item label="标题" name="title">
          <a-input v-model:value="formState.title" placeholder="请输入标题" />
        </a-form-item>
        <a-form-item label="链接" name="url">
          <a-input v-model:value="formState.url" placeholder="请输入链接" />
        </a-form-item>
        <a-form-item label="描述" name="desc">
          <a-input v-model:value="formState.desc" placeholder="请输入描述" />
        </a-form-item>
        <a-form-item label="标签" name="tags">
          <a-select
            v-model:value="formState.tags"
            mode="tags"
            placeholder="选择收藏夹标签"
          >
            <a-select-option v-for="tag in tags" :key="tag">
              {{ tag }}
            </a-select-option>
          </a-select>
        </a-form-item>
      </a-form>
    </Dialog>

    <a-list
      :data-source="favorites"
      bordered
      class="favorite-list"
      item-layout="vertical"
      size="small"
    >
      <template #renderItem="{ item, index }">
        <a-dropdown :trigger="['contextmenu']">
          <a-list-item>
            <template #extra>
              <img
                :src="`https://api.iowen.cn/favicon/${removeUrlPrefix(
                  item.url,
                )}.png`"
                alt="logo"
                onerror="this.src='https://api.iowen.cn/favicon/www.google.com.png';this.onerror=null;"
                width="40"
              />
            </template>
            <a-tag color="#1890ff">
              {{ index + 1 }}
            </a-tag>
            <span v-if="Math.random() > 0.5" class="info">
              <Icon icon="ExclamationCircleOutlined" />
            </span>
            <a-tooltip>
              <template #title>
                <span>{{ item.url }}</span>
              </template>
              <a :href="item.url" target="_blank">
                <span>{{ item.title }}</span>
              </a>
            </a-tooltip>
          </a-list-item>
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="editFavorite(index)">
                <Icon icon="EditOutlined" />
                <span>编辑</span>
              </a-menu-item>
              <a-menu-item key="2">
                <a-popconfirm title="确认删除么？">
                  <Icon icon="DeleteOutlined" />
                  <span>删除</span>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
      </template>
    </a-list>
    <div class="footer">
      <div class="checkbox">
        <span style="font-size: 12px">选择数据未同步的收藏夹</span>
        <a-switch checked-children="开" un-checked-children="关" />
      </div>
      <a-button type="primary">同步收藏夹</a-button>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.home {
  .tree-title {
    //overflow: hidden;
    //text-overflow: ellipsis;
    //white-space: nowrap
  }

  .favorite-list {
    height: 450px !important;
    background: #fff;
    overflow-y: auto;
    overflow-x: hidden;
    border-radius: 0 !important;

    .info {
      margin-right: 5px;
      color: #ff4d4f;
    }
  }

  .footer {
    height: 55px;
    background: #dddddd; // TODO
    display: flex;
    justify-content: space-around;
    align-items: center;
  }
}
</style>
