/**
 *
 * @author kharileigh
 * <------ 4. SET UP CONTROLLERS FOR EACH VIEW ---->
 */

package com.kharileigh.controller;

import com.kharileigh.entity.Employee;
import com.kharileigh.entity.EmployeePaySlip;
import com.kharileigh.model.service.EmployeeService;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class EmployeeController {
    
    // AUTOWIRE DEPENDENCY BEING USED BY CONTROLLER
    @Autowired
    private EmployeeService service;
    
    @RequestMapping("/")
    public ModelAndView menuPageController() {
        
            return new ModelAndView("index");
    }
    
    
    //==========================================================================
    //======================= LIST ALL ========================================
    @RequestMapping("/showAll")
    public ModelAndView getAllEmployeesController() {
        
        ModelAndView modelAndView = new ModelAndView();
        
        Collection<Employee> employeeList = service.getAllEmployees();
        
        modelAndView.addObject("employees", employeeList);
        modelAndView.setViewName("ShowAllEmployees");
        return modelAndView;
    
    }
    
    
    //==========================================================================
    //=================== SEARCH BY ID =======================================
    @RequestMapping("/searchByIDPage")
    public ModelAndView searchByIdPageController() {
    
        // view to get input
        return new ModelAndView("SearchById");
    }
    
    // send http request to get ID
    @RequestMapping("/searchEmployeeByID")
    public ModelAndView searchByIdController(HttpServletRequest request) {
    
        ModelAndView modelAndView = new ModelAndView();
        
        int id = Integer.parseInt(request.getParameter("empId"));
        
        Employee employee = service.searchEmployeeById(id);
        if(employee != null) {
            
            // view to show output if ID is found in datbase
            modelAndView.addObject("employee", employee);
            modelAndView.setViewName("ShowEmployee");
        
        } else {
            
            // view to show output if ID is not found
            modelAndView.addObject("message", "Employee with ID " + id + " does not exist");
            modelAndView.setViewName("Output");
        }
        return modelAndView;
    }
    
    
    
    
    
    //==========================================================================
    //=================== ADD NEW RECORD =======================================
    // matches href in index.html
    @RequestMapping("/addEmpPage")
    public ModelAndView addPageController() {
        
        // returns new page to get details = InputForAdd.html
        return new ModelAndView("AddEmployee");
    }
    
    //matches form action of InputForAdd.html
    @RequestMapping("/addEmployee")
    public ModelAndView addPageController(HttpServletRequest request) {
    
        ModelAndView modelAndView = new ModelAndView();
        Employee employee = new Employee();
        
        int newId = Integer.parseInt(request.getParameter("empId"));
        String newName = request.getParameter("empName");
        String newDesignation = request.getParameter("empDesignation");
        String newDepartment = request.getParameter("empDepartment");
        Double newSalary = Double.parseDouble(request.getParameter("empSalary"));
        //String newDateOfJoining = request.getParameter("empDateOfJoining");
        
        // REVERSING DATE FROM INPUT FORMAT
        DateTimeFormatter df = new DateTimeFormatterBuilder().parseCaseInsensitive().append(DateTimeFormatter.ofPattern("YYYY-MM-DD")).toFormatter();
        LocalDate newDateOfJoining = LocalDate.parse(request.getParameter("dateOfJoining"), df);
        
        employee.setEmpId(newId);
        employee.setEmpName(newName);
        employee.setEmpDesignation(newDesignation);
        employee.setEmpDepartment(newDepartment);
        employee.setEmpSalary(newSalary);
        employee.setDateOfJoining(newDateOfJoining);
        
        
        if(service.addEmployee(employee)) {
            
            modelAndView.addObject("message", "Employee has been successfully added");
            modelAndView.setViewName("Output");
        } else {
            modelAndView.addObject("message", "Employee has NOT been added");
            modelAndView.setViewName("Output");
            
        }
       
        
        return modelAndView;
    }
    
    
    
    
    //==========================================================================
    //=================== DELETE RECORD =======================================
    	@RequestMapping("/deleteEmpPage")
	public ModelAndView deletePageController() {
		return new ModelAndView("InputIdForDelete");
	}
	
	@RequestMapping("/deleteEmployee")
	public ModelAndView deleteEmployeeController(HttpServletRequest request) {
            
		ModelAndView modelAndView = new ModelAndView();
		int deleteId = Integer.parseInt(request.getParameter("empId"));
		String message = null;
                
		if(service.deleteEmployee(deleteId))
			message="Employee Deleted with ID "+ deleteId;
		else
			message="Employee with ID " + deleteId+ " does not exist";
		
		modelAndView.addObject("message", message);
		modelAndView.setViewName("Output");
		
		return modelAndView;
	}
    
    
    
    
    
    //==========================================================================
    //=================== INCREMENT SALARY =====================================
    @RequestMapping("/incrementEmpSalaryPage")
	public ModelAndView incrementSalaryPageController() {
		return new ModelAndView("/IncrementSalaryInput");
    }

    @RequestMapping("/incrementSalary")
    public ModelAndView incrementSalaryController(HttpServletRequest request) {
            ModelAndView modelAndView=new ModelAndView();
            
            int incrementId = Integer.parseInt(request.getParameter("empId"));
            double increment = Double.parseDouble(request.getParameter("increment"));
            String message = null;
            
            if(service.incrementSalary(incrementId, increment))
                    message="Salary for employee with ID "+ incrementId + " incrmented by " + increment ;
            else
                    message="Employee with ID " + incrementId + " doesnot exist";

            modelAndView.addObject("message", message);
            modelAndView.setViewName("Output");

            return modelAndView;
    }
        
        
    
    //==========================================================================
    //=================== GENERATE PAYSLIP =====================================
    @RequestMapping("/generatePayslipPage")
    public ModelAndView generatePayslipIdInputPageController() {
            return new ModelAndView("GeneratePayslip");
    }

    @RequestMapping("/generatePayslip")
    public ModelAndView generatePayslip(HttpServletRequest request) {
        
            ModelAndView modelAndView = new ModelAndView();
            int payslipId = Integer.parseInt(request.getParameter("empId"));

            EmployeePaySlip payslip = service.generatePaySlip(payslipId);
            if(payslip!= null) {
                    modelAndView.addObject("payslip", payslip);
                    modelAndView.setViewName("ShowPayslip");
            }
            else {
                    modelAndView.addObject("message", "Employee with ID " + payslipId + " does not exist");
                    modelAndView.setViewName("Output");
            }

            return modelAndView;
    }
}
