package com.pardis.server.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
// import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pardis.server.dao.UserDAO;
import com.pardis.server.model.User;
import com.pardis.server.validator.UserValidator;
 
@Controller
public class MainController {
 
   @Autowired
   private UserDAO UserDAO;
 
   @Autowired
   private UserRepo userRepo;
 
 
   @Autowired
   private UserValidator UserValidator;
 
   // Set a form validator
   @InitBinder
   protected void initBinder(WebDataBinder dataBinder) {
      // Form target
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == User.class) {
         dataBinder.setValidator(UserValidator);
      }
      // ...
   }
 
 
 
   @RequestMapping("/members")
   public String viewMembers(Model model,@RequestParam(defaultValue="0") int page){
 		//data have devices information
 		model.addAttribute("members",userRepo.findAll(new PageRequest(page, 4)));
 		model.addAttribute("currentPage", page);
 		 
 		 return "membersPage";
     }
   
 
   @RequestMapping("/registerSuccessful")
   public String viewRegisterSuccessful(Model model) {
 
      return "registerSuccessfulPage";
   }
 
   // Show Register page.
   @RequestMapping(value = "/register", method = RequestMethod.GET)
   public String viewRegister(Model model) {
 
      User user = new User();
 
      model.addAttribute("User", user);
 
      return "registerPage";
   }
 
   // This method is called to save the registration information.
   // @Validated: To ensure that this Form
   // has been Validated before this method is invoked.
   @RequestMapping(value = "/register", method = RequestMethod.POST)
   public String saveRegister(Model model, //
         @ModelAttribute("User") @Validated User User, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
	   System.out.println(User.getFirstName());
      User newUser= null;
      try {
         newUser = UserDAO.createUser(User);
      }
      // Other error!!
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
      
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }
   @RequestMapping(value = "/updateUser", method = RequestMethod.POST)
   public String updateRegister(Model model, //
         @ModelAttribute("User") @Validated User User, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
     
      User newUser= null;
      try {
         newUser = UserDAO.createUser(User);
      }
      // Other error!!
      catch (Exception e) {
         model.addAttribute("errorMessage", "Error: " + e.getMessage());
         return "registerPage";
      }
      
 
      redirectAttributes.addFlashAttribute("flashUser", newUser);
       
      return "redirect:/registerSuccessful";
   }
 
}
