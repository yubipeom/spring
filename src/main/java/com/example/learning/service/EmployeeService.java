package com.example.learning.service;

import com.example.learning.entity.Employee;
import java.util.List;

/**
 * 员工服务接口
 * 这个接口定义了所有与员工相关的业务操作
 * 比如：查询、新增、修改、删除等
 * 它是连接控制器和数据访问层的桥梁
 */
public interface EmployeeService {
    /**
     * 根据ID查询员工
     * 就像根据身份证号查找一个人
     * 
     * @param userId 员工ID
     * @return 返回找到的员工信息，如果没找到返回null
     */
    Employee getEmployeeById(Integer userId);
    
    /**
     * 查询所有员工
     * 获取系统中的所有员工记录
     * 
     * @return 返回所有员工的列表
     */
    List<Employee> getAllEmployees() throws Exception;
    
    /**
     * 根据级别查询员工
     * 比如：查询所有级别为3的员工
     * 
     * @param level 员工级别
     * @return 返回指定级别的所有员工列表
     */
    List<Employee> getEmployeesByLevel(Byte level);
    
    /**
     * 根据条件查询员工
     * 可以根据多个条件组合查询，比如：姓氏+级别+在职状态
     * 
     * @param employee 包含查询条件的员工对象
     * @return 返回符合条件的员工列表
     */
    List<Employee> getEmployeesByCondition(Employee employee);
    
    /**
     * 查询活跃员工
     * 获取所有在职的员工记录
     * 
     * @return 返回所有在职员工的列表
     */
    List<Employee> getActiveEmployees();
    
    /**
     * 根据姓氏查询员工
     * 比如：查询所有姓"张"的员工
     * 
     * @param lastName 员工姓氏
     * @return 返回指定姓氏的所有员工列表
     */
    List<Employee> getEmployeesByLastName(String lastName);
    
    /**
     * 新增员工
     * 将新的员工信息保存到系统中
     * 
     * @param employee 要新增的员工信息
     * @return 返回是否新增成功
     */
    boolean addEmployee(Employee employee);
    
    /**
     * 更新员工信息
     * 修改系统中已有的员工信息
     * 
     * @param employee 要更新的员工信息
     * @return 返回是否更新成功
     */
    boolean updateEmployee(Employee employee);
    
    /**
     * 删除员工
     * 根据ID删除指定的员工记录
     * 
     * @param userId 要删除的员工ID
     * @return 返回是否删除成功
     */
    boolean deleteEmployee(Integer userId);

    /**
     * 同步员工数据
     * 从JSON文件中读取最新的员工数据并更新到系统中
     * 
     * @return 返回是否同步成功
     */
    boolean syncEmployee();
    
    /**
     * 分页查询员工
     * 支持分页显示员工列表
     * 
     * @param condition 查询条件
     * @param pageNum 当前页码
     * @param pageSize 每页显示数量
     * @return 返回当前页的员工列表
     */
    List<Employee> getEmployeesByPage(Employee condition, int pageNum, int pageSize);
    
    /**
     * 获取总记录数
     * 用于分页时计算总页数
     * 
     * @param condition 查询条件
     * @return 返回符合条件的总记录数
     */
    long getTotalCount(Employee condition);
    
    /**
     * 清空员工表
     * 删除系统中的所有员工数据
     * 注意：此操作不可恢复，请谨慎使用
     * 
     * @return 返回是否清空成功
     */
    boolean truncateTable();
}
