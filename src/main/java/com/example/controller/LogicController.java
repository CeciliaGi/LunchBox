package com.example.controller;

import com.example.domain.LunchBox;
import com.example.domain.Person;
import com.example.domain.User;
import com.example.repository.Repository;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.naming.Binding;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Controller
public class LogicController {
    ArrayList<User> users;
    ArrayList<Person> persons;
    ArrayList<LunchBox> lunchBoxes;
    String lunchBoxesJson;



    @Autowired
    Repository repository;

    @PostConstruct
    public void RefreshUsers() {
        users = (ArrayList<User>) repository.getUsers();
    }
    @PostConstruct
    public void RefreshPersons() {
        persons = (ArrayList<Person>) repository.getPersons();
    }
    @PostConstruct
    public void RefresshLunchBoxes() {
        lunchBoxes = (ArrayList<LunchBox>) repository.getLunchBoxes();
        lunchBoxesJson = objectToJSON(lunchBoxes);

    }


    @PostMapping("/login")
    public ModelAndView getUserLogin(@RequestParam String userName, HttpSession session, @RequestParam String password) throws Exception {
        for (User index : users) {
            if((userName.equals(index.getUserName()) && (password.equals(index.getPassword())))) {
                return new ModelAndView("Adam");

            }

        }
        return null;
    }

    @GetMapping("/newUser")
    public ModelAndView form() {

        User user = new User("", "", "");
        Person person = new Person("", "", "");
        ModelAndView mv = new ModelAndView("signUp");
        mv.addObject("user",user);
        mv.addObject("person",person);
        mv.addObject("lunchBoxes", lunchBoxesJson);

        return mv;
}

    @PostMapping("/newUser")
    public ModelAndView newUser(@Valid User user, BindingResult br, @ModelAttribute Person person) throws Exception {

        System.out.println(person.getFirstName());
        if (br.hasErrors() || userNameDuplicate(user)) {
            return new ModelAndView("signUp");
        }

        int key = Integer.parseInt(repository.addUser(user, person));
        users.add(new User(key, user.getUserName(), user.getPassword(), user.getMail()));
        persons.add(new Person(key, person.getFirstName(), person.getLastName(), person.getPhoneNumber()));
        return new ModelAndView("Adam");
    }


    public boolean userNameDuplicate(User user) {
        boolean duplicate = false;

        for(User index : users) {
            if(index.getUserName().equals(user.getUserName())){
                duplicate = true;
                return duplicate;
            }
        }return duplicate;
    }


    public String objectToJSON(ArrayList<LunchBox> array) {
        ObjectMapper mapper  = new ObjectMapper();
        String jsonInString = "[";
            for(int i = 0; i<array.size(); i++) {
                try {
                    jsonInString += mapper.writeValueAsString(array.get(i));
                    if(i<array.size()-1) {
                        jsonInString += ",";
                    }

                }
                catch(JsonGenerationException e) {
                    e.printStackTrace();
                }
                catch(JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
            jsonInString += "]";
        return jsonInString;
    }


}
