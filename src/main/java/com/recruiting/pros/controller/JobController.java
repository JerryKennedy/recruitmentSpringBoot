package com.recruiting.pros.controller;


import com.recruiting.pros.dto.JobDto;
import com.recruiting.pros.model.Job;
import com.recruiting.pros.service.JobService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class JobController {

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping("/buttons")
    public String showJobs(Model model) {
        List<Job> jobs = jobService.getAllJobs();
        model.addAttribute("jobs", jobs);
        return "buttons";
    }



    @GetMapping("/buttons/{id}")
    public String showJobDetails(@PathVariable("id") Integer id, Model model) {
        JobDto job = jobService.getJobById(id);
        model.addAttribute("job", job);

        LocalDate currentDateTime = LocalDate.now();
        boolean isDeadlineReached = job.getDeadline().isBefore(currentDateTime);
        model.addAttribute("isDeadlineReached", isDeadlineReached);

        // Pass department to the popup form
        model.addAttribute("department", job.getDepartment());

        return "buttons";
    }




}



































//import com.recruiting.pros.model.Job;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.text.SimpleDateFormat;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.Locale;
//
//@Controller
//public class JobController {
//
//    @GetMapping("/job")
//    public String showJobDetails(Model model) {
//        // Fetch or set the job details, including the deadline date
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, Calendar.AUGUST, 2, 12, 0, 0);
//        Date deadline = calendar.getTime();
//
//        Job job = new Job(deadline);
//        String formattedDeadline = formatDeadline(deadline);
//
//        model.addAttribute("job", job);
//        model.addAttribute("formattedDeadline", formattedDeadline);
//
//        return "job"; // Return the name of the Thymeleaf template
//    }
//
//    private String formatDeadline(Date deadline) {
//        SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd yyyy HH:mm:ss 'GMT'Z (zzzz)", Locale.ENGLISH);
//        return sdf.format(deadline);
//    }
//}
