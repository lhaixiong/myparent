package lhxbase;

import com.lhxbase.datastructure.BinaryTree;
import com.lhxbase.datastructure.Link;
import org.junit.Test;

public class DatastructureTest {
    @Test
    public void testBinaryTree(){
        BinaryTree tree=new BinaryTree();
        tree.addNode(8);
        tree.addNode(3);
        tree.addNode(3);
        tree.addNode(10);
        tree.addNode(9);
        tree.addNode(1);
        tree.addNode(5);
        tree.addNode(5);
        tree.print();
    }
    @Test
    public void testLinkDelete(){
        Link link=new Link();
        link.addNode("AAA");
        link.addNode("BBB");
        link.addNode("CCC");
        link.addNode("DDD");
        System.out.println("before delete:");
        link.printNodes();

        link.deleteNode("CCC");
        System.out.println("after delete:");
        link.printNodes();
    }
    @Test
    public void testLinkContains(){
        Link link=new Link();
        link.addNode("AAA");
        link.addNode("BBB");
        link.addNode("CCC");
        link.addNode("DDD");
        System.out.println(link.containNode("CCC"));
    }
    @Test
    public void testLinkAdd(){
        Link link=new Link();
        link.addNode("AAA");
        link.addNode("BBB");
        link.addNode("CCC");
        link.addNode("DDD");
        link.printNodes();
    }
}
