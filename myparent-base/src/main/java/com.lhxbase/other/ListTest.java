package com.lhxbase.other;

import java.util.ArrayList;
import java.util.List;

/**
 * 泛型测试泛型总结
 * 阿里巴巴_码出高效:java开发手册-杨冠宝
 */
public class ListTest {
    public static void main(String[] args) {
        //
    }

    /**
     * List 、List<Object> 、List<?> 三者的区别
     */
    private static void test01() {
        //第一段 泛型出现前集合的定义方式
        List list = new ArrayList();
        list.add(new Object());
        list.add(new Integer(111));
        list.add(new String("list"));
        /**
         * 第一段说明在定义List之后，毫不犹豫地往集合里装入三种不同的对象Object、Integer 和String ，
         * 遍历没有问题，但是贸然以为里边的元素都是Integer ，使用强制转化，
         * 则抛出ClassCastException 异常
         */


        //第二段:把list赋值给objectList，注意objectList与list的区别是增加了泛型限制<Object>
        //把非泛型集合(旧代码兼容)赋值给泛型
        List<Object> objectList = list;
        list.add(new Object());
        list.add(new Integer(222));
        list.add(new String("objectList"));
        /**
         * 第二段说明把list赋值给objectList, objectList 是List<Object> 类型的，也可以再往里装入三
         * 种不同的对象。很多程序员认为List和List<Object>是完全相同的，至少从目前这两段来看是这样的
         */


        //第三段:把list赋值给integerList，注意integerList与list的区别是增加了泛型限制<Object>
        //把非泛型集合(旧代码兼容)赋值给泛型
        List<Integer> integerList = list;
        integerList.add(new Integer(333));
        //以下两行编译报错，泛型限制只能加入Integer的类型
//        integerList.add(new Object());
//        integerList.add(new String("integerList"));
        /**
         * 第三段说明，由于泛型在JDK5 之后才出现,考虑到向前兼容，因此历史代码有
         * 时需要赋值给新泛型代码,从编译器角度是允许的。这种代码似乎有点反人类，在实
         * 际故障案例中经常出现，来看一段问题代码
         */


        //第四段：把list赋值给quoteList，注意quoteList与list的区别是增加了泛通配符<?>
        List<?> quoteList = list;
        quoteList.remove(0);
        quoteList.clear();
        //以下编译报错，?泛型不允许增加任何元素
//        quoteList.add(new Object());
        /**
         * 第四段说明问号在正则表达式中可以匹配任何字符，List<?>称为通配符集合。
         * 它可以接受任何类型的集合引用赋值，不能添加任何元素， 但可以remove 和clear,
         * 并非immutable 集合。List<?>一般作为参数来接收外部的集合，或者返回一个不知道
         * 具体元素类型的集合。
         */

        //第五段：泛型间赋值问题，报类型错误
        //注意，数组可以这样赋值，因为它是协变的，而集合不是。
        //编译出错
//        objectList = integerList;
//        integerList=objectList;

        /**
         * List<T> 最大的问题是只能放置一种类型， 如果随意转换类型的话，
         * 就是“破窗理论”,泛型就是去了类型安全的意义。
         * 如果需要放置多种受泛型约束的类型呢？
         * JDK 的开发者顺应了民意， 实现了<? extends T>与<? super T>两种语法， 但是两者的区别非常微妙。
         * 简单来说，<? extends T>是Get First,适用于消费集合元素为主的场景，extends是put功能受限，
         * <? super T> 是Put First,适用于生产集合元素为主的场景，super是get功能受限。
         */
    }

    /**
     * 以加菲猫、猫、动物为例，说明extends 和super 的详细语法差异
     */
    private static void test02() {
        //第一段：声明三个依次继承的类的集合:Object>动物>猫>加菲猫
        List<Animal> animalList = new ArrayList<>();
        List<Cat> catList = new ArrayList<>();
        List<Garfield> garfieldList = new ArrayList<>();

        animalList.add(new Animal());
        catList.add(new Cat());
        garfieldList.add(new Garfield());
        /**
         * 第l 段，声明三个泛型集合，可以理解为三个不同的笼子， List<Animal＞住的是
         * 动物（反正就是动物世界里的动物） , List<Cat> 住的是猫（反正就是猫科动物），
         * List<Garfield＞住的是加菲猫（又懒又可爱的种猫）。Garfield 继承于Cat ，而Cat
         * 继承自Animal。
         */


        // 第二段:测试赋值操作
        // 下面编译错误,只能赋值Cat及Cat的子类
        // List<? extends Cat> extendCatFromAnimal=animalList;
        List<? super Cat> superCatFromAnimal = animalList;

        List<? extends Cat> extendCatFromCat = catList;
        List<? super Cat> superCatFromCat = catList;

        List<? extends Cat> extendCatFromGarfield = garfieldList;
        // 下面编译错误
        // List<? super Cat> superCatFromGarfield = garfieldList;

        /**
         * 第2 段，以Cat 类为核心，因为它有父类也有子类。定义类型限定集合，分别
         * 为List<? extends Cat> 和List<? super Cat>。在理解这两个概念时，暂时不要引人上
         * 界和下界，专注于代码本身就好。把List<Cat> 对象赋值给两者都是可以的。但是把
         * List<Animal＞赋值给List<Cat> 时会编译出错。因为能赋值给＜？ extends Cat> 的类型，
         * 只有Cat 自己和它的子类。尽管它是类型安全的，但依然有泛型信息，因而从笼子里
         * 取出来的必然是只猫，而List<Animal＞里边有可能住着毒蛇、鲤鱼、蝙蝠等其他动物。
         *
         * 把List<Garfield＞赋值给List<? super Cat＞时，也会编译报错。因为能赋值给
         * <? super Cat> 的类型，只有Cat 自己和它的父类。
         */


        //第三段：测试add方法
        //以下三行均编译错误,<? extends T>不能新增,put受限，属于get first
        // extendCatFromCat.add(new Animal());
        // extendCatFromCat.add(new Cat());
        // extendCatFromCat.add(new Garfield());

        //下行编译出错,只能添加Cat及Cat的子类
        // superCatFromCat.add(new Animal());
        superCatFromCat.add(new Cat());
        superCatFromCat.add(new Garfield());

        /**
         * 第3 段，所有的List<? extends T> 都会编译出锚，无法进行add 操作，这是因为
         * 除null 外，任何元素都不能被添加进〈？巳xtends T＞集合内。List<? super Cat> 可以往
         * 里增加元素，但只能添加Cat 自身及子类对象，假如放人块石头，贝lj 明显违背了
         * Animal 大类的性质。
         */


        //第四段：测试get方法
        //所有的super操作能够返回元素，但是泛型丢失，只能返回Object对象
        Object object = superCatFromCat.get(0);

        // 以下是extend操作返回元素
        Object catObject = extendCatFromCat.get(0);
        Cat cat = extendCatFromCat.get(0);
        //下行编译错误，虽然Cat集合从Garfield赋值而来，单类型擦除后，是不知道的。
        // Garfield garfield = extendCatFromGarfield.get(0);

        /**
         * 第4 段，所有List<? super T＞集合可以执行get 操作，虽然能够返回元素，但是
         * 类型丢失，即只能返回Object 对象。List<? extends Cat> 可以返回带类型的元素，但
         * 只能返回Cat 自身及其父类对象，因为子类类型被擦除了。
         */

        //? super Cat 下限是猫，赋值可以为Cat和Cat的父类
        List<? super Cat> superCatList = new ArrayList<Object>();
        superCatList = new ArrayList<Animal>();
        superCatList = new ArrayList<Cat>();
        produce(superCatList);

        //? extends Cat 上限是猫，赋值可以为Cat和Cat的子类，所以extendCatList只适用取值，存值的时候不知道具体是什么子类，所以编译器不允许往extend泛型集合增加内容
        List<? extends Cat> extendCatList = new ArrayList<Cat>();
        extendCatList = new ArrayList<Garfield>();
        extendCatList = new ArrayList<WhiteGarfield>();
        consumer(extendCatList);
    }

    public static void consumer(List<? extends Cat> extendCatList) {
        //上限是猫，赋值可以为Cat和Cat的子类，所以extendCatList只适用取值，存值的时候不知道具体是什么子类，所以编译器不允许往extend泛型集合增加内容
    }

    public static void produce(List<? super Cat> superCatList) {

    }

    public static class Animal {
    }

    public static class Cat extends Animal {
    }

    public static class Garfield extends Cat {
    }

    public static class WhiteGarfield extends Garfield {
    }
}
