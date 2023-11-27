<script lang="ts" setup>
import { computed, reactive, ref, watch, watchEffect } from 'vue'
import { useDraggable } from '@vueuse/core'

const modalTitleRef = ref<HTMLElement>()
// 接受父组件传递的方法
const emit = defineEmits(['onClose'])
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
  handleOk: () => {},
  handleCancel: () => {},
})
const { x, y, isDragging } = useDraggable(modalTitleRef)
const startX = ref(0)
const startY = ref(0)
const startedDrag = ref(false)
const transformX = ref(0)
const transformY = ref(0)
const preTransformX = ref(0)
const preTransformY = ref(0)
const dragRect = ref({
  left: 0,
  right: 0,
  top: 0,
  bottom: 0,
})
watch([x, y], () => {
  if (!startedDrag.value) {
    startX.value = x.value
    startY.value = y.value
    const bodyRect = document.body.getBoundingClientRect()
    const titleRect = modalTitleRef.value?.getBoundingClientRect() || {
      width: 0,
      height: 0,
    }
    dragRect.value.right = bodyRect.width - titleRect.width
    dragRect.value.bottom = bodyRect.height - titleRect.height
    preTransformX.value = transformX.value
    preTransformY.value = transformY.value
  }
  startedDrag.value = true
})
watch(isDragging, () => {
  if (!isDragging.value) {
    startedDrag.value = false
  }
})
watchEffect(() => {
  if (startedDrag.value) {
    transformX.value =
      preTransformX.value +
      Math.max(Math.max(dragRect.value.left, x.value), dragRect.value.right) -
      startX.value
    transformY.value =
      preTransformY.value +
      Math.min(Math.max(dragRect.value.top, y.value), dragRect.value.bottom) -
      startY.value
  }
})
const transformStyle = computed(() => {
  return {
    transform: `translate(${transformX.value}px, ${transformY.value}px)`,
  }
})
</script>

<template>
  <a-modal
    ref="modalRef"
    v-model:open="modalState.open"
    centered
    destroy-on-close
    style="min-width: 60vw; min-height: 50vh"
    @cancel="modalState.handleCancel"
    @ok="modalState.handleOk"
  >
    <div class="main-content-con">
      <slot></slot>
    </div>
    <template #title>
      <div ref="modalTitleRef" style="width: 100%; cursor: move">
        {{ modalState.title }}
      </div>
    </template>
    <template #modalRender="{ originVNode }">
      <div :style="transformStyle">
        <component :is="originVNode" />
      </div>
    </template>
    <template #footer> </template>
  </a-modal>
</template>

<style lang="scss" scoped></style>
