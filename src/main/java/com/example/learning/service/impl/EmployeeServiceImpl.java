package com.example.learning.service.impl;

import com.example.learning.entity.Employee;
import com.example.learning.entity.EmployeeJson;
import com.example.learning.mapper.EmployeeMapper;
import com.example.learning.service.EmployeeService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    
    private static final Logger logger = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    
    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Override
    public Employee getEmployeeById(Integer userId) {
        return employeeMapper.selectById(userId);
    }
    
    @Override
    public List<Employee> getAllEmployees() throws Exception {
        return employeeMapper.selectAll();
    }
    
    @Override
    public List<Employee> getEmployeesByLevel(Byte level) {
        Employee condition = new Employee();
        condition.setLevel(level);
        return employeeMapper.selectByCondition(condition);
    }
    
    @Override
    public List<Employee> getEmployeesByCondition(Employee employee) {
        return employeeMapper.selectByCondition(employee);
    }
    
    @Override
    public List<Employee> getActiveEmployees() {
        Employee condition = new Employee();
        condition.setIsActive(true);
        return employeeMapper.selectByCondition(condition);
    }
    
    @Override
    public List<Employee> getEmployeesByLastName(String lastName) {
        Employee condition = new Employee();
        condition.setLastName(lastName);
        return employeeMapper.selectByCondition(condition);
    }
    
    @Override
    public boolean addEmployee(Employee employee) {
        return employeeMapper.insert(employee) > 0;
    }
    
    @Override
    public boolean updateEmployee(Employee employee) {
        return employeeMapper.update(employee) > 0;
    }
    
    @Override
    public boolean deleteEmployee(Integer userId) {
        return employeeMapper.deleteById(userId) > 0;
    }
    
    @Override
    @Transactional
    public boolean syncEmployee() {
        try {
            // 读取JSON文件
            ClassPathResource resource = new ClassPathResource("employee.json");
            logger.info("开始读取 employee.json 文件");

            // 读取文件内容
            String jsonContent = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            if (jsonContent == null || jsonContent.trim().isEmpty()) {
                logger.error("employee.json 文件内容为空");
                return false;
            }

            List<EmployeeJson> employeeJsons = objectMapper.readValue(
                jsonContent,
                new TypeReference<List<EmployeeJson>>() {}
            );
            
            logger.info("成功解析JSON数据，共 {} 条记录", employeeJsons.size());
            
            // 转换为Employee实体
            List<Employee> employees = employeeJsons.stream()
                .map(this::convertToEmployee)
                .collect(Collectors.toList());
            
            logger.info("开始批量插入数据");
            
            // 批量插入数据
            for (Employee employee : employees) {
                if (validateEmployee(employee)) {
                    employeeMapper.insert(employee);
                } else {
                    logger.warn("跳过无效的员工数据: {}", employee);
                }
            }
            
            logger.info("数据同步完成");
            return true;
        } catch (IOException e) {
            logger.error("同步数据时发生错误: {}", e.getMessage(), e);
            return false;
        }
    }
    
    private Employee convertToEmployee(EmployeeJson json) {
        if (json == null) {
            logger.warn("收到空的 EmployeeJson 对象");
            return null;
        }
        
        logger.info("正在转换 EmployeeJson: {}", json);
        
        Employee employee = new Employee();
        employee.setUserId(json.getUserId());
        employee.setFirstName(json.getFirstName());
        employee.setLastName(json.getLastName());
        employee.setSalary(json.getSalary());
        employee.setCurrency(json.getCurrency());
        employee.setBirthdate(json.getBirthdateAsLocalDate());
        employee.setIsActive(json.getIsActive());
        employee.setLevel(json.getLevel());
        
        // 设置创建和更新时间
        LocalDateTime now = LocalDateTime.now();
        employee.setCreatedAt(now);
        employee.setUpdatedAt(now);
        
        logger.debug("转换后的员工数据: {}", employee);
        return employee;
    }
    
    private boolean validateEmployee(Employee employee) {
        if (employee == null) {
            return false;
        }
        
        boolean isValid = employee.getUserId() != null 
            && StringUtils.hasText(employee.getFirstName())
            && StringUtils.hasText(employee.getLastName())
            && employee.getSalary() != null
            && StringUtils.hasText(employee.getCurrency())
            && employee.getBirthdate() != null
            && employee.getIsActive() != null
            && employee.getLevel() != null;
            
        if (!isValid) {
            logger.warn("员工数据验证失败: userId={}, firstName={}, lastName={}, salary={}, currency={}, birthdate={}, isActive={}, level={}",
                employee.getUserId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getSalary(),
                employee.getCurrency(),
                employee.getBirthdate(),
                employee.getIsActive(),
                employee.getLevel()
            );
        }
        
        return isValid;
    }

    @Override
    public List<Employee> getEmployeesByPage(Employee condition, int pageNum, int pageSize) {
        // 计算偏移量
        int offset = (pageNum - 1) * pageSize;
        // 设置分页参数
        condition.setOffset(offset);
        condition.setLimit(pageSize);
        return employeeMapper.selectByCondition(condition);
    }

    @Override
    public long getTotalCount(Employee condition) {
        return employeeMapper.countByCondition(condition);
    }
    
    @Override
    @Transactional
    public boolean truncateTable() {
        try {
            employeeMapper.truncateTable();
            return true;
        } catch (Exception e) {
            logger.error("清空员工表时发生错误: {}", e.getMessage(), e);
            return false;
        }
    }
}
