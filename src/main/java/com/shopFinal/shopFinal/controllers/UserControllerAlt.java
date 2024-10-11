package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.UserModel;
import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.model.OrderModel;
import com.shopFinal.shopFinal.service.InMemoryUserService;
import com.shopFinal.shopFinal.service.InMemoryOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserControllerAlt {

    @Autowired
    public InMemoryUserService userService;

    @Autowired
    public InMemoryOrderService orderService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/all")
    public String findAllUsers(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        model.addAttribute("users", userService.paginUsers(page, size));
        model.addAttribute("page", page);
        model.addAttribute("count", userService.getSizePaginUsers());
        model.addAttribute("user", new UserModel());
        model.addAttribute("orders", orderService.findAll());
        return "usersList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/all/{id}")
    public String findUserById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("users", userService.findById(id));
        model.addAttribute("page", 0);
        model.addAttribute("count", 0);
        model.addAttribute("user", new UserModel());
        return "usersList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String createUser(@Valid @ModelAttribute("user") UserModel userModel, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("users", userService.paginUsers(0, 0));
            model.addAttribute("page", 0);
            model.addAttribute("count", userService.getSizePaginUsers());
            return "usersList";
        }
        userService.createNote(userModel);
        return "redirect:/users/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/update")
    public String updateCreate(@Valid @ModelAttribute("user") UserModel userModel, @RequestParam(value = "user.order", required = false) List<OrderModel> orderList, BindingResult result) {
        userModel.setOrders(orderList);
        userService.updateUser(userModel, userModel.getId());
        return "redirect:/users/all";
    }

   @PostMapping("/search")
    public String findUsersByParam(@RequestParam String param, @RequestParam String value, Model model){
        model.addAttribute("users", userService.findUsersByParam(param, value));
        return "usersFilterPage";
   }

//    @PostMapping("/filter")
//     public String filerUsers(@RequestParam String when,
//                                @RequestParam String time,
//                                @RequestParam String gender, Model model){

//         model.addAttribute("users", userService.filterUsers(when, time, gender));
//         return "usersFilterPage";
//    }

   @PostMapping("/softdelete")
   public String softDeleteUser(@RequestParam UUID id) {
       userService.softDeleteUser(id);
       return "redirect:/users/all";
   }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/deleteAll")
    public String allDeleteUsers(@RequestParam List<UUID> userIds){
        for(var i : userIds){
            userService.softDeleteUser(i);
            System.out.println(i);
        }
        return "redirect:/users/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteUser(@RequestParam UUID id){
        userService.deleteNote(id);
        return "redirect:/users/all";
    }
}
