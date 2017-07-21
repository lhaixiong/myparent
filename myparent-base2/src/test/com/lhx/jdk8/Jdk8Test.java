package com.lhx.jdk8;

import com.lhx.jdk8.sundry.*;
import org.junit.Test;

import java.util.*;
import java.util.function.*;
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
 */
public class Jdk8Test {
    List<Employee> employeeList= Arrays.asList(
            new Employee("aa",23,2999.99, Employee.Status.BUSY),
            new Employee("dd",53,5999.99, Employee.Status.FREE),
            new Employee("bb",33,3999.99, Employee.Status.FREE),
            new Employee("cc",43,4999.99, Employee.Status.BUSY),
            new Employee("ee",63,6999.99, Employee.Status.BUSY),
            new Employee("ee", 63, 6999.99, Employee.Status.VOCATION),
            new Employee("ee",63,6999.99, Employee.Status.BUSY)
    );
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
}
