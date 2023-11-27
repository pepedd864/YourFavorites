<script lang="ts" setup>
import { onMounted, onUnmounted, reactive, ref } from 'vue'

let floatButton: HTMLElement
const isDragging = ref(false)
const emit = defineEmits(['handleClick'])

const originalPosition = reactive({
  x: 0,
  y: 0,
})

const mousedownOffset = reactive({
  x: 0,
  y: 0,
})

const elementPosition = reactive({
  x: 0,
  y: 0,
})

function onMousedown(event: MouseEvent) {
  event.stopPropagation()
  isDragging.value = false
  mousedownOffset.x = event.clientX - originalPosition.x
  mousedownOffset.y = event.clientY - originalPosition.y
  document.addEventListener('mousemove', onMousemove, true)
  document.addEventListener('mouseup', onMouseup, true)
}

function onMousemove(event: MouseEvent) {
  event.stopPropagation()
  isDragging.value = true
  elementPosition.x = event.clientX - mousedownOffset.x
  elementPosition.y = event.clientY - mousedownOffset.y
}

function onMouseup(event: MouseEvent) {
  if (!isDragging.value) emit('handleClick')
  event.stopPropagation()
  isDragging.value = false
  document.removeEventListener('mousemove', onMousemove, true)
  document.removeEventListener('mouseup', onMouseup, true)
  restore()
}

function restore() {
  const transformStyle = window
    .getComputedStyle(floatButton)
    .getPropertyValue('transform')
  const matrix = new DOMMatrixReadOnly(transformStyle)
  const translateX = matrix.m41 // 获取 X 轴方向的 translate 值
  const translateY = matrix.m42 // 获取 Y 轴方向的 translate 值

  originalPosition.x = translateX
  originalPosition.y = translateY
  elementPosition.x = originalPosition.x
  elementPosition.y = originalPosition.y
}

onMounted(() => {
  setTimeout(() => {
    floatButton = document.querySelector('.float-button') as HTMLElement
    restore()
    floatButton.addEventListener('mousedown', onMousedown, true)
  }, 500)
})

onUnmounted(() => {
  floatButton.removeEventListener('mousedown', onMousedown, true)
})
</script>

<template>
  <div
    :style="{
      cursor: `${isDragging ? 'move' : 'pointer'}`,
      transform: `translate(${elementPosition.x}px, ${elementPosition.y}px)`,
    }"
    class="float-button"
  >
    <slot :isDragging="isDragging"></slot>
  </div>
</template>

<style scoped>
.float-button {
  display: inline-block;
  position: fixed;
  background: transparent;
  background-size: cover;
}
</style>
