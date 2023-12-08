// @ts-nocheck
/*global chrome*/
// manifest.json的Permissions配置需添加declarativeContent权限
if (chrome.runtime?.onInstalled) {
  chrome.runtime.onInstalled.addListener(function() {
    // 默认先禁止Page Action。如果不加这一句，则无法生效下面的规则
    chrome.action.disable()
    chrome.declarativeContent.onPageChanged.removeRules(undefined, () => {
      // 设置规则
      let rule = {
        // 运行插件运行的页面URL规则
        conditions: [
          new chrome.declarativeContent.PageStateMatcher({
            pageUrl: {
              // 适配所有域名以“www.”开头的网页
              // hostPrefix: 'www.'
              // 适配所有域名以“.element-plus.org”结尾的网页
              // hostSuffix: '.element-plus.org',
              // 适配域名为“element-plus.org”的网页
              // hostEquals: 'element-plus.org',
              // 适配https协议的网页
              // schemes: ['https'],
            },
          }),
        ],
        actions: [new chrome.declarativeContent.ShowAction()],
      }
      // 整合所有规则
      const rules = [rule]
      // 执行规则
      chrome.declarativeContent.onPageChanged.addRules(rules)
    })
  })
}


// 导出获取书签的函数
export function getBookmarks() {
  return new Promise((resolve, reject) => {
    if (chrome.bookmarks?.getTree === undefined) reject('无法获取书签')
    chrome.bookmarks.getTree(function (bookmarkTreeNodes) {
      resolve(bookmarkTreeNodes)
    })
  })
}
