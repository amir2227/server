package com.pardis.server.controllers;

import java.lang.reflect.Array;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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

import com.pardis.server.bootstrap.SpringJpaBootstrap;
import com.pardis.server.domain.User;
 
@Controller
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
 		model.addAttribute("members",userRepo.findAll(new PageRequest(page, 50)));
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
   
   @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
   @RequestMapping(value = "/giveRole", method = RequestMethod.POST)
   public String giveRole(@RequestParam("role") String username) {
	   
	   if(username.equalsIgnoreCase("ADMIN"))
		   SpringJpaBootstrap.assignUsersToAdminRole(username);
	   
	   else if(username.equalsIgnoreCase("USER"))
		   SpringJpaBootstrap.assignUsersToUserRole(username);
		   
	   return "redirect:/members";

   }
 
   @PreAuthorize("hasRole('ROLE_ADMIN')")
   @RequestMapping(value = "/giveRoleUser", method = RequestMethod.POST)
   public String giveRoleUser(@RequestParam("role") String username) {
	   
	    if(username.equalsIgnoreCase("USER"))
		   SpringJpaBootstrap.assignUsersToUserRole(username);
		   
	   return "redirect:/members";

   }
}