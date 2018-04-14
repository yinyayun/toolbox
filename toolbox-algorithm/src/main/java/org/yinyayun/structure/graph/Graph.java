package org.yinyayun.structure.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * 图的存储，主要有两种方方式：邻接矩阵，邻接列表 <br>
 * 下面主要使用邻接列表
 * 
 * @author yinyayun
 *
 */
public class Graph {
	private int edges;
	private int vertexs;
	private List<Integer>[] vertexList;

	public Graph(int vertexs) {
		this.vertexs = vertexs;
		this.vertexList = new List[this.vertexs];
	}
}
