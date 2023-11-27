<script lang="ts" setup>
import { ref, watch } from 'vue'
import Dialog from './components/Dialog/Dialog.vue'
import FloatButton from './components/FloatButton/FloatButton.vue'

const isShowDialog = ref(false)
watch(isShowDialog, () => {
  setTimeout(() => {
    if (isShowDialog.value) {
      const iframe = document.querySelector('#iframe') as HTMLIFrameElement
      const loading = document.querySelector('.loading') as HTMLElement
      iframe?.addEventListener('DOMContentLoaded ', () => {
        console.log(1)
        loading.style.display = 'none'
      })
    }
  }, 1000)
})
</script>

<template>
  <div class="CRX-content">
    <float-button
      style="bottom: 100px; right: 20px"
      @handleClick="isShowDialog = !isShowDialog"
    >
      <template v-slot="{ isDragging }">
        <div class="content-entry"></div>
      </template>
    </float-button>
    <Dialog
      :visible="isShowDialog"
      title="书签思维导图"
      @on-close="
        () => {
          isShowDialog = false
        }
      "
    >
      <div class="loading">
        <a-spin />
      </div>
      <iframe
        id="iframe"
        src="http://localhost:3000/"
        style="width: 100%; height: 70vh; border: none"
      />
    </Dialog>
  </div>
</template>

<style lang="scss" scoped>
.CRX-content {
  position: relative;

  .content-entry {
    width: 50px;
    height: 50px;
    background: url('images/content-icon.png');
    background-size: 100% 100%;
  }

  .loading {
    position: relative;
    height: 100vh !important;
    width: 100vw !important;
    display: flex;
    justify-content: center;
    align-items: center;
  }
}
</style>
