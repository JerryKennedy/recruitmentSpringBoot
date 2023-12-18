package com.recruiting.pros.controller;
import com.recruiting.pros.dto.EmployeeDto;
import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.dto.UserDto;
import com.recruiting.pros.model.Job;
import com.recruiting.pros.model.User;
import com.recruiting.pros.service.EmailService;
import com.recruiting.pros.service.EmployeeService;
import com.recruiting.pros.service.JobService;
import com.recruiting.pros.service.UserService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    private final JobService jobService;
    private final UserService userService;
    private final EmployeeService employeeService;
    private final EmailService emailService;

    public AdminController(JobService jobService, UserService userService, EmployeeService employeeService, EmailService emailService) {
        this.jobService = jobService;
        this.userService = userService;
        this.employeeService = employeeService;
        this.emailService = emailService;
    }

    @PostMapping("/postJob")
    public String saveJob(@ModelAttribute JobDto jobDto) {
        jobService.saveJob(jobDto);
        return "redirect:/admin"; // Redirect to admin dashboard or appropriate page
    }

    @GetMapping("/editJob/{jobId}")
    public String editJob(@PathVariable Integer jobId, Model model) {
        JobDto jobDto = jobService.getJobById(jobId); // Implement this method in your service
        model.addAttribute("jobDto", jobDto);
        return "pages/tableJob"; // Create an edit job page with the form
    }

    @PostMapping("/updateJob")
    public String updateJob(@ModelAttribute JobDto jobDto, Model model) {
        jobService.updateJob(jobDto);
        List<Job> updatedJobs = jobService.getAllJobs();
        model.addAttribute("jobs", updatedJobs);
        return "pages/tableJob"; // Return the fragment HTML th
        // at renders the table
    }


    @PostMapping("/createUser")
    public String createUser(@ModelAttribute("userDto") UserDto userDto,
                             @RequestParam("roleName") String roleName) {
        userService.createUserWithRole(userDto, roleName);
        return "create-user";
    }

    @GetMapping("/userList")
    public String userList(Model model) {
        List<User> user = userService.getAllUsers();
        model.addAttribute("user", user);
        return "userList";
    }


    @GetMapping("/admin/editUser/{id}")
    public String getEditForm(@PathVariable("id") Long id, Model model){
        model.addAttribute("user", userService.findUserById(id));
        return "create-user";
    }

    @PostMapping("/editUser")
    public String editUser(@ModelAttribute("userDto") UserDto userDto,
                           @RequestParam("roleName") String roleName) {
        if (userDto.getId() == null) {
            userService.createUserWithRole(userDto, roleName);
        } else {
            userService.updateUserWithRole(userDto, roleName);
        }
        return "redirect:/userList";
    }
    @PostMapping("/admin/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/userList";
    }


    @GetMapping("/admin")
    public String getAdminDashboard(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        long financeApplicantsCount = employees.stream().filter(e -> "Finance".equals(e.getApplied())).count();
        model.addAttribute("financeApplicantsCount", financeApplicantsCount);

        long MarketingApplicantsCount = employees.stream().filter(e -> "Marketing".equals(e.getApplied())).count();
        model.addAttribute("MarketingApplicantsCount", MarketingApplicantsCount);

        long NetworkApplicantsCount = employees.stream().filter(e -> "Networking".equals(e.getApplied())).count();
        model.addAttribute("NetworkApplicantsCount", NetworkApplicantsCount);

        long SoftwareApplicantsCount = employees.stream().filter(e -> "Software".equals(e.getApplied())).count();
        model.addAttribute("SoftwareApplicantsCount", SoftwareApplicantsCount);
        return "admindash";
    }

    @GetMapping("/pages/table2")
    public String getMarketingDashboard(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        long MarketingApplicantsCount = employees.stream().filter(e -> "Marketing".equals(e.getApplied())).count();
        model.addAttribute("MarketingApplicantsCount", MarketingApplicantsCount);

        return "pages/table2";
    }

    @GetMapping("/pages/table1")
    public String getFinanceDashboard(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        long financeApplicantsCount = employees.stream().filter(e -> "Finance".equals(e.getApplied())).count();
        model.addAttribute("financeApplicantsCount", financeApplicantsCount);
        return "pages/table1";
    }

    @GetMapping("/pages/table3")
    public String getNetworkingDashboard(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        long NetworkApplicantsCount = employees.stream().filter(e -> "Networking".equals(e.getApplied())).count();
        model.addAttribute("NetworkApplicantsCount", NetworkApplicantsCount);

        return "pages/table3";
    }

    @GetMapping("/pages/tables")
    public String getSoftwareDashboard(Model model) {
        List<EmployeeDto> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);

        long SoftwareApplicantsCount = employees.stream().filter(e -> "Software".equals(e.getApplied())).count();
        model.addAttribute("SoftwareApplicantsCount", SoftwareApplicantsCount);
        return "pages/tables";
    }

    @GetMapping("/pages/tableJob")
    public String getAllJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs(); // Fetch the list of jobs from your repository
        model.addAttribute("jobs", jobs);
        return "pages/tableJob"; // Return the name of your Thymeleaf template
    }

    @GetMapping("/updateButtons")
    public String updateButtons(Model model) {
        // Fetch data and populate the model attribute
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);

        // Return the Thymeleaf template
        return "buttons"; // This is the name of your buttons.html template
    }


    @GetMapping("/admin/download/{employeeId}")
    public ResponseEntity<ByteArrayResource> downloadCV(@PathVariable int employeeId) {
        Optional<EmployeeDto> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isPresent()) {
            EmployeeDto employeeDto = employeeOptional.get();
            byte[] cvBytes = employeeDto.getCv();
            String fileName = employeeDto.getCvFileName(); // Use the original file name

            ByteArrayResource resource = new ByteArrayResource(cvBytes);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(cvBytes.length)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/admin/accept/{employeeId}")
    public String acceptEmployee(@PathVariable int employeeId) {
        Optional<EmployeeDto> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isPresent()) {
            EmployeeDto employeeDto = employeeOptional.get();
            // Modify the subject and body of the email as needed
            String subject = "Job Application Acceptance";
            String body = "Dear " + employeeDto.getName() + ",\n\n" +
                    "Congratulations! We are pleased to inform you that your application for the position of " + employeeDto.getApplied() + "has been accepted.\n\n" +
                    "Your qualifications and experience stood out among the applicants, and we believe you will be a valuable addition to our team. " +
                    "\n\n" +
                    "The next step in the process is to schedule an interview, " +
                    "where we will discuss the role in more detail and get to know you better. " +
                    "We are looking forward to meeting you in person " +
                    "\n\n" +
                    "Please let us know your availability for the interview, " +
                    "and we will coordinate a suitable time. " +
                    "If you have any questions or need further information, " +
                    "feel free to reach out to us. " +
                    "\n\n" +
                    "Once again, congratulations on your acceptance, and we hope to see you soon!"+
                    "\n\n"+
                    "Best regards,\n" +
                    "Your Recruiting Team";
            emailService.sendRegistrationEmail(employeeDto.getEmail(), subject, body);
            employeeService.updateEmployeeStatus(employeeId, "Approved");
        }
        return "redirect:/admin"; // Redirect to the admin dashboard after processing
    }

    @PostMapping("/admin/decline/{employeeId}")
    public String declineEmployee(@PathVariable int employeeId) {
        Optional<EmployeeDto> employeeOptional = employeeService.getEmployeeById(employeeId);
        if (employeeOptional.isPresent()) {
            EmployeeDto employeeDto = employeeOptional.get();
            // Modify the subject and body of the email as needed
            String subject = "Job Application Update";
            String body = "Dear " + employeeDto.getName() + ",\n\n" +
                    "Thank you for taking the time to apply for the position of " + employeeDto.getApplied() + ".\n\n" +
                    "After careful consideration and review of all applicants, we regret to inform you that your application was not selected for further consideration. " +
                    "Please know that this decision does not reflect on your qualifications or skills; we received many highly qualified candidates," +
                    " making the selection process challenging.\n\n" +
                    "Your dedication and enthusiasm for the position were evident in your application, " +
                    "and we encourage you to keep applying to opportunities that match your skills and interests.\n\n" +
                    "We sincerely appreciate your interest in our company, " +
                    "and we wish you the best in your job search. " +
                    "Please consider us for future opportunities that align with your expertise.\n\n" +
                    "Thank you again for your interest, and we hope to see you succeed in your future endeavors."+
                    "\n\n"+
                    "Best regards,\n" +
                    "Your Recruiting Team";
            emailService.sendRegistrationEmail(employeeDto.getEmail(), subject, body);
            employeeService.updateEmployeeStatus(employeeId, "Rejected");
        }
        return "redirect:/admin"; // Redirect to the admin dashboard after processing
    }

    @GetMapping("/admin/search")
    public String searchEmployees(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String applied,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String status,
            Model model
    ) {
        List<EmployeeDto> searchResults = employeeService.searchEmployees(name, applied, email, status);
        model.addAttribute("employees", searchResults);
        return "fragments/employeeTable :: employeeTable";
    }
}





