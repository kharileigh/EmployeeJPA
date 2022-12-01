/**
 *
 * @author kharileigh
 * <----- 3 : SERVICE LAYER ------>
 */
package com.kharileigh.model.service;

import com.kharileigh.entity.Employee;
import com.kharileigh.entity.EmployeePaySlip;
import java.util.Collection;
import java.util.List;


public interface EmployeeService {
    
    Collection<Employee> getAllEmployees();

    Employee searchEmployeeById(int id);

    boolean addEmployee(Employee employee);

    boolean deleteEmployee(int id);

    boolean incrementSalary(int id,double increment);

    EmployeePaySlip generatePaySlip(int employeeId);

    List<Employee> getEmployeesByDepartment(String deptt);

    List<Employee> searchByDesignation(String designation);

    boolean deleteEmployeeByName(String name);
}
