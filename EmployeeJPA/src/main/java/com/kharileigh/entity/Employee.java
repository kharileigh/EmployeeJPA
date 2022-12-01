/**
 *
 * @author kharileigh
 * <------ 1st STEP : CREATE POJO - encapsulated entity - getters & setters / lombok API -------->
 * 
 */
package com.kharileigh.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "employee")
public class Employee {
    
    @Id
    @Column(name = "employeeId")
    private int empId;
    
    @Column(name = "name")
    private String empName;
    
    @Column(name = "designation")
    private String empDesignation;
    
    @Column(name = "department")
    private String empDepartment;
    
    @Column(name= "salary")
    private double empSalary;
    
    @Column(name = "doj")
    private LocalDate dateOfJoining;
    
}