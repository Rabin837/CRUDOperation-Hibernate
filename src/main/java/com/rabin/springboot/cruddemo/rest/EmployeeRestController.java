//Creating Rest Controller to use DAO
package com.rabin.springboot.cruddemo.rest;
import com.rabin.springboot.cruddemo.entity.Employee;
import com.rabin.springboot.cruddemo.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {
    //Refactor code to use EmployeeService
    //first we are using quick and dirty solution and
    // later we delete that and make use of created Service
    private EmployeeService employeeService;
     @Autowired
    public EmployeeRestController(EmployeeService theEmployeeService){
         employeeService=theEmployeeService;
     }
    //expose "/employees" and return list of employees
    @GetMapping("/employees")
    public List<Employee> findAll(){
         return employeeService.findAll();
    }

    //Read a single employee
    @GetMapping("/employees/{employeeId}")
    public Employee getEmployee(@PathVariable int employeeId) {
        Employee theEmployee = employeeService.findById(employeeId);
        if (theEmployee == null) {
            throw new RuntimeException("Employee id with" + employeeId + " not found!!!");
        }
        return theEmployee;
    }
    //Add a new employee
    @PostMapping("/employees")
    public Employee addEmployee(@RequestBody Employee theEmployee){
         //also just in case they pass an id in JSON... set id to 0 because id is automatically updated in the database
        //this is to force a save of new item... instead of update
         theEmployee.setId(0);
        employeeService.save(theEmployee);
        return theEmployee;
    }
    //Update an employee
    @PutMapping("/employees")
    public Employee updateEmployee(@RequestBody Employee theEmployee){
         employeeService.save(theEmployee);
         return theEmployee;
    }

    //Delete an employee by employeeId
    @DeleteMapping("/employees/{employeeId}")
    public String deleteEmployee(@PathVariable int employeeId){

         Employee tempEmployee= employeeService.findById(employeeId);

         //throw exception if null
        if(tempEmployee==null){
            throw new RuntimeException("Employee Id with "+ employeeId+" is not found");
        }
        employeeService.deleteById(employeeId);

        return "Deleted employee with id "+ employeeId;
    }
}






