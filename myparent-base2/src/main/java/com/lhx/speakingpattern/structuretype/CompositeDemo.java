package com.lhx.speakingpattern.structuretype;

import java.util.ArrayList;
import java.util.List;

/**
 * 分公司 = 1部门   ---组合模式
 */
public class CompositeDemo {
    public static void main(String[] args) {
        INode root=new Node("root",null);
        INode a=new Node("A",root);
        INode b=new Node("B",root);
        INode c=new Node("C",root);
        root.add(a);
        root.add(b);
        root.add(c);

        INode b1=new Node("B1",b);
        INode b2=new Node("B2",b);
        INode b3=new Node("B3",b);
        b.add(b1);
        b.add(b2);
        b.add(b3);

        INode b21=new Node("B21",b2);
        INode b22=new Node("B22",b2);
        INode b23=new Node("B23",b2);
        b2.add(b21);
        b2.add(b22);
        b2.add(b23);

        INode c1=new Node("c1",c);
        INode c2=new Node("c2",c);
        INode c3=new Node("c3",c);
        c.add(c1);
        c.add(c2);
        c.add(c3);

        root.display();
    }
    private interface INode{
        void add(INode node);
        void remove(INode node);
        void display();
    }
    private static class Node implements INode{
        private List<INode> children=new ArrayList<>();
        private String name;
        private INode parent;
        @Override
        public void add(INode node) {
            this.children.add(node);
            children.iterator();
        }

        @Override
        public void remove(INode node) {
            this.children.remove(node);
        }

        @Override
        public void display() {
            Node parent = (Node) this.getParent();
            if (parent== null) {
                System.out.println("-"+this.getName());
            }else {
                StringBuilder sb=new StringBuilder();
                while (parent!=null){
                    sb.append("--");
                    parent=(Node)parent.getParent();
                }
                System.out.println(sb.toString()+this.getName());
            }
            children.stream()
                    .forEach((x) -> x.display());
        }

        public Node(String name, INode parent) {
            this.name = name;
            this.parent = parent;
        }

        public List<INode> getChildren() {
            return children;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public INode getParent() {
            return parent;
        }

        public void setParent(INode parent) {
            this.parent = parent;
        }
    }
}
