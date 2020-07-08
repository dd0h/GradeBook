package com.gradebook;


import com.gradebook.entities.grades.Grades;
import com.gradebook.entities.grades.GradesRepository;
import com.gradebook.entities.gradesInfo.GradesInfo;
import com.gradebook.entities.gradesInfo.GradesInfoRepository;
import com.gradebook.entities.teachers.Teacher;
import com.gradebook.entities.teachers.TeacherRepository;
import com.gradebook.entities.users.User;
import com.gradebook.entities.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Optional;

@Controller
@RequestMapping(path="/")
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GradesInfoRepository gradesInfoRepository;
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private GradesRepository gradesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(path = "/register")
    public String addNewUserForm(Model model) {
        model.addAttribute("user", new User());
        return "fragments/forms/register";
    }

    @PostMapping(path = "/register")
    public String addNewUserSubmit(@Valid @ModelAttribute User user,
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
            } else if(!user.getPassword().equals(user.getPassword_repeated())) {
                attributes.addFlashAttribute("register_fail", "Passwords do not match!");
                return "redirect:/register";
            } else {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                attributes.addFlashAttribute("register_success", "Your registration was successful!");
                return "redirect:/login";
            }
        }
    }

    @GetMapping(path="/info")
    public String Info(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        if(user.isPresent()){
            model.addAttribute("userinfo", user.get());
        }
        return "fragments/userprofile";
    }

    @GetMapping(path="/grades")
    public String Grades(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        if(user.isPresent()) {
            Integer userId = user.get().getId();
            Optional<GradesInfo[]> gradesInfo = gradesInfoRepository.getUserGradesInfo(userId);
            if (gradesInfo.isPresent()) {
                model.addAttribute("gradesInfo", gradesInfo.get());
            }
        }
        return "fragments/grades";
    }

    @GetMapping(path="/give_mark")
    public String GiveMark(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        model.addAttribute("grade", new Grades());
        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        if(user.isPresent()){
            Integer teacherId = user.get().getId();
            Optional<Teacher> teacher = teacherRepository.findById(teacherId);
            if(teacher.isPresent()){
                String subject = teacher.get().getSubject();
                model.addAttribute("subject", subject);
            }
        }

        Optional<User[]> students = userRepository.getAllStudents();
        if(students.isPresent()){
            model.addAttribute("students", students.get());
        }
        return "fragments/give_mark";
    }

    @PostMapping(path="/give_mark")
    public String GiveMarkSubmit(@Valid @ModelAttribute Grades grade,
                                 BindingResult result,
                                 RedirectAttributes attributes,
                                 @AuthenticationPrincipal UserDetailsImpl principal){
        Teacher teacher = new Teacher();
        teacher.setId(userRepository.findByUsername(principal.getUsername()).get().getId());
        grade.setTeacher(teacher);
        gradesRepository.save(grade);
        attributes.addFlashAttribute("mark_given", "You have succesfully given a mark to a student");
        return "redirect:/give_mark";
    }
}



