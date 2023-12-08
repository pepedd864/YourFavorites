// @ts-nocheck
// 自定义节点、边
import G6 from '@antv/g6'
import './nodeStyle.scss'
import { getStrLength, removeUrlPrefix } from '~/utils/utils'

export default function() {

  /**
   * 自定义节点
   */
  G6.registerNode(
    'flow-rect',
    {
      shapeType: 'flow-rect',
      draw(cfg, group) {
        function handleCollapse(e) {
          const target = e.target
          const id = target.get('modelId')
          const item = graph.findById(id)
          const nodeModel = item.getModel()
          nodeModel.collapsed = !nodeModel.collapsed
          graph.layout()
          graph.setItemState(item, 'collapse', nodeModel.collapsed)
        }

        let { title, desc, url, collapsed } = cfg

        this.drawLinkPoints(cfg, group)

        let width = 160
        let height = 90
        let padding = 10

        // 计算宽度
        // 如果字符数大于8 中文+14 英文+10 大于14 的部分截取换为省略号
        if (title.length > 8) {
          const [chinese, english] = getStrLength(title)
          width = width + chinese * 14 + english * 10 - 8 * 12
          width = width > 300 ? 300 : width
        }
        if (title.length > 14) {
          title = title.substr(0, 14) + '...'
        }

        /** 根节点 **/
        if (url === undefined || url === '') {
          let width1 = width - padding
          let height1 = height - 52 - padding
          let html1 = `
          <div class='node-item' style='width: ${width1}px;height: ${height1}px'>
            <div class='node-header' style='background: rgb(0,220,130)'>
              <img src='https://api.iowen.cn/favicon/www.google.com.png' alt=''>
              <span>${title}</span>
            </div>
            <div class='collapse' onclick='${handleCollapse}'>${
              collapsed ? '-' : '+'
            }</div>
          </div>
        `
          return group.addShape('dom', {
            attrs: {
              width: width1 + padding,
              height: height1 + padding,
              html: html1,
            },
            draggable: true,
          })
        }

        /** 叶子节点 **/
        let iconurl = removeUrlPrefix(url)
        let width2 = width - padding
        let height2 = height - padding
        let iconSize = height2 - 27
        let html2 = `
         <div class='node-item node-item-hover' style='width: ${width2}px;height: ${height2}px;'>
            <a href='${url}' target='_blank'>
              <div class='node-header'>
                ${title}
              </div>
              <div class='node-bottom'>
                 <div class='node-icon' style='height: ${iconSize}px; width: ${iconSize}px;'>
                  <img src='https://api.iowen.cn/favicon/${iconurl}.png' alt='' onerror="this.src='https://api.iowen.cn/favicon/www.google.com.png';this.onerror=null;">
                 </div>
                 <div class='node-desc'>
                    <span>
                    ${desc}
                    </span>
                  </div>
              </div>
            </a>
          </div>
        `
        return group.addShape('dom', {
          attrs: {
            width: width2 + padding,
            height: height2 + padding,
            html: html2,
          },
          draggable: true,
        })
      },
      update(cfg, item) {
        const group = item.getContainer()
        this.updateLinkPoints(cfg, group)
      },
      setState(name, value, item) {
        if (name === 'collapse') {
          const group = item.getContainer()
          const collapseText = group.find(
            (e) => e.get('name') === 'collapse-text',
          )
          if (collapseText) {
            if (!value) {
              collapseText.attr({
                text: '-',
              })
            } else {
              collapseText.attr({
                text: '+',
              })
            }
          }
        }
      },
      getAnchorPoints() {
        return [
          [0, 0.5],
          [1, 0.5],
        ]
      },
    },
    'rect',
  )

  G6.registerEdge(
    'flow-cubic',
    {
      getControlPoints(cfg) {
        let controlPoints = cfg.controlPoints // 指定controlPoints
        if (!controlPoints || !controlPoints.length) {
          const { startPoint, endPoint, sourceNode, targetNode } = cfg
          const {
            x: startX,
            y: startY,
            coefficientX,
            coefficientY,
          } = sourceNode ? sourceNode.getModel() : startPoint
          const { x: endX, y: endY } = targetNode
            ? targetNode.getModel()
            : endPoint
          let curveStart = (endX - startX) * coefficientX
          let curveEnd = (endY - startY) * coefficientY
          curveStart = curveStart > 40 ? 40 : curveStart
          curveEnd = curveEnd < -30 ? curveEnd : -30
          controlPoints = [
            { x: startPoint.x + curveStart, y: startPoint.y },
            { x: endPoint.x + curveEnd, y: endPoint.y },
          ]
        }
        return controlPoints
      },
      getPath(points) {
        const path = []
        path.push(['M', points[0].x, points[0].y])
        path.push([
          'C',
          points[1].x,
          points[1].y,
          points[2].x,
          points[2].y,
          points[3].x,
          points[3].y,
        ])
        return path
      },
    },
    'single-line',
  )
}
