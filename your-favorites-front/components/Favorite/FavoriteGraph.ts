import type { GraphOptions, TreeGraph } from '@antv/g6'
import G6 from '@antv/g6'

export default class FavoriteGraph {
  private readonly graph?: TreeGraph

  constructor(data: any, container: string, containerDom: HTMLDivElement) {
    if (data === undefined || data === null) return
    const width = containerDom.offsetWidth
    const height = containerDom.offsetHeight

    const defaultConfig = {
      width,
      height,
      modes: {
        default: ['drag-canvas', 'zoom-canvas'],
      },
      // fitView: true,
      animate: true,
      defaultNode: {
        type: 'flow-rect',
      },
      defaultEdge: {
        type: 'cubic-horizontal',
        style: {
          stroke: '#CED4D9',
        },
      },
      layout: {
        type: 'dendrogram',
        direction: 'H',
        nodeSep: 200,
        rankSep: 400,
      },
    } as unknown as GraphOptions

    const config = {
      animateCfg: {
        duration: 400,
        easing: 'easePolyInOut',
      },
      // maxZoom: 2.5,
      // minZoom: 0.5,
    }

    this.graph = new G6.TreeGraph({
      ...defaultConfig,
      ...config,
      renderer: 'svg',
      container: container,
      // plugins: [tooltip],
    })

    this.graph.data(data)
    this.graph.render()
    this.graph.fitView()
  }

  destroy() {
    if (!this.graph || this.graph.destroyed) return
    this.graph.destroy()
  }

  getInstance(): TreeGraph {
    return <TreeGraph>this.graph
  }
}
