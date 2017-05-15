package com.lhxbase.datastructure;

public class BinaryTree {
    private Node root;

    public void addNode(Comparable data){
        if(this.root==null){
            Node node=new Node(data);
            this.root=node;
        }else {
            this.root.add(data);
        }
    }
    public void print(){
        if(this.root!=null){
            this.root.print();
        }else {
            System.out.println("该二叉树一个节点也没有");
        }
    }

    class Node{
        private Comparable data;
        private Node leftNode;
        private Node rightNode;

        public Comparable getData() {
            return data;
        }

        public Node(Comparable data) {
            this.data = data;
        }
        public void add(Comparable data){
            Node node=new Node(data);

            if(this.getData().compareTo(data)>0){//新加入的节点小于当前节点，则放到二叉树左边
                if (this.leftNode==null){
                    this.leftNode=node;
                }else {
                    this.leftNode.add(data);
                }
            }else {//新加入的节点等于或大于当前节点，则放到二叉树左边
                if(this.rightNode==null){
                    this.rightNode=node;
                }else {
                    this.rightNode.add(data);
                }
            }
        }
        public void print(){//才有中序排列输出(左根右)
            if(this.leftNode!=null){
                this.leftNode.print();
            }
            System.out.println(this.data.toString());
            if(this.rightNode!=null){
                this.rightNode.print();
            }
        }
    }
}
