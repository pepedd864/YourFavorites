<script lang="ts" setup>
import { computed, reactive, ref } from 'vue'

// 接受父组件传递的方法
const emit = defineEmits(['onClose', 'onConfirm'])
// 接收父组件传递的参数
const props = defineProps(['visible', 'title'])

// 输入框中的内容
const text = ref('')
//模态框
const modalState = reactive({
  open: computed({
    get() {
      return props.visible
    },
    set() {
      emit('onClose')
    },
  }),
  title: props.title,
  handleOk: () => {
    emit('onConfirm')
  },
  handleCancel: () => {
    emit('onClose')
  },
})
</script>

<template>
  <a-modal
    v-model:open="modalState.open"
    :title="modalState.title"
    centered
    destroy-on-close
    @cancel="modalState.handleCancel"
    @ok="modalState.handleOk"
  >
    <div class="main-content-con">
      <slot></slot>
    </div>
  </a-modal>
</template>

<style lang="scss" scoped></style>
