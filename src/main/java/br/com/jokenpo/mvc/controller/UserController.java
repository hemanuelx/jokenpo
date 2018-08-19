package br.com.jokenpo.mvc.controller;

import br.com.jokenpo.mvc.entity.User;
import br.com.jokenpo.mvc.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getIndex() {
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String postUser(String userName, Model model) {
        User user = new User();
        user.setId(2l);
        user.setUserName(userName);
        userRepository.saveAndFlush(user);
        model.addAttribute("userName", userName);
        return "index2";
    }
}
