package com.recruiting.pros.service;

import com.recruiting.pros.dto.EmployeeDto;
import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.model.Employee;
import com.recruiting.pros.model.Job;
import com.recruiting.pros.repository.EmployeeRepo;
import com.recruiting.pros.repository.JobRepo;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl  implements EmployeeService{
    
    private final EmailService emailService;

    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeServiceImpl(EmailService emailService, EmployeeRepo employeeRepository) {
        this.emailService = emailService;
        this.employeeRepo = employeeRepository;

    }

    @Override
    public List<EmployeeDto> getAllEmployees() {
        List<Employee> employees = employeeRepo.findAll();
        return employees.stream()
                .map(this::mapToEmployeeDto)
                .collect(Collectors.toList());
    }



    @Override
    public Optional<EmployeeDto> getEmployeeById(int id) {
        Optional<Employee> employeeOptional = employeeRepo.findById(id);
        return employeeOptional.map(this::mapToEmployeeDto);
    }




    private EmployeeDto mapToEmployeeDto(Employee employee){
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setApplied(employee.getApplied());
        employeeDto.setId(employee.getId());
        employeeDto.setName(employee.getName());
        employeeDto.setEmail(employee.getEmail());
        employeeDto.setNumber(employee.getNumber());
        employeeDto.setGender(employee.getGender());
        employeeDto.setStudent(employee.getStudent());
        employeeDto.setInterest(employee.getInterest());
        employeeDto.setLanguage(employee.getLanguage());
        employeeDto.setSkills(employee.getSkills());
        employeeDto.setWork(employee.getWork());
        employeeDto.setCv(employee.getCv());
        employeeDto.setCvFileName(employee.getCvFileName());
        employeeDto.setStatus(employee.getStatus());
        return employeeDto;

    }

    @Override
    public void saveEmployee(EmployeeDto employeeDto, MultipartFile cvFile) throws IOException {
        byte[] cvBytes = cvFile.getBytes();
        if (employeeDto.getStatus() == null || employeeDto.getStatus().isEmpty()) {
            employeeDto.setStatus("Pending");
        }
        employeeDto.setCv(cvBytes);
        employeeDto.setCvFileName(cvFile.getOriginalFilename()); // Set the original file name

        // Save the employee
        Employee employee = mapToEmployee(employeeDto);
        employeeRepo.save(employee);


        // Send the registration email
        String to = employeeDto.getEmail();
        String subject = "RecruitMe: Thank You for Your Application";
        String body = "Dear " + employeeDto.getName() + ",\n\n" +
                "Thank you for your application for the position of " + employeeDto.getApplied() + ".\n\n" +
                "We're pleased that you are interested in joining our company to make your ideas real.\n\n" +
                "What happens next? The recruiting team will check your documents and contact you as soon as possible. Please give us some time for a thorough review.\n\n" +
                "In the meantime, you may be interested in reading the inspiring people stories on our careers website. We also invite you to follow us on LinkedIn to keep up to date with the latest news on our company.\n\n" +
                "Best regards,\n" +
                "Your Recruiting Team";
        emailService.sendRegistrationEmail(to, subject, body);
    }

    private Employee mapToEmployee(EmployeeDto employeeDto){
        Employee employee = new Employee();
        employee.setId(employeeDto.getId());
        employee.setApplied(employeeDto.getApplied());
        employee.setName(employeeDto.getName());
        employee.setEmail(employeeDto.getEmail());
        employee.setNumber(employeeDto.getNumber());
        employee.setGender(employeeDto.getGender());
        employee.setStudent(employeeDto.getStudent());
        employee.setInterest(employeeDto.getInterest());
        employee.setLanguage(employeeDto.getLanguage());
        employee.setSkills(employeeDto.getSkills());
        employee.setWork(employeeDto.getWork());
        employee.setCv(employeeDto.getCv());
        employee.setCvFileName(employeeDto.getCvFileName());
        employee.setStatus(employeeDto.getStatus());
        return employee;
    }


    @Override
    public void deleteEmployeeById(int id) {
        employeeRepo.deleteById(id);
    }

    @Override
    public void updateEmployee(EmployeeDto employeeDto) {
        Employee existingEmployee = employeeRepo.findById(employeeDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        Employee updatedEvent = mapToEmployee(employeeDto);
        updatedEvent.setId(existingEmployee.getId());
        employeeRepo.save(updatedEvent);
    }



    @Override
    public void updateEmployeeStatus(int employeeId, String status) {
        Optional<Employee> employeeOptional = employeeRepo.findById(employeeId);
        if (employeeOptional.isPresent()) {
            Employee employeeDto = employeeOptional.get();
            employeeDto.setStatus(status);
            employeeRepo.save(employeeDto);
        }
    }



    @Override
    public List<EmployeeDto> searchEmployees(String name, String applied, String email, String status) {
        List<Employee> employees = employeeRepo.findByNameAndAppliedAndEmailAndStatus(name, applied, email, status);
        return employees.stream()
                .map(this::mapToEmployeeDto)
                .collect(Collectors.toList());
    }
    }

