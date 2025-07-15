package com.example.learning.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 员工实体类
 * 这个类用来表示一个员工的所有信息
 * 比如：姓名、工资、生日等
 */
public class Employee {
    /**
     * 员工ID
     * 这是员工的唯一标识，就像身份证号一样
     */
    private Integer userId;

    /**
     * 员工的名字
     * 比如：张三中的"三"
     */
    private String firstName;

    /**
     * 员工的姓氏
     * 比如：张三中的"张"
     */
    private String lastName;

    /**
     * 员工的工资
     * 使用BigDecimal类型可以精确计算金额，避免浮点数计算误差
     */
    private BigDecimal salary;

    /**
     * 工资的货币单位
     * 比如：CNY（人民币）、USD（美元）等
     */
    private String currency;

    /**
     * 员工的生日
     * 使用LocalDate类型表示日期，不包含具体时间
     */
    private LocalDate birthdate;

    /**
     * 员工是否在职
     * true表示在职，false表示离职
     */
    private Boolean isActive;

    /**
     * 员工的级别
     * 使用Byte类型，范围是0-12
     */
    private Byte level;

    /**
     * 记录的创建时间
     * 使用LocalDateTime类型，包含日期和时间
     */
    private LocalDateTime createdAt;

    /**
     * 记录的最后更新时间
     * 每次修改数据时，这个时间会自动更新
     */
    private LocalDateTime updatedAt;

    /**
     * 分页查询时的起始位置
     * 比如：第2页，每页10条，offset就是10
     */
    private Integer offset;

    /**
     * 分页查询时每页显示的数量
     * 比如：每页显示10条数据
     */
    private Integer limit;

    // 以下是所有字段的getter和setter方法
    // getter方法用于获取字段的值
    // setter方法用于设置字段的值

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Byte getLevel() {
        return level;
    }

    public void setLevel(Byte level) {
        this.level = level;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}
