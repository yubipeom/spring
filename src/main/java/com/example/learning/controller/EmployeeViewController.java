package com.example.learning.controller;

import com.example.learning.entity.Employee;
import com.example.learning.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 员工视图控制器
 * 这个类负责处理所有与员工页面相关的请求
 * 比如显示员工列表、搜索员工、同步数据等
 */
@Controller  // 告诉Spring这是一个控制器类
@RequestMapping("/employees")  // 所有请求都以/employees开头
public class EmployeeViewController {

    @Autowired  // 自动注入员工服务，这样我们就可以使用它的功能
    private EmployeeService employeeService;

    /**
     * 显示员工列表页面
     * 这个方法是当用户访问 /employees 时会被调用
     * 
     * @param pageNum 当前页码，默认是第1页
     * @param pageSize 每页显示多少条数据，默认是10条
     * @param model 用于向页面传递数据的对象
     * @return 返回要显示的页面名称
     */
    @GetMapping  // 处理GET请求，当访问/employees时调用此方法
    public String listEmployees(
            @RequestParam(defaultValue = "1") int pageNum,  // 如果没有指定页码，默认是第1页
            @RequestParam(defaultValue = "10") int pageSize,  // 如果没有指定每页数量，默认是10条
            Model model) {  // model用于向页面传递数据
        
        // 创建一个空的查询条件对象
        Employee condition = new Employee();
        
        // 获取当前页的员工数据
        List<Employee> employees = employeeService.getEmployeesByPage(condition, pageNum, pageSize);
        
        // 获取总记录数
        long totalCount = employeeService.getTotalCount(condition);
        
        // 计算总页数（向上取整）
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        // 将数据添加到model中，这样页面就可以使用这些数据了
        model.addAttribute("employees", employees);  // 员工列表
        model.addAttribute("pageNum", pageNum);  // 当前页码
        model.addAttribute("pageSize", pageSize);  // 每页显示数量
        model.addAttribute("totalCount", totalCount);  // 总记录数
        model.addAttribute("totalPages", totalPages);  // 总页数
        
        return "employee/list";  // 返回要显示的页面名称
    }

    /**
     * 搜索员工
     * 这个方法是当用户点击搜索按钮时会被调用
     * 可以根据姓氏、级别和在职状态来搜索员工
     * 
     * @param lastName 姓氏，可以为空
     * @param level 级别，可以为空
     * @param isActive 是否在职，可以为空
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @param model 用于向页面传递数据的对象
     * @return 返回要显示的页面名称
     */
    @GetMapping("/search")  // 处理GET请求，当访问/employees/search时调用此方法
    public String searchEmployees(
            @RequestParam(required = false) String lastName,  // 姓氏，不是必须的
            @RequestParam(required = false) Byte level,  // 级别，不是必须的
            @RequestParam(required = false) Boolean isActive,  // 是否在职，不是必须的
            @RequestParam(defaultValue = "1") int pageNum,  // 当前页码，默认是第1页
            @RequestParam(defaultValue = "10") int pageSize,  // 每页显示数量，默认是10条
            Model model) {
        
        // 创建一个查询条件对象
        Employee condition = new Employee();
        
        // 如果用户输入了姓氏，就添加到查询条件中
        if (lastName != null && !lastName.isEmpty()) {
            condition.setLastName(lastName);
        }
        
        // 如果用户选择了级别，就添加到查询条件中
        if (level != null) {
            condition.setLevel(level);
        }
        
        // 如果用户选择了在职状态，就添加到查询条件中
        if (isActive != null) {
            condition.setIsActive(isActive);
        }

        // 根据条件查询员工数据
        List<Employee> employees = employeeService.getEmployeesByPage(condition, pageNum, pageSize);
        
        // 获取符合条件的总记录数
        long totalCount = employeeService.getTotalCount(condition);
        
        // 计算总页数
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        
        // 将数据添加到model中
        model.addAttribute("employees", employees);  // 员工列表
        model.addAttribute("lastName", lastName);  // 搜索的姓氏
        model.addAttribute("level", level);  // 搜索的级别
        model.addAttribute("isActive", isActive);  // 搜索的在职状态
        model.addAttribute("pageNum", pageNum);  // 当前页码
        model.addAttribute("pageSize", pageSize);  // 每页显示数量
        model.addAttribute("totalCount", totalCount);  // 总记录数
        model.addAttribute("totalPages", totalPages);  // 总页数
        
        return "employee/list";  // 返回要显示的页面名称
    }

    /**
     * 同步员工数据
     * 这个方法是当用户点击"同步数据"按钮时会被调用
     * 会从JSON文件中读取最新的员工数据并更新到数据库中
     * 
     * @param model 用于向页面传递数据的对象
     * @return 重定向到员工列表页面
     */
    @GetMapping("/sync")  // 处理GET请求，当访问/employees/sync时调用此方法
    public String syncEmployees(Model model) {
        // 调用服务层方法同步数据
        boolean success = employeeService.syncEmployee();
        // 将同步结果添加到model中
        model.addAttribute("syncSuccess", success);
        // 重定向到员工列表页面
        return "redirect:/employees";
    }

    /**
     * 清空员工数据
     * 这个方法是当用户点击"清空数据"按钮时会被调用
     * 会清空数据库中的所有员工数据
     * 
     * @param model 用于向页面传递数据的对象
     * @return 重定向到员工列表页面
     */
    @GetMapping("/truncate")  // 处理GET请求，当访问/employees/truncate时调用此方法
    public String truncateTable(Model model) {
        // 调用服务层方法清空数据
        boolean success = employeeService.truncateTable();
        // 将清空结果添加到model中
        model.addAttribute("truncateSuccess", success);
        // 重定向到员工列表页面
        return "redirect:/employees";
    }

    /**
     * 新增员工
     * 这个方法是当用户提交新增员工表单时会被调用
     * 会将表单中的数据保存到数据库中
     * 
     * @param userId 员工ID
     * @param firstName 名字
     * @param lastName 姓氏
     * @param salary 工资
     * @param currency 货币
     * @param birthdate 生日
     * @param level 级别
     * @param isActive 是否在职
     * @param model 用于向页面传递数据的对象
     * @return 重定向到员工列表页面
     */
    @PostMapping("/add")  // 处理POST请求，当访问/employees/add时调用此方法
    public String addEmployee(
            @RequestParam Integer userId,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam BigDecimal salary,
            @RequestParam String currency,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate birthdate,  // 使用ISO日期格式
            @RequestParam Byte level,
            @RequestParam Boolean isActive,
            Model model) {
        
        // 创建新的员工对象
        Employee employee = new Employee();
        employee.setUserId(userId);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setSalary(salary);
        employee.setCurrency(currency);
        employee.setBirthdate(birthdate);
        employee.setLevel(level);
        employee.setIsActive(isActive);
        
        // 设置创建时间和更新时间
        LocalDateTime now = LocalDateTime.now();
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        
        // 调用服务层方法保存员工数据
        boolean success = employeeService.addEmployee(employee);
        
        // 将保存结果添加到model中
        model.addAttribute("addSuccess", success);
        
        // 重定向到员工列表页面
        return "redirect:/employees";
    }
} 