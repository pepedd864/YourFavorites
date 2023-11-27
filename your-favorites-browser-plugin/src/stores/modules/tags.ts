import { ref } from 'vue'
import { defineStore } from 'pinia'

export type Tag = {
  id: string
  title: string
  desc: string
  level: number
  index: number
  key: string
  createTime: string
  updateTime?: string
  remark?: string
}

export const useTagsStore = defineStore('tagsStore', () => {
  const tags = ref<Tag[]>([])
  return {
    tags,
  }
})
