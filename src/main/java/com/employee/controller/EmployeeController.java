package com.employee.controller;

import com.employee.ResourcesNotFoundException.ResourceNotFoundException;
import com.employee.model.Employee;
import com.employee.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins ="http://localhost:3000")
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepo employeeRepo;
    @GetMapping("/getall")
    public List<Employee> getAll(){
       return employeeRepo.findAll();
    }

    @PostMapping("/add" )
    public Employee addemp(@RequestBody Employee employee){
        return employeeRepo.save(employee);
    }
    @GetMapping("getById/{id}")
    public ResponseEntity <Employee> getById(@PathVariable int id ){
        Employee employee;
        employee = employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        return ResponseEntity.ok(employee);

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable int id ,@RequestBody   Employee employeeupdates){
        Employee employee;
        employee= employeeRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("invalid id"));
        employee.setFirstName(employeeupdates.getFirstName());
        employee.setLastName(employeeupdates.getLastName());
        employee.setEmail(employeeupdates.getEmail());

        Employee employeeUpdated = employeeRepo.save(employee);

        return ResponseEntity.ok(employeeUpdated);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Map<String , Boolean>> deleteEmp(@PathVariable int id){
        Employee employee = employeeRepo.findById(id).orElseThrow(() ->new ResourceNotFoundException("invalid id"));

        employeeRepo.delete(employee);
        Map<String ,Boolean> response= new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return ResponseEntity.ok(response);
    }

}
