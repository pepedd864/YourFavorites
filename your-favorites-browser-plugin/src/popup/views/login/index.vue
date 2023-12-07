<script lang="ts" setup>
import { nextTick, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useRequest } from 'alova'
import { useUserStore } from '@/stores'
import { getCodeImgApi, loginApi } from '@/api/auth'

const formRef = ref()
const captchaEnabled = ref(true)
const codeUrl = ref()

const loginForm = reactive({
  username: 'admin',
  password: '123456',
  code: '',
  uuid: '',
  rememberMe: false,
})
const rules = ref({
  username: [{ required: true, message: '请输入帐户名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
})

const router = useRouter()
const userStore = useUserStore()

// 注：import('ant-design-vue/es')['InputPassword'] 为 unplugin-vue-components 解析的引用
const pwdInputRef = ref<any>(null)

const { loading, send, onSuccess } = useRequest(
  // request.Post('https://www.fastmock.site/mock/34475c3a26f32e7b37e76af47125375f/mockApi/login'),
  loginApi(loginForm),
  {
    // 默认不发出
    immediate: false,
  },
)

onSuccess((event) => {
  const data = event.data as any
  console.log('data', data)
  if (data) {
    userStore.token = data.token
    userStore.userInfo = data.userVO
    // 获取当前路由的参数, 跳转到指定页面
    const { redirect } = router.currentRoute.value.query
    router.push((redirect as string) || '/')
  }
})

async function login() {
  formRef.value.validate().then(() => {
    send(true)
  })
}

async function getCode() {
  const res = await getCodeImgApi()
  captchaEnabled.value =
    res.captchaEnabled === undefined ? true : res.captchaEnabled
  if (captchaEnabled.value) {
    codeUrl.value = 'data:image/gif;base64,' + res.img
    loginForm.uuid = res.uuid || ''
  }
}

function rememberMe(e: any) {
  loginForm.rememberMe = e.target.checked
}

nextTick(() => {
  pwdInputRef.value?.focus()
  getCode()
})
</script>

<template>
  <div class="login">
    <img alt="" class="logo" src="./logo.png" />
    <div class="title">收藏夹管理器插件</div>
    <a-form ref="formRef" :model="loginForm" :rules="rules" class="login-form">
      <a-form-item name="username">
        <a-input v-model:value="loginForm.username" placeholder="账户: admin">
          <!--<template #prefix>-->
          <!--  <Icon icon="UserOutlined" />-->
          <!--</template>-->
        </a-input>
      </a-form-item>
      <a-form-item name="password">
        <a-input-password
          v-model:value="loginForm.password"
          placeholder="密码: admin123"
        >
          <!--<template #prefix>-->
          <!--  <Icon icon="LockOutlined" />-->
          <!--</template>-->
        </a-input-password>
      </a-form-item>
      <a-row v-if="captchaEnabled" :gutter="16">
        <a-col :span="16" class="gutter-row">
          <a-form-item name="code">
            <a-input v-model:value="loginForm.code" placeholder="验证码">
              <!--<template #prefix>-->
              <!--  <Icon icon="SecurityScanOutlined" />-->
              <!--</template>-->
            </a-input>
          </a-form-item>
        </a-col>
        <a-col :span="8" class="gutter-row">
          <img
            :src="codeUrl"
            class="getCaptcha"
            style="cursor: pointer"
            @click="getCode"
          />
        </a-col>
      </a-row>
      <!--<a-form-item name="rememberMe">-->
      <!--  <div class="user-login-other">-->
      <!--    <a-checkbox :checked="loginForm.rememberMe" @change="rememberMe">-->
      <!--      记住密码-->
      <!--    </a-checkbox>-->
      <!--    <router-link class="register" :to="{ name: 'register' }"-->
      <!--      >注册账户-->
      <!--    </router-link>-->
      <!--  </div>-->
      <!--</a-form-item>-->
      <a-form-item>
        <a-button
          :loading="loading"
          htmlType="submit"
          style="width: 100%"
          @click="login"
        >
          确定
        </a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<style lang="scss" scoped>
.login {
  position: relative;
  padding: 50px 0;
  height: 100%;
  background: #4bb8e5;

  .logo {
    display: block;
    margin: 0 auto 5px;
  }

  .title {
    text-align: center;
    color: #fff;
    font-size: 24px;
    font-weight: bold;
    margin-bottom: 15px;
  }

  .login-form {
    padding: 15px;
  }
}
</style>
