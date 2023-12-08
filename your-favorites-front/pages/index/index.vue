<script lang="ts" setup>
import ua from '~/utils/ua'
import { removeUrlPrefix } from '~/utils/utils'

console.log(removeUrlPrefix('https://www.baidu.com/'))
let uaInfo = ua()
</script>

<template>
  <div id="app">
    <div class="background"></div>
    <div class="search-bar-area">
      <search-bar style="max-width: 700px" />
    </div>
    <div class="favorite-area">
      <favorite />
    </div>
    <div>{{ uaInfo }}</div>
  </div>
</template>

<style lang="scss" scoped>
#app {
  .background {
    position: absolute;
    top: 0;
    left: 0;
    width: 100vw;
    height: 100vh;
    z-index: -1;
  }

  .search-bar-area {
    display: flex;
    height: 200px;
    justify-content: center;
    align-items: center;
    background-color: #eee;
    background-image: radial-gradient(transparent 1px, #fff 1px);
    background-size: 6px 6px;
    backdrop-filter: saturate(50%) blur(4px);
    -webkit-backdrop-filter: saturate(50%) blur(4px);

    @media screen and (max-width: 600px) {
      display: none;
    }
  }

  .favorite-area {
    margin: 3vh 0 5vh 0;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
  }

  .footer {
    position: relative;
    text-align: center;
  }
}
</style>
