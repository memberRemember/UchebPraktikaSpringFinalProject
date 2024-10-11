package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.CategoryModel;
import com.shopFinal.shopFinal.service.InMemoryCategoryService;
import com.shopFinal.shopFinal.service.InMemoryItemService;
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
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    public InMemoryCategoryService categoryService;
    @Autowired
    public InMemoryItemService itemService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllCategories(Model model){
        // List<CategoryModel> categories = categoryService.findAll();
        // for (CategoryModel category : categories) {
        //     // Здесь должна быть логика, чтобы наполнить category.items
        //     category.setItems(itemService.findByCategory(category.getId())); // Замените этот метод на правильный
        // }
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("userRole", getRole());

        model.addAttribute("category", new CategoryModel());
        return "categoryList";
    }

    @GetMapping("all/test")
    public String getAllTestCategories(){
        List<CategoryModel> list = categoryService.findAll();
        for(var category: list){
            System.out.println(category.getId());
        }
        return "categoryList";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getCategoryById(@PathVariable("id") UUID id, Model model){
        model.addAttribute("categories", categoryService.findById(id));
        model.addAttribute("category", new CategoryModel());
        return "categoryList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String addCategory(@Valid @ModelAttribute("category") CategoryModel categoryModel, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("categories", categoryService.findAll());
            return "categoryList";
        }
        categoryService.createNote(categoryModel);
        return "redirect:/categories/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/update")
    public String updateCategory(@Valid @ModelAttribute("category") CategoryModel categoryModel, BindingResult result){
        categoryService.updateNote(categoryModel, categoryModel.getId());
        return "redirect:/categories/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteCategory(@RequestParam UUID id){
        categoryService.deleteNote(id);
        return "redirect:/categories/all";
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
