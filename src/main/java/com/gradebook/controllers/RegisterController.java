package com.gradebook.controllers;

import com.gradebook.entities.classes.Class;
import com.gradebook.entities.classes.ClassRepository;
import com.gradebook.entities.grades.GradesRepository;
import com.gradebook.entities.gradesInfo.GradesInfoRepository;
import com.gradebook.entities.teachers.Teacher;
import com.gradebook.entities.teachers.TeacherRepository;
import com.gradebook.entities.users.Status;
import com.gradebook.entities.users.User;
import com.gradebook.entities.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping(path="/register")
public class RegisterController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private ClassRepository classRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping()
    public String addNewUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("class", new Class());
        model.addAttribute("teacher", new Teacher());
        return "fragments/forms/register";
    }

    @PostMapping()
    public String addNewUserSubmit(@Valid @ModelAttribute User user,
                                   @Valid @ModelAttribute Class Class,
                                   @Valid @ModelAttribute Teacher teacher,
                                   BindingResult result,
                                   RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("register_fail", "Check if you have all fields!");
            return "fragments/forms/register";
        } else {
            if (userRepository.existsByUsername(user.getUsername())) {
                attributes.addFlashAttribute("register_fail", "User with that name already exists!");
                return "redirect:/register";
            } else if (userRepository.existsByEmail(user.getEmail())) {
                attributes.addFlashAttribute("register_fail", "This email is already in use!");
                return "redirect:/register";
            } else if (!user.getPassword().equals(user.getPassword_repeated())) {
                attributes.addFlashAttribute("register_fail", "Passwords do not match!");
                return "redirect:/register";
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                if (user.getStatus().toString().equals(Status.STUDENT.toString())) {
                    Class.setUser(user);
                    classRepository.save(Class);
                } else if (user.getStatus().toString().equals(Status.TEACHER.toString())) {
                    teacher.setUser(user);
                    teacherRepository.save(teacher);
                }
                attributes.addFlashAttribute("register_success", "Your registration was successful!");
                return "redirect:/login";
            }
        }
    }
}
