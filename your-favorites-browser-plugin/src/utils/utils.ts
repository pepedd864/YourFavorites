export function removeUrlPrefix(url: string) {
  // 定义正则表达式，匹配"http://", "https://", "ftp://"等前缀
  let regex = /^(http:\/\/|https:\/\/|ftp:\/\/)/i

  // 使用空字符串替换匹配到的前缀
  return url.replace(regex, '')
}
