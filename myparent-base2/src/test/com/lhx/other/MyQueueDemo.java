package com.lhx.other;

public class MyQueueDemo {
    public static void main(String[] args) {
        MyQueue<Integer> queue=new MyQueue<>();
        queue.push(3);
        queue.push(2);
        queue.push(1);
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
        System.out.println(queue.pop());
    }
    private static class MyQueue<T>{
        private Node<T> head;
        private Node<T> last;

        private class Node<T>{
            T item;
            Node<T> next;
            public Node(T x){
                this.item=x;
            }
        }

        public MyQueue() {
            Node<T> node=new Node<>(null);
            head=last=node;
        }

        /**
         * 入队
         * @param item
         */
        public void push(T item){
            Node<T> node=new Node<>(item);
            last.next=node;
            last=node;
        }

        /**
         * 出队
         * @return
         */
        public T pop(){
            Node<T> h=head;
            Node<T> node=h.next;
            if (node == null) {
                return null;
            }
            head=node;
            h.next=h;//help gc
            return node.item;
        }
    }
}
