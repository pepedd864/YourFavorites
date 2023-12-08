import type { IG6GraphEvent } from '@antv/g6'
import G6 from '@antv/g6'

const tooltip = new G6.Tooltip({
  offsetX: 20,
  offsetY: 30,
  // 允许出现 tooltip 的 item 类型
  itemTypes: ['dom'],
  // custom the tooltip's content
  // 自定义 tooltip 内容
  getContent: (e: IG6GraphEvent | undefined) => {
    const outDiv = document.createElement('div') as HTMLDivElement
    //outDiv.style.padding = '0px 0px 20px 0px';
    const nodeName = e?.item?.getModel().name as string
    let formatedNodeName = ''
    for (let i = 0; i < nodeName.length; i++) {
      formatedNodeName = `${formatedNodeName}${nodeName[i]}`
      if (i !== 0 && i % 20 === 0) formatedNodeName = `${formatedNodeName}<br/>`
    }
    outDiv.innerHTML = `${formatedNodeName}`
    return outDiv
  },
  shouldBegin: (e: IG6GraphEvent | undefined) => {
    return e?.target.get('name') === 'name-shape'
  },
})

export default tooltip
