package com.lhx.jdk8;

import com.lhx.jdk8.sundry.*;
import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 一、Lambda 表达式的基础语法：Java8中引入了一个新的操作符 "->" 该操作符称为箭头操作符或 Lambda 操作符
 * 						    箭头操作符将 Lambda 表达式拆分成两部分：
 *
 * 左侧：Lambda 表达式的参数列表
 * 右侧：Lambda 表达式中所需执行的功能， 即 Lambda 体
 *
 * 语法格式一：无参数，无返回值
 * 		() -> System.out.println("Hello Lambda!");
 *
 * 语法格式二：有一个参数，并且无返回值
 * 		(x) -> System.out.println(x)
 *
 * 语法格式三：若只有一个参数，小括号可以省略不写
 * 		x -> System.out.println(x)
 *
 * 语法格式四：有两个以上的参数，有返回值，并且 Lambda 体中有多条语句
 *		Comparator<Integer> com = (x, y) -> {
 *			System.out.println("函数式接口");
 *			return Integer.compare(x, y);
 *		};
 *
 * 语法格式五：若 Lambda 体中只有一条语句， return 和 大括号都可以省略不写
 * 		Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
 *
 * 语法格式六：Lambda 表达式的参数列表的数据类型可以省略不写，因为JVM编译器通过上下文推断出，数据类型，即“类型推断”
 * 		(Integer x, Integer y) -> Integer.compare(x, y);
 *
 * 上联：左右遇一括号省
 * 下联：左侧推断类型省
 * 横批：能省则省
 *  ------------------------------------
 * 二、Lambda 表达式需要“函数式接口”的支持
 * 函数式接口：接口中只有一个抽象方法的接口，称为函数式接口。 可以使用注解 @FunctionalInterface 修饰
 * 			 可以检查是否是函数式接口
 *
 * Java8 内置的四大核心函数式接口
 *
 * Consumer<T> : 消费型接口
 * 		void accept(T t);
 *
 * Supplier<T> : 供给型接口
 * 		T get();
 *
 * Function<T, R> : 函数型接口
 * 		R apply(T t);
 *
 * Predicate<T> : 断言型接口
 * 		boolean test(T t);
 * -----------------------------
 * 	* 一、方法引用：若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用
 * 			  （可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * 			  注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 *
 * 1. 对象的引用 :: 实例方法名
 *
 * 2. 类名 :: 静态方法名
 *
 * 3. 类名 :: 实例方法名
 *
 *
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 *
 * 1. 类名 :: new
 *
 * 三、数组引用
 *
 * 	类型[] :: new;
 *
 * 一、Stream API 的操作步骤：
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 *  2.1筛选与切片:
  filter(Predicatep)
  接收Lambda ，从流中排除某些元素。
  distinct()
  筛选，通过流所生成元素的hashCode() 和equals() 去除重复元素
  limit(long maxSize)
  截断流，使其元素不超过给定数量。
  skip(long n)
  跳过元素，返回一个扔掉了前n 个元素的流。若流中元素不足n 个，则返回一个空流。与limit(n) 互补

   2.2 映射
 map(Functionf)
 接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
 mapToDouble(ToDoubleFunction f)
 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的DoubleStream。
 mapToInt(ToIntFunction f)
 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的IntStream。
 mapToLong(ToLongFunction f)
 接收一个函数作为参数，该函数会被应用到每个元素上，产生一个新的LongStream。
 flatMap(Function f)
 接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流

 2.3排序
 sorted()
 产生一个新流，其中按自然顺序排序
 sorted(Comparatorcomp)
 产生一个新流，其中按比较器顺序排序
 *
 * 3. 终止操作(终端操作)
 *终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是void
 *
 * 3.1查找与匹配
 * allMatch(Predicate p)
 检查是否匹配所有元素
 anyMatch(Predicate p)
 检查是否至少匹配一个元素
 noneMatch(Predicatep)
 检查是否没有匹配所有元素
 findFirst()
 返回第一个元素
 终端操作会从流的流水线生成结果。其结果可以是任何不是流的值，例如：List、Integer，甚至是void 。
 findAny()
 返回当前流中的任意元素
 count()
 返回流中元素总数
 max(Comparatorc)
 返回流中最大值
 min(Comparatorc)
 返回流中最小值
 reduce(T iden, BinaryOperator b)
 可以将流中元素反复结合起来，得到一个值。
 forEach(Consumerc)
 内部迭代(使用Collection 接口需要用户去做迭代，称为外部迭代。相反，Stream API 使用内部迭代——它帮你把迭代做了)

 3.2归约
 reduce(BinaryOperator b)
 可以将流中元素反复结合起来，得到一个值。
 返回Optional<T>
 备注：map 和reduce 的连接通常称为map-reduce 模式，因Google 用它来进行网络搜索而出名。

 3.3收集
 collect(Collector c)
 将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
 Collector 接口中方法的实现决定了如何对流执行收集操作(如收集到List、Set、Map)。
 但是Collectors 实用类提供了很多静态方法，可以方便地创建常见收集器实例，具体方法与实例如下表：

 toList
 List<T>
 把流中元素收集到List
 List<Employee>emps=list.stream().collect(Collectors.toList());
 toSet
 Set<T>
 把流中元素收集到Set
 Set<Employee>emps=list.stream().collect(Collectors.toSet());
 toCollection
 Collection<T>
 把流中元素收集到创建的集合
 Collection<Employee>emps=list.stream().collect(Collectors.toCollection(ArrayList::new));
 counting
 Long
 计算流中元素的个数
 longcount=list.stream().collect(Collectors.counting());
 summingInt
 Integer
 对流中元素的整数属性求和
 inttotal=list.stream().collect(Collectors.summingInt(Employee::getSalary));
 averagingInt
 Double
 计算流中元素Integer属性的平均值
 doubleavg=list.stream().collect(Collectors.averagingInt(Employee::getSalary));
 summarizingInt
 IntSummaryStatistics
 收集流中Integer属性的统计值。如：平均值
 IntSummaryStatisticsiss=list.stream().collect(Collectors.summarizingInt(Employee::getSalary));
 joining
 String
 连接流中每个字符串
 Stringstr=list.stream().map(Employee::getName).collect(Collectors.joining());
 maxBy
 Optional<T>
 根据比较器选择最大值
 Optional<Emp>max=list.stream().collect(Collectors.maxBy(comparingInt(Employee::getSalary)));
 minBy
 Optional<T>
 根据比较器选择最小值
 Optional<Emp>min=list.stream().collect(Collectors.minBy(comparingInt(Employee::getSalary)));
 reducing
 归约产生的类型
 从一个作为累加器的初始值开始，利用BinaryOperator与流中元素逐个结合，从而归约成单个值
 inttotal=list.stream().collect(Collectors.reducing(0,Employee::getSalar,Integer::sum));
 collectingAndThen
 转换函数返回的类型
 包裹另一个收集器，对其结果转换函数
 inthow=list.stream().collect(Collectors.collectingAndThen(Collectors.toList(),List::size));
 groupingBy
 Map<K,List<T>>
 根据某属性值对流分组，属性为K，结果为V
 Map<Emp.Status, List<Emp>> map= list.stream()
 .collect(Collectors.groupingBy(Employee::getStatus));
 partitioningBy
 Map<Boolean,List<T>>
 根据true或false进行分区
 Map<Boolean,List<Emp>>vd=list.stream().collect(Collectors.partitioningBy(Employee::getManage));


 三、并行流与串行流
 并行流就是把一个内容分成多个数据块，并用不同的线程分别处理每个数据块的流。
 Java 8 中将并行进行了优化，我们可以很容易的对数据进行并行操作。Stream API 可以声明性地通过parallel() 与sequential() 在并行流与顺序流之间进行切换。

四： Optional 类
 Optional<T> 类(java.util.Optional) 是一个容器类，代表一个值存在或不存在，原来用null 表示一个值不存在，现在Optional 可以更好的表达这个概念。并且可以避免空指针异常。
 常用方法：
 Optional.of(T t) : 创建一个Optional 实例
 Optional.empty() : 创建一个空的Optional 实例
 Optional.ofNullable(T t):若t 不为null,创建Optional 实例,否则创建空实例
 isPresent() : 判断是否包含值
 orElse(T t) : 如果调用对象包含值，返回该值，否则返回t
 orElseGet(Supplier s) :如果调用对象包含值，返回该值，否则返回s 获取的值
 map(Function f): 如果有值对其处理，并返回处理后的Optional，否则返回Optional.empty()
 flatMap(Function mapper):与map 类似，要求返回值必须是Optional

 五：接口中的默认方法与静态方法
 5.1接口中的默认方法
 Java 8中允许接口中包含具有具体实现的方法，该方法称为“默认方法”，默认方法使用default关键字修饰。
 例如：
 public interface MyInterface {//接口中的默认方法

 default String getName(){
 return "呵呵呵";
 }

 public static void show(){//接口中的静态方法
 System.out.println("接口中的静态方法");
 }

 }

 5.2接口默认方法的”类优先”原则
 若一个接口中定义了一个默认方法，而另外一个父类或接口中又定义了一个同名的方法时
 选择父类中的方法。如果一个父类提供了具体的实现，那么接口中具有相同名称和参数的默认方法会被忽略。
 接口冲突。如果一个父接口提供一个默认方法，而另一个接口也提供了一个具有相同名称和参数列表的方法（不管方法是否是默认方法），那么必须覆盖该方法来解决冲突
 public class SubClass extends MyClass implements MyFun, MyInterface{

@Override
public String getName() {
return MyInterface.super.getName();
}

 六:日期新API
6.1 Instant 时间戳
用于“时间戳”的运算。它是以Unix元年(传统的设定为UTC时区1970年1月1日午夜时分)开始所经历的描述进行运算

6.2 Duration 和Period
Duration:用于计算两个“时间”间隔
Period:用于计算两个“日期”间隔

6.3 日期的操纵
TemporalAdjuster : 时间校正器。有时我们可能需要获取例如：将日期调整到“下个周日”等操作。
TemporalAdjusters : 该类通过静态方法提供了大量的常用TemporalAdjuster 的实现。
例如获取下个周日：

 6.4 解析与格式化
java.time.format.DateTimeFormatter 类：该类提供了三种格式化方法：
预定义的标准格式
语言环境相关的格式
自定义的格式

 6.5时区的处理
Java8 中加入了对时区的支持，带时区的时间为分别为：
ZonedDate、ZonedTime、ZonedDateTime
其中每个时区都对应着ID，地区ID都为“{区域}/{城市}”的格式
例如：Asia/Shanghai 等
ZoneId：该类中包含了所有的时区信息
getAvailableZoneIds() : 可以获取所有时区时区信息
of(id) : 用指定的时区信息获取ZoneId 对象

 6.6与传统日期处理的转换
java.time.Instant  java.util.Date
Date.from(instant)
date.toInstant()
java.time.Instant  java.sql.Timestamp
Timestamp.from(instant)
timestamp.toInstant()
java.time.ZonedDateTime  java.util.GregorianCalendar
GregorianCalendar.from(zonedDateTime)
cal.toZonedDateTime()
java.time.LocalDate  java.sql.Time
Date.valueOf(localDate)
date.toLocalDate()
java.time.LocalTime  java.sql.Time
Date.valueOf(localDate)
date.toLocalTime()
java.time.LocalDateTime  java.sql.Timestamp
Timestamp.valueOf(localDateTime)
timestamp.toLocalDateTime()
java.time.ZoneId  java.util.TimeZone
Timezone.getTimeZone(id)
timeZone.toZoneId()
java.time.format.DateTimeFormatter  java.text.DateFormat
formatter.toFormat()

七：重复注解与类型注解
Java 8对注解处理提供了两点改进：可重复的注解及可用于类型的注解。
 */


public class Jdk8Test {
    List<Employee> employeeList= Arrays.asList(
            new Employee("aa",23,2999.99, Employee.Status.BUSY),
            new Employee("dd",53,5999.99, Employee.Status.FREE),
            new Employee("bb",33,3999.99, Employee.Status.FREE),
            new Employee("cc",43,4999.99, Employee.Status.BUSY),
            new Employee("ee",63,6999.99, Employee.Status.BUSY),
            new Employee("ee",63, 6999.99, Employee.Status.VOCATION),
            new Employee("ee",63,6999.99, Employee.Status.BUSY)
    );
    @Test
    public void testOptional(){
//        Optional<Employee> op = Optional.of(new Employee("张三", 18, 9999.99d));
//
//        Optional<String> op2 = op.map(Employee::getName);
//        System.out.println(op2.get());
//
//        Optional<String> op3 = op.flatMap((e) -> Optional.of(e.getName()));
//        System.out.println(op3.get());
        ///////////////
//        Optional<Employee> op = Optional.ofNullable(null);
//
//        if(op.isPresent()){
//            System.out.println(op.get());
//        }
//
//        Employee emp = op.orElse(new Employee("张三11", 18, 9999.99d));
//        System.out.println(emp);
//
//        Employee emp2 = op.orElseGet(() -> new Employee("张三133", 18, 9999.99d));
//        System.out.println(emp2);
        ///////////////
//        Optional<Employee> op = Optional.ofNullable(null);
//		System.out.println(op.get());

//		Optional<Employee> op2 = Optional.empty();
//		System.out.println(op2.get());
        ///////////////
//        Optional<Employee> op = Optional.of(new Employee());
//        Employee emp = op.get();
//        System.out.println(emp);
        ///////////////
//        Man man = new Man();
//        String name = getGodnessName(man);
//        System.out.println(name);
        ///////////////
        //运用 Optional 的实体类
//        Optional<Godness> godness = Optional.ofNullable(new Godness("林志玲"));
//        Optional<NewMan> op = Optional.ofNullable(new NewMan(godness));
        Optional<NewMan> op = Optional.ofNullable(null);
        String name = getGodnessName2(op);
        System.out.println(name);
        ///////////////
        ///////////////
    }
    public String getGodnessName2(Optional<NewMan> man){
        return man.orElse(new NewMan())
                .getGodness()
                .orElse(new Godness("苍老师"))
                .getName();
    }
    //需求：获取一个男人心中女神的名字
    public String getGodnessName(Man man){
        if(man != null){
            Godness g = man.getGod();

            if(g != null){
                return g.getName();
            }
        }

        return "苍老师";
    }

    @Test
    public void testParallel(){
//        Instant start = Instant.now();
//        long sum = LongStream.rangeClosed(0, 1000000000L)
////                .parallel()//没有为串行流
//                .reduce(0,Long::sum);
//        Instant end = Instant.now();
//        System.out.println("sum："+ sum);
//        System.out.println("耗时："+ Duration.between(start,end).toMillis());//1903


        Instant start = Instant.now();
//        long sum = LongStream.range(0, 1000000L) //左闭右开区间[0,1000000)
        long sum = LongStream.rangeClosed(0, 1000000L) //闭区间[0,1000000]
                .parallel()//并行流
                .reduce(0,Long::sum);
        Instant end = Instant.now();
        System.out.println("sum："+ sum);
        System.out.println("耗时："+ Duration.between(start,end).toMillis());//450
    }
    @Test
    public void testCollector(){
        List<String> list = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());
        list.forEach(System.out::println);

        System.out.println("------set--------");
        Set<String> set = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        set.forEach(System.out::println);

        System.out.println("------hashset--------");
        set = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        set.forEach(System.out::println);


        System.out.println("-------count------");
        Long count = employeeList.stream()
                .collect(Collectors.counting());
        System.out.println("count:"+count);

        System.out.println("---------avg-----");
        Double avg = employeeList.stream()
                .collect(Collectors.averagingDouble(Employee::getSalory));
        System.out.println("avg:"+avg);

        System.out.println("--------sum DoubleSummaryStatistics-----");
        DoubleSummaryStatistics sum = employeeList.stream()
                .collect(Collectors.summarizingDouble(Employee::getSalory));
        System.out.println("sum:"+sum);

        System.out.println("----------max-----");
        Optional<Employee> max = employeeList.stream()
                .collect(Collectors.maxBy((e1, e2) -> Double.compare(e1.getSalory(), e2.getSalory())));
        System.out.println("max:"+max);

        System.out.println("------min------");
        Optional<Employee> min = employeeList.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalory(), e2.getSalory())));
        System.out.println("min:"+min);

        System.out.println("------group-----");
        Map<Employee.Status, List<Employee>> group = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));
        System.out.println("group:"+group);

        System.out.println("-----multiGroup-----");
        Map<Employee.Status, Map<String, List<Employee>>> multiGroup = employeeList.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() <= 35) {
                        return "青年";
                    } else if (e.getAge() <= 50) {
                        return "中年";
                    } else {
                        return "老年";
                    }
                })));
        System.out.println("multiGroup:"+multiGroup);

        System.out.println("-----partition-----");
        Map<Boolean, List<Employee>> partition = employeeList.stream()
                .collect(Collectors.partitioningBy(e -> e.getSalory() > 5000d));
        System.out.println("partition:"+partition);

        System.out.println("------join------");
        String names = employeeList.stream()
                .map(Employee::getName)
                .collect(Collectors.joining("|"));
        System.out.println("join:"+names);
    }
    @Test
    public void testReduce(){
        List<Integer> list=Arrays.asList(1,2,3,4,5);
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println("sum:"+sum);
        System.out.println("-----map-reduce----");
        Optional<Double> salory = employeeList.stream()
                .map(x -> x.getSalory())
                .reduce(Double::sum);
        System.out.println("salory:"+salory.get());

    }
    @Test
    public void testMatch(){
        System.out.println("----------allMatch-------");
        boolean b = employeeList.stream()
                .allMatch((e) -> e.getStatus().equals(Employee.Status.BUSY));
        System.out.println(b);

        System.out.println("-----anyMatch----");
        System.out.println(employeeList.stream()
                .anyMatch((e) -> e.getStatus().equals(Employee.Status.FREE)));

        System.out.println("-----noneMatch----");
        boolean b3 = employeeList.stream()
                .noneMatch((e) -> e.getStatus().equals(Employee.Status.VOCATION));
        System.out.println(b3);

        System.out.println("-----findFirst----");
        Optional<Employee> opt = employeeList.stream()
                .sorted((e1, e2) -> -Double.compare(e1.getSalory(), e2.getSalory()))
                .findFirst();
        opt.orElse(new Employee());
        System.out.println(opt.get());

        System.out.println("-----findAny----");
        Optional<Employee> opt2 = employeeList.stream()
                .filter((e)->e.getStatus().equals(Employee.Status.FREE))
                .findAny();
        opt2.orElse(new Employee());
        System.out.println(opt2.get());

        System.out.println("-----count----");
        long count = employeeList.stream().count();
        System.out.println(count);

        System.out.println("-----Max obj----");
        Optional<Employee> max = employeeList.stream()
                .max((e1, e2) -> -Double.compare(e1.getSalory(), e2.getSalory()));
        System.out.println(max.get());

        System.out.println("-----min field----");
        Optional<Double> min = employeeList.stream()
                .map((e) -> e.getSalory())
                .min(Double::compare);
        System.out.println(min.get());
    }
    @Test
    public void testSort(){
        List<String> list = Arrays.asList("ccc", "bbb", "ggg", "aaa", "eee");
        list.stream()
                .sorted()
                .forEach(System.out::println);
        System.out.println("-----------");
        employeeList.stream()
                .sorted((e1,e2)->{
                    int age=e1.getAge()-e2.getAge();
                    if(age==0){
                        return e1.getName().compareTo(e2.getName());
                    }else {
                        return age;
                    }
                }).forEach(System.out::println);
    }
    @Test
    public void testAdd(){
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        List list1=new ArrayList<>();
        list1.add(11);
        list1.add(22);
        list1.add(list);
        System.out.println("add....."+list1);//add.....[11, 22, [aaa, bbb, ccc, ddd, eee]]

        list1.clear();
        list1.add(11);
        list1.add(22);
        list1.addAll(list);
        System.out.println("addAll....."+list1);//addAll.....[11, 22, aaa, bbb, ccc, ddd, eee]
    }
    @Test
    public void testMap(){
        List<String> list = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        list.stream()
                .map((s)->s.toUpperCase())
                .forEach((e)-> System.out.println(e));
        System.out.println("-----------");
        employeeList.stream()
                .map((e)->e.getSalory())
                .forEach(System.out::println);

        System.out.println("-------------");
//        flatMap(Function f)
//        接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
        Stream<Stream<Character>> ss = list.stream()
                .map(Jdk8Test::filterCharacter);//{'a','a','a'},{'b','b','b'}
        ss.forEach((s)->{
            s.forEach(System.out::println);
        });
        System.out.println("-----flatMap--------");
        Stream<Character> s = list.stream()
                .flatMap(Jdk8Test::filterCharacter);//{'a','a','a','b','b','b'}
        s.forEach(System.out::println);
    }
    public static Stream<Character> filterCharacter(String str){
        List<Character> list=new ArrayList<>();
        char[] chars = str.toCharArray();
        for (char c : chars) {
            list.add(c);
        }
        return list.stream();
    }
    @Test
    public void testDistinct(){
        Collections.sort(employeeList, (e1, e2) ->e1.getAge()-e2.getAge());
        employeeList.stream().forEach(System.out::println);
        System.out.println("------------");
        employeeList.stream()
                .filter((e)->e.getSalory()>3000)
//                .limit(3)
//                .skip(1)
                .distinct()
                .forEach(System.out::println);
    }
    @Test
    public void testLimit(){
        Collections.sort(employeeList,(e1,e2)->e1.getAge()-e2.getAge());
        employeeList.stream().forEach(System.out::println);
        System.out.println("------------");
        employeeList.stream()
                .filter((e)->e.getSalory()>3000)
                .limit(3)
                .skip(1)
                .forEach(System.out::println);
    }
    @Test
    public void testFilter(){
        Stream<Employee> stream = employeeList.stream()
                .filter((e) -> e.getAge() > 40);
        stream.forEach(System.out::println);
    }
    @Test
    public void testStreamCreate(){
        //1. Collection 提供了两个方法  stream() 与 parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1,2,3,4,5,6);

        //4. 创建无限流
        //迭代
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);

    }
    @Test
    public void testArrayReferrence(){
        Function<Integer,String[]> function=(x)->new String[x];
        System.out.println(function.apply(10).length);

        Function<Integer,String[]> function2=String[]::new;
        System.out.println(function2.apply(20).length);
    }
    @Test
    public void testConstructReferrence(){
        Supplier<Employee> supplier=()->new Employee();
        //构造器引用
        Supplier<Employee> supplier1=Employee::new;

        //带一个参数的构造器引用
        Function<Integer,Employee> function=(x)->new Employee(x);
        System.out.println(function.apply(100));

        //带一个参数的构造器引用
        BiFunction<Integer,Double,Employee> function2=Employee::new;
        System.out.println(function2.apply(33,22.44));

    }
    @Test
    public void testMethodReferrence(){
        Consumer<String> consumer=(x)->System.out.println(x);
        Consumer<String> consumer2=System.out::println;
        consumer.accept("(x)->System.out.println(x)");
        consumer2.accept("System.out::println");

        //对象的引用 :: 实例方法名
        Employee employee=new Employee("aaa",44,44.4);
        Supplier<Integer> supplier=()->employee.getAge();
        Supplier<String> supplier2=employee::getName;
        System.out.println(supplier.get()+">>>>>>>>>>>>>>"+supplier2.get());
        //类名 :: 静态方法名
        Comparator<Integer> comparator=(x,y)->Integer.compare(x,y);
        Comparator<Integer> comparator2=Integer::compare;

        //类名 :: 实例方法名(少用，容易混乱)
        //若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
        BiPredicate<String,String> bp=(x,y)->x.equals(y);
        BiPredicate<String,String> bp2=String::equals;

    }
    @Test
    public void test05(){
        Collections.sort(employeeList,(e1,e2)->{
            if(e1.getAge()==e2.getAge()){
                return e1.getName().compareTo(e2.getName());
            }else {
                return e1.getAge()-e2.getAge();
            }
        });
        for (Employee employee : employeeList) {
            System.out.println(employee);
        }
    }
    @Test
    public void test04(){
        System.out.println("mult>>>"+operate(5,(x)->{return x*x;}));
        System.out.println("add>>>"+operate(5,(x)->{return x+x;}));
    }
    public Integer operate(Integer num,MyPredicatea<Integer> mp){
        System.out.println("operate num="+num);
        return (Integer) mp.test(num);
    }
    /**
     * 测试lamda表达式语法
     */
    @Test
    public void test03(){
        //老方式
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                System.out.println("hello,world");
            }
        };
        runnable.run();

        System.out.println("-------------------");
        //1.无参无返回值
        Runnable runnable2=()-> System.out.println("hello,world lamda");
        runnable2.run();
        System.out.println("-------------------");

        //2.有参无返回值
        Consumer<String> consumer=(x)-> System.out.println(x);
        consumer.accept("梁海雄好靓仔");

        //3.多个参数，多条语句，有返回值
        System.out.println("-------------------");
        Comparator<Integer> comparator=(x,y)->{
            System.out.println("多个参数，多条语句，有返回值");
            return Integer.compare(x,y);
        };
        int result = comparator.compare(3, 5);
        System.out.println(result);
        System.out.println("-------------------");

        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
        System.out.println("-------------------");
    }


    //要求筛选员工
    @Test
    public void test02(){
        //老方式
        List<Employee> list=filterEmployees(employeeList,new FilterEmpsByAge());
        System.out.println("满足年龄大于35岁的员工");
        for (Employee employee : list) {
            System.out.println(employee);
        }
        list=filterEmployees(employeeList,new FilterEmpsBySalory());
        System.out.println("满足工资大于4000的员工");
        for (Employee employee : list) {
            System.out.println(employee);
        }
        System.out.println("--满足年龄大于35岁的员工-----------使用lamda");
        //新方式
        list=filterEmployees(employeeList,(e)->e.getAge()>35);
        list.forEach(System.out::println);
        System.out.println("-------满足工资大于4000的员工------使用lamda");
        list=filterEmployees(employeeList,(e)->e.getSalory()>4000);
        list.forEach(System.out::println);

        //更加优化
        System.out.println("--满足年龄大于35岁的员工-----------使用Stream和lamda");
        employeeList.stream()
                .filter((e) -> e.getAge() > 35)
                //.limit(2)//从帅选的结果集中再取前几条
                .forEach(System.out::println);
        employeeList.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

    }

    public List<Employee> filterEmployees(List<Employee> list,MyPredicate<Employee> mp){
        List<Employee> retList=new ArrayList<>();
        for (Employee employee : list) {
            if(mp.test(employee)){
                retList.add(employee);
            }
        }
        return retList;
    }

    @Test
    public void test01(){
        //原来的匿名内部类
        Comparator<Integer> comparator=new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(01,02);
            }
        };
        TreeSet<Integer> set=new TreeSet<Integer>(comparator);

        //lamda表达式
        Comparator<Integer> comparator2=(x,y)->Integer.compare(x,y);
        TreeSet<Integer> set2=new TreeSet<>(comparator);
    }
    //注意：Optional 不能被序列化
    public static class NewMan {

        private Optional<Godness> godness = Optional.empty();

        private Godness god;

        public Optional<Godness> getGod(){
            return Optional.of(god);
        }

        public NewMan() {
        }

        public NewMan(Optional<Godness> godness) {
            this.godness = godness;
        }

        public Optional<Godness> getGodness() {
            return godness;
        }

        public void setGodness(Optional<Godness> godness) {
            this.godness = godness;
        }

        @Override
        public String toString() {
            return "NewMan [godness=" + godness + "]";
        }

    }
    public static class Godness{
        private String name;

        public Godness() {
        }

        public Godness(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Godness [name=" + name + "]";
        }
    }
    public static class Man{
        private Godness god;

        public Man() {
        }

        public Man(Godness god) {
            this.god = god;
        }

        public Godness getGod() {
            return god;
        }

        public void setGod(Godness god) {
            this.god = god;
        }

        @Override
        public String toString() {
            return "Man [god=" + god + "]";
        }
    }
}
