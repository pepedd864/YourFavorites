import '@/content/styles/style.scss'

import { createApp } from 'vue'
import Content from '@/content/content.vue'

// 创建id为CRX-container的div
const crxApp = document.createElement('div')
crxApp.id = 'CRX-container'
// 将刚创建的div插入body最后
document.body.appendChild(crxApp)

// 创建Vue APP
const app = createApp(Content)

// 将Vue APP插入刚创建的div
app.mount('#CRX-container')
