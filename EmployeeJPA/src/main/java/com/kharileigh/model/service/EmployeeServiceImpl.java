/**
 *
 * @author kharileigh
 * <----- 3 : SERVICE LAYER IMPLEMENTATION - call Dao to handle data persistence ------>
 */

package com.kharileigh.model.service;

import com.kharileigh.entity.Employee;
import com.kharileigh.entity.EmployeePaySlip;
import com.kharileigh.model.persistence.EmployeeDao;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("service")
public class EmployeeServiceImpl implements EmployeeService {
    
    // AUTOWIRE DAO DEPENDENCY 
    @Autowired
    private EmployeeDao dao;
    

    @Override
    public Collection<Employee> getAllEmployees() {
        
        return dao.findAll();         
    }

    @Override
    public Employee searchEmployeeById(int id) {
        
        return dao.findById(id).orElse(null);
    }

    
    @Override
    public boolean addEmployee(Employee employee) {
        
        try {
            
            dao.insertEmployee(employee.getEmpId(), employee.getEmpName(), employee.getEmpDesignation(), employee.getEmpDepartment(), employee.getEmpSalary(), employee.getDateOfJoining());
            return true;
        
        } catch(SQLIntegrityConstraintViolationException ex) {
            return false;
            
        } catch (Exception ex) {
            return false;
        }
    }

    
    @Override
    public boolean deleteEmployee(int id) {
        
        if(searchEmployeeById(id) != null) {
           
            dao.deleteById(id);
            
            return true;
        } else {
            return false;
        }
    }

    
    @Override
    public boolean incrementSalary(int id, double increment) {
        
        return dao.updateSalary(id, increment) > 0;
    }

    
    @Override
    public EmployeePaySlip generatePaySlip(int employeeId) {
        
        Employee employee = searchEmployeeById(employeeId);
        
        if(employee != null) {
            
            double allowanceA = .18 * employee.getEmpSalary();
            double allowanceB = .12 * employee.getEmpSalary();
            double deduction = .08 * employee.getEmpSalary();
            double totalSalary = employee.getEmpSalary() + allowanceA + allowanceB - deduction;
            
            EmployeePaySlip employeePaySlip = new EmployeePaySlip(employee, allowanceA, allowanceB, deduction, totalSalary);
            return employeePaySlip;
          
        } else {
            return null;
        }
    }

    
    @Override
    public List<Employee> getEmployeesByDepartment(String deptt) {
        
        return dao.findByEmpDepartment(deptt);
    }

    @Override
    public List<Employee> searchByDesignation(String designation) {
        
        return dao.searchEmployeeByDesignation(designation);
    }

    @Override
    public boolean deleteEmployeeByName(String name) {
        
        return dao.deleteByName(name) > 0;
    }
    
    
}
