package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.AssortmentModel;
import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.model.OrderModel;
import com.shopFinal.shopFinal.model.UserModel;
import com.shopFinal.shopFinal.service.InMemoryAssortmentService;
import com.shopFinal.shopFinal.service.InMemoryItemService;
import com.shopFinal.shopFinal.service.InMemoryOrderService;
import com.shopFinal.shopFinal.service.InMemoryUserService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    public InMemoryOrderService orderService;

    @Autowired
    public InMemoryItemService itemService;

    @Autowired
    public InMemoryAssortmentService assortmentService;
    
    @Autowired
    public InMemoryUserService userService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("order", new OrderModel());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("users", userService.findAll());
        model.addAttribute("assortments", assortmentService.findAll());
        model.addAttribute("assortment", new AssortmentModel());
        model.addAttribute("user", new UserModel());
        model.addAttribute("userRole", getRole());
        
        return "orderList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @PostMapping("/add")
    public String addOrder(@Valid @ModelAttribute("order") OrderModel order, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("orders", orderService.findAll());
            model.addAttribute("assortments", assortmentService.findAll());
            return "orderList";
        }
        order.setTotalPrice(order.calculateTotalPrice());
        orderService.createNote(order);
        return "redirect:/orders/all";

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/update")
    public String updateOrder(@Valid @ModelAttribute("order") OrderModel order, @RequestParam("order.assortments") List<AssortmentModel> itemList, BindingResult result) {
        order.setAssortments(itemList);
        order.setTotalPrice(order.calculateTotalPrice()); 
        orderService.updateNote(order, order.getId());
        return "redirect:/orders/all";

    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteOrder(@RequestParam UUID id) {
        orderService.deleteNote(id);
        return "redirect:/orders/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getIdOrder(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("orders", orderService.findById(id));
        model.addAttribute("order", new OrderModel());
        model.addAttribute("items", itemService.findAll());
        return "orderList";
    }

    private String getRole(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userRole = "";

        if (authentication != null && authentication.isAuthenticated()) {

            userRole = authentication.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .findFirst()
                    .orElse(null);
        }

        return userRole;
    }
}
