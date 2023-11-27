import request from '@/utils/request'

export type LoginFormType = {
  username: string
  password: string
  code: string
}

export function loginApi(loginForm: LoginFormType) {
  return request.Post(
    '/auth/login',
    loginForm,
    // @ts-ignore
    { headers: { 'Content-Type': 'application/json' }, ignoreToken: true },
  )
}

export type CaptchaResponse = {
  uuid?: string
  img?: string
  captchaEnabled?: boolean
}

export function getCodeImgApi() {
  return request
    .Get('/auth/captchaImage', {
      // @ts-ignore
      ignoreToken: true,
      // 强制请求，防止缓存
    })
    .send(true) as Promise<CaptchaResponse>
}
