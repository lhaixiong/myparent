package com.lhxbase.datastructure;

public class Link {
    private Node root;

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    /**
     * 单向链表增加节点
     * @param content
     */
    public void addNode(String content){
        Node node=new Node(content);
        if(this.root==null){
            this.root=node;
        }else {
            this.root.add(node);
        }
    }
    /**
     * 单向链表删除节点
     * @param content
     */
    public void deleteNode(String content){
        //先判断链表是否含有该节点
        if(this.containNode(content)){
            //判断是否为根节点
            if(this.root.getContent().equals(content)){
                this.root=this.root.next;
            }else {
                this.root.next.delete(root,content);
            }
        }
    }

    /**
     * 打印链表节点
     */
    public void printNodes(){
        this.root.print();
    }
    public boolean containNode(String content){
        return this.root.contain(content);
    }

    class Node{
        private String content;
        private Node next;
        public Node(String content){
            this.content=content;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
        public void add(Node node){
            if(this.next==null){
                this.next=node;
            }else {
                this.next.add(node);
            }
        }
        public void print(){
            System.out.println(this.getContent());
            if(this.next!=null){
                this.next.print();
            }
        }
        public boolean contain(String content){
            if(this.getContent().equals(content)){
                return true;
            }else {
                if(this.next==null){
                    return false;
                }
                return this.next.contain(content);
            }
        }
        public void delete(Node previous,String content){
            if(this.getContent().equals(content)){
                previous.next=this.next;
            }else {
                if(this.next!=null){
                    this.next.delete(this,content);
                }
            }
        }
    }
}
