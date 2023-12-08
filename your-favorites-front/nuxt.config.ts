// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  // 开启ssr服务端渲染
  ssr: false,
  devtools: { enabled: true },
  modules: [
    '@pinia/nuxt',
    '@pinia-plugin-persistedstate/nuxt',
    '@ant-design-vue/nuxt',
  ],
  devServer: {
    port: 3000,
  },
  app: {
    head: {
      // 解决403 Forbbiden问题
      meta: [{ name: 'referrer', content: 'no-referrer' }],
    },
  },
})
