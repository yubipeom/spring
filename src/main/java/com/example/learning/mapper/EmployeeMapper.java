package com.example.learning.mapper;

import com.example.learning.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 员工数据访问接口
 * 这个接口负责与数据库进行交互，处理所有与员工数据相关的数据库操作
 * 比如：查询、新增、修改、删除等
 */
@Mapper  // 告诉Spring这是一个MyBatis的Mapper接口
public interface EmployeeMapper {
    /**
     * 根据ID查询员工
     * 就像根据身份证号查找一个人
     * 
     * @param userId 员工ID
     * @return 返回找到的员工信息，如果没找到返回null
     */
    Employee selectById(@Param("id") Integer userId);
    
    /**
     * 查询所有员工
     * 获取数据库中的所有员工记录
     * 
     * @return 返回所有员工的列表
     */
    List<Employee> selectAll();
    
    /**
     * 根据级别查询员工
     * 比如：查询所有级别为3的员工
     * 
     * @param level 员工级别
     * @return 返回指定级别的所有员工列表
     */
    List<Employee> selectByLevel(@Param("level") Byte level);
    
    /**
     * 根据条件查询员工
     * 可以根据多个条件组合查询，比如：姓氏+级别+在职状态
     * 
     * @param employee 包含查询条件的员工对象
     * @return 返回符合条件的员工列表
     */
    List<Employee> selectByCondition(Employee employee);
    
    /**
     * 新增员工
     * 将新的员工信息保存到数据库中
     * 
     * @param employee 要新增的员工信息
     * @return 返回影响的行数，大于0表示新增成功
     */
    int insert(Employee employee);
    
    /**
     * 更新员工信息
     * 修改数据库中已有的员工信息
     * 
     * @param employee 要更新的员工信息
     * @return 返回影响的行数，大于0表示更新成功
     */
    int update(Employee employee);
    
    /**
     * 删除员工
     * 根据ID删除指定的员工记录
     * 
     * @param userId 要删除的员工ID
     * @return 返回影响的行数，大于0表示删除成功
     */
    int deleteById(@Param("id") Integer userId);

    /**
     * 统计符合条件的员工数量
     * 用于分页时计算总记录数
     * 
     * @param employee 包含查询条件的员工对象
     * @return 返回符合条件的员工总数
     */
    long countByCondition(Employee employee);
    
    /**
     * 清空员工表
     * 删除表中的所有数据，但保留表结构
     * 注意：此操作不可恢复，请谨慎使用
     */
    void truncateTable();
} 