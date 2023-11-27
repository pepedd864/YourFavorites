import { defineConfig, loadEnv } from "vite";
import vue from "@vitejs/plugin-vue";
import path from "path";

// https://vitejs.dev/config/
export default defineConfig(({ command, mode }) => {
  const env = loadEnv(mode, process.cwd(), '')
  return {
    build: {
      // 输出目录
      outDir: env.VITE_CRX_BACKGROUND_OUTDIR,
      lib: {
        entry: [path.resolve(__dirname, 'src/background/index.ts')],
        // background script不支持ES6，因此不用使用es模式，需要改为cjs模式
        formats: ['cjs'],
        // 设置生成文件的文件名
        fileName: () => {
          // 将文件后缀名强制定为js，否则会生成cjs的后缀名
          return 'background.js'
        },
      },
    },
    resolve: {
      alias: {
        '@': path.resolve(__dirname, 'src'),
      },
    },
    plugins: [vue()],
  }
})
