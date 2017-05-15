import com.lhxs3.controller.Branch;
import com.lhxs3.dao.DepartmentDao;
import com.lhxs3.dao.EmployeeDao;
import com.lhxs3.pojo.Employee;
import com.lhxs3.utils.EntityUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:springmvc.xml"})
public class STest {
    @Autowired
    private EmployeeDao employeeDao;
    @Test
    public void test03(){
        Employee e1=new Employee();
        e1.setEmail("aaaaaaaaaa");
        e1.setLastName("llllllllll");
        Employee e2=employeeDao.getEmployee(1001);
        System.out.println("before e1>>>" + e1);
        System.out.println("before e2>>>"+e2);

        EntityUtil.copyPropertiesIgnoreNull(e1,e2);

        System.out.println("after e1>>>"+e1);
        System.out.println("after e2>>>"+e2);

    }
    @Test
    public void test01(){
        Branch branch=Branch.valueOf("GD");
        int bb=5;
        System.out.println(branch.getClass().getName());
    }
    @Test
    public void test02(){
        DepartmentDao departmentDao=new DepartmentDao();
        System.out.println(departmentDao.getDepartment(1001).getDepartmentName());
    }
}
