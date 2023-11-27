<script lang="ts" setup>
import { getDomain, isEmpty } from '@/utils/utils'

const searchContent = ref()
const searchInput = ref<HTMLInputElement>()
let clickFlag = false

function toggleSearchList() {
  const searchList = document.querySelector('.search-list') as HTMLDivElement
  if (!searchList.classList.contains('search-list-show')) {
    searchList.classList.add('search-list-show')
    clickFlag = true
  } else {
    closeSearchList()
  }
}

function closeSearchList() {
  const searchList = document.querySelector('.search-list') as HTMLDivElement
  if (searchList.classList.contains('search-list-show') && !clickFlag) {
    searchList.classList.remove('search-list-show')
  }
}

function focusInput() {
  searchInput.value?.focus()
}

function handleSearchSubmit() {
  if (isEmpty(searchContent.value)) return
  // 搜索
  window.open(
    searchEngineList.value[activeEngine.value].url + searchContent.value,
  )
}

function changeSearchEngine(index: number) {
  activeEngine.value = index
  closeSearchList()
}

const searchEngineList = ref([
  {
    name: '谷歌',
    url: 'https://www.google.com/search?q=',
    placeholder: '与世界连接',
  },
  {
    name: '必应',
    url: 'https://www.bing.com/search?q=',
    placeholder: '来自世界第一软件公司的搜索引擎',
  },
  {
    name: '百度',
    url: 'https://www.baidu.com/s?wd=',
    placeholder: '百度一下，你就知道',
  },
  {
    name: 'Github',
    url: 'https://github.com/search?q=',
    placeholder: '与全世界的程序员交流',
  },
])
const activeEngine = ref(0)

nextTick(() => {
  focusInput()
})
</script>

<template>
  <div class="search-bar-panel">
    <form class="search-input-form" @submit.prevent="handleSearchSubmit()">
      <a-input
        ref="searchInput"
        v-model:value="searchContent"
        :placeholder="searchEngineList[activeEngine].placeholder"
        class="search-bar"
        @blur="closeSearchList"
        @keydown.enter="handleSearchSubmit"
      >
        <template #prefix>
          <button
            class="search-icon"
            @mousedown.prevent="toggleSearchList"
            @click.prevent="clickFlag = false"
          >
            <img
              :src="`https://api.iowen.cn/favicon/${getDomain(
                searchEngineList[activeEngine].url,
              )}.png`"
              alt=""
            />
          </button>
        </template>
        <template #suffix>
          <Icon
            icon="SearchOutlined"
            style="cursor: pointer"
            @click="handleSearchSubmit"
          />
        </template>
      </a-input>
    </form>
    <div class="search-list">
      <div
        v-for="(item, index) in searchEngineList"
        :key="item.name"
        class="search-list-item"
        @click="changeSearchEngine(index)"
      >
        <div class="icon">
          <img
            :alt="item.name"
            :src="`https://api.iowen.cn/favicon/${getDomain(item.url)}.png`"
          />
        </div>
        <div>{{ item.name }}</div>
      </div>
      <div class="search-list-item" style="font-size: 40px">+</div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.search-bar-panel {
  .search-input-form {
    .search-bar {
      font-size: 22px;

      &:focus {
        outline: none;
      }

      .search-icon {
        width: 30px;
        height: 30px;
        cursor: pointer;
        outline: none;
        border: none;
        background: transparent;

        img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }

  .search-list {
    position: absolute;
    display: flex;
    justify-content: space-around;
    box-shadow: 2px 3px 5px rgba(0, 0, 0, 0.4);
    border-radius: 7px;
    margin-top: 10px;
    padding: 10px;
    opacity: 0;
    transform: translateY(-10px);
    background: rgb(255, 255, 255, 0.7);
    transition:
      opacity 0.3s,
      transform 0.3s;

    .search-list-item {
      display: flex;
      flex-direction: column;
      min-width: 60px;
      align-items: center;
      justify-content: center;
      border-radius: 5px;
      padding: 5px;
      background: #fff;
      cursor: pointer;

      .icon {
        width: 30px;
        height: 30px;
        margin-right: 3px;

        img {
          width: 100%;
          height: 100%;
        }
      }
    }
  }

  .search-list-show {
    opacity: 1;
    transform: translateY(0px);
  }
}
</style>
