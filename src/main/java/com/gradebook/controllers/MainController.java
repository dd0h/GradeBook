package com.gradebook.controllers;


import com.gradebook.UserDetailsImpl;
import com.gradebook.entities.classes.Class;
import com.gradebook.entities.classes.ClassName;
import com.gradebook.entities.classes.ClassRepository;
import com.gradebook.entities.grades.Grades;
import com.gradebook.entities.grades.GradesRepository;
import com.gradebook.entities.gradesInfo.GradesInfo;
import com.gradebook.entities.gradesInfo.GradesInfoRepository;
import com.gradebook.entities.teachers.Subject;
import com.gradebook.entities.teachers.Teacher;
import com.gradebook.entities.teachers.TeacherRepository;
import com.gradebook.entities.users.Status;
import com.gradebook.entities.users.User;
import com.gradebook.entities.users.UserRepository;
import org.graalvm.compiler.lir.LIRInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    private ClassRepository classRepository;

    @GetMapping(path="/info")
    public String Info(@AuthenticationPrincipal UserDetailsImpl principal, Model model){
        Optional<User> user = userRepository.findByUsername(principal.getUsername());
        if(user.isPresent()) {
            model.addAttribute("userinfo", user.get());
            if(user.get().getStatus() == Status.STUDENT){
                Optional<Class> Class = classRepository.findById(user.get().getId());
                if(Class.isPresent()) {
                    model.addAttribute("Class", Class.get());
                }
            } else if(user.get().getStatus() == Status.TEACHER){
                Optional<Teacher> teacher = teacherRepository.findById(user.get().getId());
                if(teacher.isPresent()) {
                    model.addAttribute("teacher", teacher.get());
                }
            }
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
                Subject subject = teacher.get().getSubject();
                model.addAttribute("subject", subject);
            }
        }

        List<User> students = userRepository.getAllStudents();
        List<Class> classes = classRepository.findAll();
        List<List<User>> usersSortedByClass = new ArrayList<>();
        for(ClassName className : ClassName.values()){
            List<User> usersOfSingleClass = new ArrayList<>();
            for(User student : students)
                for(Class Class : classes)
                    if(student.getId() == Class.getId() &&  Class.getClassName().equals(className))
                        usersOfSingleClass.add(student);

            usersSortedByClass.add(usersOfSingleClass);
        }
        model.addAttribute("usersSortedByClass", usersSortedByClass);
        return "fragments/give_mark";
    }

    @PostMapping(path="/give_mark")
    public String GiveMarkSubmit(@Valid @ModelAttribute Grades grade,
                                 RedirectAttributes attributes,
                                 @AuthenticationPrincipal UserDetailsImpl principal){
        if (grade.getUser()!=null){
            Teacher teacher = new Teacher();
            teacher.setId(userRepository.findByUsername(principal.getUsername()).get().getId());
            grade.setTeacher(teacher);
            gradesRepository.save(grade);
            attributes.addFlashAttribute("mark_given", "You have succesfully given a mark to a student");
        } else {
            attributes.addFlashAttribute("giving_mark_fail", "Select the student you want to give a mark!");
        }
        return "redirect:/give_mark";
    }
}



