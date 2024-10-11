package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.CategoryModel;
import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.model.TagModel;
import com.shopFinal.shopFinal.service.InMemoryCategoryService;
import com.shopFinal.shopFinal.service.InMemoryItemService;
import com.shopFinal.shopFinal.service.InMemoryTagService;
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
@RequestMapping("/tags")
public class TagController {
    @Autowired
    public InMemoryTagService tagService;
    @Autowired
    public InMemoryItemService itemService;

    @Autowired
    public InMemoryCategoryService categoryService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllTags(Model model){
        // List<TagModel> tags = tagService.findAll();
        // for (TagModel tag : tags) {
        //     // Здесь должна быть логика, чтобы наполнить tag.items
        //     tag.setItems(itemService.findByTag(tag.getId())); // Замените этот метод на правильный
        // }
        model.addAttribute("tags", tagService.findAll());
        System.out.println(categoryService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("category", new CategoryModel());
        model.addAttribute("tag", new TagModel());
        model.addAttribute("userRole", getRole());
        return "tagList";
    }

    @GetMapping("all/test")
    public String getAllTestTags(){
        List<TagModel> list = tagService.findAll();
        for(var tag: list){
            System.out.println(tag.getId());
        }
        return "tagList";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getTagById(@PathVariable("id") UUID id, Model model){
        model.addAttribute("tags", tagService.findById(id));
        model.addAttribute("tag", new TagModel());
        return "tagList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String addTag(@Valid @ModelAttribute("tag") TagModel tagModel, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("tags", tagService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            return "tagList";
        }
        tagService.createNote(tagModel);
        return "redirect:/tags/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/update")
    public String updateTag(@Valid @ModelAttribute("tag") TagModel tagModel, BindingResult result){
        
        if (tagModel.getCategory() != null) {
            CategoryModel existingCategory = categoryService.findById(tagModel.getCategory().getId());
            if (existingCategory != null) {
                System.out.println("Категория найдена и устанавливается: " + existingCategory);
                tagModel.setCategory(existingCategory);
            } else {
                System.out.println("Категория не найдена. " + tagModel);
            }
        } else {
            System.out.println("Категория не установлена. Тег остается без категории." + tagModel);
            return "redirect:/tags/all";
        }
        tagService.updateNote(tagModel, tagModel.getId());
        return "redirect:/tags/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteTag(@RequestParam UUID id){
        tagService.deleteNote(id);
        return "redirect:/tags/all";
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
