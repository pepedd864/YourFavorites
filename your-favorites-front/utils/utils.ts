export function getRandomHexColor() {
  const letters = '0123456789ABCDEF' // 十六进制字符
  let color = '#'
  for (let i = 0; i < 6; i++) {
    color += letters[Math.floor(Math.random() * 16)] // 随机选择一个字符
  }
  return color // 返回十六进制颜色值
}

export function getDomain(url: string) {
  const domainRegex = /^(?:https?:\/\/)?(?:[^@\n]+@)?(?:www\.)?([^:\/\n]+)/im
  const matches = url.match(domainRegex)
  return matches?.[1]
}

export function isEmpty(str: string) {
  return str === null || str === undefined || str === ''
}

export interface NestedObject {
  [key: string]: any
}

export function findElement(
  obj: NestedObject,
  key: string,
  value: any,
): NestedObject | null {
  // 检查当前对象是否具有所需的属性键和值
  if (obj.hasOwnProperty(key) && obj[key] == value) {
    // 不能===全等，比较引用是不可能相等的
    return obj
  }

  for (let prop in obj) {
    if (typeof obj[prop] === 'object') {
      let result = findElement(obj[prop], key, value)
      if (result) {
        return result
      }
    }
  }

  return null
}

/**
 * 获取字符串中中文和英文的长度
 * @param str 字符串
 * @returns {Array<number>} [中文长度, 英文长度]
 */
export function getStrLength(str: string) {
  let chinese = str.match(/[\u4e00-\u9fa5]/g)
  let english = str.match(/[a-zA-Z0-9]/g)
  return [chinese?.length ?? 0, english?.length ?? 0]
}
