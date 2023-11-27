// @ts-nocheck
import { createApp } from 'vue'
import Popup from '@/popup/popup.vue'
import '@/common/styles/global.scss'
import '@/common/styles/reset.scss'
import router from '@/popup/router'
import VueTransitions from '@morev/vue-transitions'
import '@morev/vue-transitions/styles'
import pinia from '@/stores/index'
// 路由鉴权
import '@/popup/router/permisson'

const app = createApp(Popup);
app.use(pinia);
app.use(router);
app.use(VueTransitions, {
  // Plugin options (optional, described below)
});
app.mount("#app");
