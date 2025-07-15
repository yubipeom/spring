package com.example.learning.controller;

import com.example.learning.entity.Employee;
import com.example.learning.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    // add some comment here updated by Allen
    @Autowired
    private EmployeeService employeeService;
    @GetMapping("/test")
    public Employee test(@RequestBody Employee employee) {
       return employee;
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer userId) {
        Employee employee = employeeService.getEmployeeById(userId);
        return employee != null ? ResponseEntity.ok(employee) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() throws Exception {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/level/{level}")
    public ResponseEntity<List<Employee>> getEmployeesByLevel(@PathVariable Byte level) {
        return ResponseEntity.ok(employeeService.getEmployeesByLevel(level));
    }

    @GetMapping("/active")
    public ResponseEntity<List<Employee>> getActiveEmployees() {
        return ResponseEntity.ok(employeeService.getActiveEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam(required = false) String lastName,
            @RequestParam(required = false) Boolean isActive,
            @RequestParam(required = false) Byte level) {
        Employee condition = new Employee();
        condition.setLastName(lastName);
        condition.setIsActive(isActive);
        condition.setLevel(level);
        return ResponseEntity.ok(employeeService.getEmployeesByCondition(condition));
    }

    @PostMapping
    public ResponseEntity<Boolean> addEmployee(@RequestBody Employee employee) {
        return ResponseEntity.ok(employeeService.addEmployee(employee));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Boolean> updateEmployee(
            @PathVariable Integer userId,
            @RequestBody Employee employee) {
        employee.setUserId(userId);
        return ResponseEntity.ok(employeeService.updateEmployee(employee));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Boolean> deleteEmployee(@PathVariable Integer userId) {
        return ResponseEntity.ok(employeeService.deleteEmployee(userId));
    }

    @GetMapping("/sync")
    public ResponseEntity<Boolean> syncEmployee(){
        boolean result = employeeService.syncEmployee();
        return ResponseEntity.ok(result);
    }
}
