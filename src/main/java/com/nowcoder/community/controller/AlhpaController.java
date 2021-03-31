package com.nowcoder.community.controller;

import com.nowcoder.community.util.CommunityUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlhpaController {

    //“RequestMapping是一个用来处理请求地址映射的注解,可用于类或方法上。
    // 用于类上,表示类中的所有响应请求的方法都是以该地址作为父路径。
    @RequestMapping("/hello")
    // ResponseBody是项目中最常用的接收参数的注解
    //将 HTTP 请求正文插入方法中，使用适合的 HttpMessageConverter 将请求体写入某个对象；
    @ResponseBody
    public String sayHello(){
        return "Hello Spring Boot.";
    }

    // 处理请求、处理响应（从底层请求对象，响应对象）
    @RequestMapping("/http")
    // 通过response对象可以直接获取数据，所以http方法不再需要返回值
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        // 可不可以理解成浏览器向服务器发出的请求内容
        System.out.println(request.getMethod()); //请求方式
        System.out.println(request.getServletPath()); //请求路径
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }
        System.out.println(request.getParameter("code")); //GET方式传参"code"的值

        // 返回响应数据
        // 应该就是server向浏览器返回的具体网页html内容
        response.setContentType("text/html;charset=utf-8");
        try (
                PrintWriter writer = response.getWriter();
        ) {
            writer.write("<h1>牛客网</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /////////////////////////////
    /// GET请求，有两种传参方式
    /////////////////////////////

    // 一种是用问号拼参数
    // 分页显示，多参数——students?current=1&limit=20
    @RequestMapping(path = "/students", method = RequestMethod.GET) //强制该方法只有GET请求才能访问
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current", required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit", required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // 一种是把参数拼在路径中
    // 查询某个id的学生，单参数
    // /student/123
    @RequestMapping(path = "/student/{id}", method = RequestMethod.GET)
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {  //@PathVariable 路径参数
        System.out.println(id);
        return "a student";
    }

    /////////////////////////////
    /// POST请求，浏览器向服务器提交数据
    /////////////////////////////
    @RequestMapping(path = "/student", method = RequestMethod.POST)
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    /////////////////////////////
    // 响应返回HTML数据，而不是上面用注解ResponseBody简单返回一个字符串
    /////////////////////////////
        @RequestMapping(path = "/teacher", method = RequestMethod.GET)
        public ModelAndView getTeacher() {
            ModelAndView mav = new ModelAndView();
            mav.addObject("name", "张三");
            mav.addObject("age", 30);
            //设置模板的路径名字
            mav.setViewName("/demo/view");
            return mav;
    }

    @RequestMapping(path = "/school", method = RequestMethod.GET)
    public String getSchool(Model model) {
        model.addAttribute("name", "北京大学");
        model.addAttribute("age", 80);
        return "/demo/view";  //String类型的返回值代表：返回view的路径
    }

    /////////////////////////////
    // 响应JSON数据(异步请求)
    // Java对象 -> JSON字符串 -> JS对象
    /////////////////////////////
    @RequestMapping(path = "/emp", method = RequestMethod.GET)
    @ResponseBody //不加这个的话就默认返回html，加了才能返回其他类型包括json字符串
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        return emp;
    }

    // 返回多条JSON数据的操作
    @RequestMapping(path = "/emps", method = RequestMethod.GET)
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "张三");
        emp.put("age", 23);
        emp.put("salary", 8000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "李四");
        emp.put("age", 24);
        emp.put("salary", 9000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王五");
        emp.put("age", 25);
        emp.put("salary", 10000.00);
        list.add(emp);

        return list;
    }

    // cookie示例

    @RequestMapping(path = "/cookie/set", method = RequestMethod.GET)
    @ResponseBody
    public String setCookie(HttpServletResponse response) {
        // 创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie生效的范围
        cookie.setPath("/community/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(60 * 10);
        // 发送cookie
        response.addCookie(cookie);

        return "set cookie";
    }
    @RequestMapping(path = "/cookie/get", method = RequestMethod.GET)
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {  //CookieValue注解可以从所有Cookie中取得相应key的value
        System.out.println(code);
        return "get cookie";
    }

    // session示例

    @RequestMapping(path = "/session/set", method = RequestMethod.GET)
    @ResponseBody
    public String setSession(HttpSession session) {  //HttpSession只要声明了SpringMVC就会自动注入进来，不用像Cookie那样实例化
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @RequestMapping(path = "/session/get", method = RequestMethod.GET)
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    // ajax示例
    @RequestMapping(path = "/ajax", method = RequestMethod.POST)
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
    }

}
