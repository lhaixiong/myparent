import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import org.junit.Test;

public class MyTest {
    @Test
    public void test02(){
        String abc="from Oper";
        int idx=abc.toUpperCase().indexOf("FROM");
        System.out.println(idx);
        System.out.println(abc.substring(idx));
    }

    @Test
    public void test01(){
        TestBean bean=new TestBean(1,"lhx","jakldfjjdf");
        System.out.println(JSONObject.toJSONString(bean));
    }
    private static class TestBean{
        private int id;
        private String name;
        private String age;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        @JSONField(name = "n")
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        @JSONField(name = "a")
        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }

        public TestBean(int id, String name, String age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }
    }
}
