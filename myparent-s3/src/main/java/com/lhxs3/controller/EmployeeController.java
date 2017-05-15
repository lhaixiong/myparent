package com.lhxs3.controller;

import com.lhxs3.dao.DepartmentDao;
import com.lhxs3.dao.EmployeeDao;
import com.lhxs3.pojo.Employee;
import com.lhxs3.utils.EntityUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeeController {
    private static final String SUCCESS="success";
    private static final String ERROR="error";
    @Autowired
    private EmployeeDao employeeDao;
    @Autowired
    private DepartmentDao departmentDao;
    @Autowired
    private ResourceBundleMessageSource messageSource;
//    @ExceptionHandler(value = {Exception.class})
//    public String handleException(Exception ex,Model model){
//        System.out.println("EmployeeController handleException出异常了>>>"+ex);
//        model.addAttribute("errorMsg",ex);
//        ex.printStackTrace();
//        return ERROR;
//    }
    @RequestMapping("/testExceptionResolver")
    public String testExceptionResolver(@RequestParam(value = "id") int id){
        System.out.println("testExceptionResolver result="+(10/id));
        return SUCCESS;
    }
    @RequestMapping("/testMultiFileResolver")
    public String testMultiFileResolver(@RequestParam(value = "description") String description,
                                        @RequestParam(value = "file")MultipartFile file) throws IOException {
        System.out.println("description>>>"+description);
        System.out.println("file getOriginalFilename>>> "+file.getOriginalFilename());
        System.out.println("file  getSize>>>"+file.getSize());
        System.out.println("file  getInputStream>>>"+file.getInputStream());
        return "success";
    }
    @RequestMapping("/i18n")
    public String testI18n(Locale locale){
        System.out.println("language:"+locale.getLanguage()+";country:"+locale.getCountry());
        System.out.println("username>>>"+messageSource.getMessage("i18n.username",null,locale));
        return "/i18n";
    }

    @ResponseBody
    @RequestMapping("/testJson")
    public Collection<Employee> testJson(){
        return employeeDao.getAll();
    }
    @RequestMapping("/testResponseEntity")
    public ResponseEntity<byte[]> testResponseEntity(HttpSession session){
        InputStream in=null;
        ResponseEntity<byte[]> responseEntity=null;
        try {
            ServletContext servletContext = session.getServletContext();
             in= servletContext.getResourceAsStream("/file/abc.txt");
            byte[] bytes=new byte[in.available()];
            in.read(bytes);

            HttpHeaders headers=new HttpHeaders();
            headers.add("Content-disposition","attachment;filename=abc.txt");

            HttpStatus status=HttpStatus.OK;
            responseEntity=new ResponseEntity<byte[]>(bytes,headers,status);
            return responseEntity;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return responseEntity;
    }

    @ResponseBody
    @RequestMapping("/testHttpMessageConverter")
    public String testHttpMessageConverter(@RequestBody String requestBody){
        System.out.println(requestBody);
        return "heehehehehheheheh"+new Date();
    }

    @RequestMapping("/testNormalMappingParam")
    public String testNormalMappingParam(Employee employee,Integer departmentId){
        if(departmentId!=null){
            employee.setDepartment(departmentDao.getDepartment(departmentId));
        }
        System.out.println("testNormalMappingParam employee>>"+employee);
        employeeDao.save(employee);
        return "redirect:/emp/list";
    }
    @RequestMapping("/testConverionServiceConvertor")
    public String testConverionServiceConvertor(@RequestParam("employee") Employee employee){
        System.out.println("testConverionServiceConvertor employee>>"+employee);
        employeeDao.save(employee);
        return "redirect:/emp/list";
    }
//    @InitBinder
//    public void initBinder(WebDataBinder webDataBinder){
//        System.out.println("initBinder>>>>");
//        webDataBinder.setDisallowedFields("lastName");
//    }

//    @ModelAttribute
//    public void getEmployee(Map<String,Object> map,@RequestParam(value = "id",required = false) Integer id){
//        System.out.println("ModelAttribute getEmployee");
//        if(id!=null){
//            map.put("employee",employeeDao.getEmployee(id));
//        }
//    }
//    @RequestMapping(value = "/save",method = RequestMethod.PUT)
//    public String updateWithModelAttribute(Employee employee){
//        System.out.println("updateWithModelAttribute>>>"+employee);
//        employeeDao.save(employee);
//        return "redirect:/emp/list";
//    }

    @RequestMapping(value = "/save",method = RequestMethod.PUT)
    public String update(Employee employee){
        System.out.println("update>>>>>");
        if(employee.getId()!=null){
            Employee dest=employeeDao.getEmployee(employee.getId());
            try {
//                BeanUtils.copyProperties(employee,dest);//非此方法,dest将employee完全覆盖
                System.out.println("before employee>>"+employee);
                System.out.println("before dest>>"+dest);
                EntityUtil.copyPropertiesIgnoreNull(employee,dest);
                System.out.println("after employee>>" + employee);
                System.out.println("after dest>>"+dest);
                employeeDao.save(dest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return "redirect:/emp/list";
    }
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public String delete(@PathVariable("id") Integer id){
        System.out.println("delete>>>>");
        employeeDao.delete(id);
        return "redirect:/emp/list";
    }
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Employee employee){
        System.out.println("add save>>>>>");
        System.out.println(employee);
        employeeDao.save(employee);
        return "redirect:/emp/list";
    }
    @RequestMapping(value = "/content")
    public String content(@RequestParam(value = "id",required = false) Integer id,Map<String,Object> map){
        System.out.println("content>>>>");
        if(id==null){//新增
            map.put("departments",departmentDao.getDepartments());
            map.put("employee",new Employee());
        }else {//修改
            map.put("departments",departmentDao.getDepartments());
            map.put("employee",employeeDao.getEmployee(id));
        }
        return "content";
    }
    @RequestMapping(value = "/list")
    public String list(Map<String,Object> map){
        System.out.println("list>>>>");
        map.put("emps",employeeDao.getAll());
        return "list";
    }
}
