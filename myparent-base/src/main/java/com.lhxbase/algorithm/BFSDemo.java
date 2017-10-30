package com.lhxbase.algorithm;

import java.util.LinkedHashSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

/**
 * 宽度优先搜索 参考有道云算法
 */
public class BFSDemo {
    public static void main(String[] args) {
        Graph graph=new Graph();
        graph.initGraph();
        Queue queue=new LinkedBlockingQueue<>();
        queue.add(graph.root);

        bfs(queue);

    }
    /**
     * (1) 顶点V 入队列。
     (2) 当队列非空时继续执行，否则算法为空。
     (3) 出队列，获得队头节点V，访问顶点V 并标记V 已经被访问。
     (4) 查找顶点V 的第一个邻接顶点col。
     (5) 若V 的邻接顶点col 未被访问过，则col 进队列。
     (6) 继续查找V 的其他邻接顶点col，转到步骤(5)，若V 的所有邻接顶点都已经被访
     问过，则转到步骤(2)。
     * @param queue
     */
    public static void bfs(Queue<Node> queue){
        while (!queue.isEmpty()) {
            Node node = queue.poll();
            if (node != null) {
                node.setVisited(true);
                System.out.println(node.getContent()+"被访问");
                for (Node child : node.getChilds()) {
                    if(!child.isVisited){
                        queue.offer(child);
                    }
                }
            }
        }
    }

    private static class Node{
        private String content;
        private boolean isVisited=false;
        private Set<Node> childs;

        public Node getChildByContent(String content){
            Set<Node> set = this.childs.stream()
                    .filter(x -> x.getContent().equals(content))
                    .collect(Collectors.toSet());
            return (Node) set.toArray()[0];
        }

        public Node(String content, Set<Node> childs) {
            this.content = content;
            this.childs = childs;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Set<Node> getChilds() {
            return childs;
        }

        public void setChilds(Set<Node> childs) {
            this.childs = childs;
        }

        public boolean isVisited() {
            return isVisited;
        }

        public void setVisited(boolean isVisited) {
            this.isVisited = isVisited;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "content='" + content + '\'' +
                    ", isVisited=" + isVisited +
                    ", childs=" + childs +
                    '}';
        }
    }
    private static class Graph{
        private Node root;
        /**
         * 初始化图
         */
        public void initGraph(){
            this.root=new Node("A",new LinkedHashSet<>());

            Set<Node> aChilds = root.getChilds();
            aChilds.add(new Node("B",new LinkedHashSet<Node>()));
            aChilds.add(new Node("C",new LinkedHashSet<Node>()));
            aChilds.add(new Node("D",new LinkedHashSet<Node>()));
            aChilds.add(new Node("E",new LinkedHashSet<Node>()));
            aChilds.add(new Node("F",new LinkedHashSet<Node>()));

            Node f=root.getChildByContent("F");
            Set<Node> fChilds = f.getChilds();
            fChilds.add(new Node("G",new LinkedHashSet<Node>()));


            Node e=root.getChildByContent("E");
            Set<Node> eChilds = e.getChilds();
            eChilds.add(new Node("H",new LinkedHashSet<Node>()));

            Node h=e.getChildByContent("H");
            h.getChilds().add(new Node("I",new LinkedHashSet<Node>()));
        }
    }
}


