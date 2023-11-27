import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/login',
    meta: { title: '登录', requireAuth: false },
    component: () => import('@/popup/views/login/index.vue'),
  },
  {
    path: '/',
    redirect: '/home',
    component: () => import('@/popup/views/entry/index.vue'),
    children: [
      {
        path: '/home',
        meta: { requireAuth: true, title: '首页' },
        component: () => import('@/popup/views/home/index.vue'),
      },
      {
        path: '/user',
        meta: { requireAuth: true, title: '用户' },
        component: () => import('@/popup/views/user/index.vue'),
      },
      { path: '', redirect: '/home' },
      { path: '/:pathMatch(.*)', redirect: '/home' },
    ],
  },
]

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})

export default router;
