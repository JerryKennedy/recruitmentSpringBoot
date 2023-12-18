package com.recruiting.pros.controller;

import com.recruiting.pros.dto.EmployeeDto;
import com.recruiting.pros.service.EmailService;
import com.recruiting.pros.service.EmployeeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService empService) {
        this.employeeService = empService;
    }

    @GetMapping("/employee/registration")
    public String getCreateEmployeeForm(Model model){
        EmployeeDto emp = new EmployeeDto ();
        model.addAttribute("employee", emp);
        return "registration";
    }

    @PostMapping("/employee/registration")
    public String createEmployee(@ModelAttribute("employee") EmployeeDto employeeDto,
                                 @RequestParam("CVFile") MultipartFile cvFile,
                                 @RequestParam String department) throws IOException {
        // Set the department value in applied field
        employeeDto.setApplied(department);

        // Set the CV file and save the employee
        employeeDto.setCVFile(cvFile);
        employeeDto.setCvFileName(cvFile.getOriginalFilename());
        employeeService.saveEmployee(employeeDto, cvFile);

        return "redirect:/";
    }

}
