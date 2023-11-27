// 路由守卫 如果没有token并且不在白名单中则跳转到登录页
import router from '@/popup/router'
import { nextTick } from 'vue'
import { useUserStore } from '@/stores'

let userStore: any = null;

nextTick(() => userStore = useUserStore());

router.beforeEach((to: any, from: any, next: any) => {
  if (to.meta.title) {
    document.title = to.meta.title;
  }
  if (to.meta.requireAuth) {
    if (userStore.token) {
      next();
    } else {
      next({
        path: "/login",
        // 在login中使用redirect参数跳转到登录前的页面
        query: { redirect: to.fullPath }
      });
    }
  } else {
    next();
  }
});
