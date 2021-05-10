package springboot.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import springboot.exception.ResourceNotFoundException;
import springboot.model.Employee;
import springboot.repository.EmployeeRepository;

@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	private EmployeeRepository employeeRepository;

	
	@GetMapping("/employees")
	public List<Employee> getAllEmployees(){
		return employeeRepository.findAll();
		
	}
	
	@PostMapping("/addEmployee")
	public Employee addEmployee(@RequestBody Employee employee) {
		
		return employeeRepository.save(employee);
		
	}
	
	@GetMapping("/employees/{id}")
	public ResponseEntity<Employee > findById(@PathVariable Long id) {
		
		Employee employee=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Id not found"));
		
		return ResponseEntity.ok(employee);
		

	}
	
	@PutMapping("/updateEmployee/{id}")
	@PostMapping(consumes = "application/json")
	public ResponseEntity<Employee >  updateEmployee(@PathVariable Long id, @RequestBody Employee employee) {
		
		Employee employeeFound=employeeRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Employee Id not found"));
		employeeFound.setFirstName(employee.getFirstName());
		employeeFound.setEmail(employee.getEmail());
		employeeFound.setLastName(employee.getLastName());
		
		Employee employeeUpdated=employeeRepository.save(employeeFound);
		
	
		
//		Optional<Employee> employeeOpt=employeeRepository.findById(id);
//		Employee employeeOld=employeeOpt.get();
//		employeeOld.setEmail(employee.getEmail());
//		employeeOld.setFirstName(employee.getFirstName());
//		employeeOld.setLastName(employee.getLastName());
//		
		
		
		return ResponseEntity.ok(employeeUpdated);
	}
	
	
	@DeleteMapping("/deleteEmployee/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable Long id) {
		
		Employee employee = employeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("id not found"));
		
		employeeRepository.delete(employee);
		
		return ResponseEntity.ok(employee);
		
	}
	
}
