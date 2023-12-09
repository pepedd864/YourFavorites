import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    meta: { title: '登录', requireAuth: false },
    component: () => import('@/popup/views/login/index.vue'),
  },
  {
    path: '/',
    redirect: '/favorite',
    component: () => import('@/popup/views/entry/index.vue'),
    children: [
      {
        path: '/favorite',
        meta: { requireAuth: true, title: '收藏夹' },
        component: () => import('@/popup/views/favorite/index.vue'),
      },
      {
        path: '/tag',
        meta: { requireAuth: true, title: '标签' },
        component: () => import('@/popup/views/tag/index.vue'),
      },
      {
        path: '/user',
        meta: { requireAuth: true, title: '用户' },
        component: () => import('@/popup/views/user/index.vue'),
      },
      { path: '', redirect: '/favorite' },
      { path: '/:pathMatch(.*)', redirect: '/favorite' },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router
