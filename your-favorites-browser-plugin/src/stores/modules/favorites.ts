import { ref } from 'vue'
import { defineStore } from 'pinia'

export type Favorite = {
  id: string
  tag_id: string
  title: string
  desc: string
  url: string
  level: number
  index: number
  key: string
  createTime: string
  updateTime?: string
  remark?: string
}
export const useFavoritesStore = defineStore('favoritesStore', () => {
  const favorites = ref<Favorite[]>([])
  return {
    favorites,
  }
})
