package com.recruiting.pros.service;

import com.recruiting.pros.dto.EmployeeDto;
import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface EmployeeService {
    List<EmployeeDto> getAllEmployees();

    Optional<EmployeeDto> getEmployeeById(int id);
    void saveEmployee(EmployeeDto employeeDto, MultipartFile cvFile) throws IOException;
    void deleteEmployeeById(int id);
    void updateEmployeeStatus(int employeeId, String status);

    void updateEmployee(EmployeeDto employeeDto);
    List<EmployeeDto> searchEmployees(String name, String applied, String email, String status);
}
