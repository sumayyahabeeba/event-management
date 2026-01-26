package com.example.project.controller;

import com.example.project.model.Registration;
import com.example.project.service.RegistrationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/registrations")
public class RegistrationController {

    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping
    public List<Registration> getAllRegistrations(){
        return registrationService.getAllRegistrations();
    }

    // POST /registrations?employeeId=1&eventId=2
    @PostMapping
    public Registration register(@RequestParam Long employeeId,
                                 @RequestParam Long eventId) {
        return registrationService.register(employeeId, eventId);
    }

    @DeleteMapping("/{id}")
    public void cancel(@PathVariable Long id) {
        registrationService.cancel(id);
    }

    @GetMapping("/event/{eventId}")
    public List<Registration> getByEvent(@PathVariable Long eventId) {
        return registrationService.getByEvent(eventId);
    }

    @GetMapping("/employee/{empId}")
    public List<Registration> getByEmployee(@PathVariable Long empId) {
        return registrationService.getByEmployee(empId);
    }

    @PostMapping("/{id}/attend")
    public Registration attend(@PathVariable Long id) {
        return registrationService.markAttendance(id);
    }
}
