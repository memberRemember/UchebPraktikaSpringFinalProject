package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.model.ItemTagModel;
import com.shopFinal.shopFinal.model.TagModel;
import com.shopFinal.shopFinal.model.TypeModel;
import com.shopFinal.shopFinal.service.InMemoryItemService;
import com.shopFinal.shopFinal.service.InMemoryItemTagService;
import com.shopFinal.shopFinal.service.InMemoryTagService;
import com.shopFinal.shopFinal.service.InMemoryTypeService;

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
@RequestMapping("/items")
public class ItemController {
    @Autowired
    public InMemoryItemService itemService;

    @Autowired
    public InMemoryTagService tagService;

    @Autowired
    public InMemoryTypeService typeService;

    @Autowired
    public InMemoryItemTagService itemTagService;


    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllItems(Model model){
        List<ItemModel> items = itemService.findAll();
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("types", typeService.findAll());
        model.addAttribute("type", new TypeModel());
        model.addAttribute("item", new ItemModel());
        model.addAttribute("userRole", getRole());
        for (ItemModel item : items) {
            System.out.println(item);
        }
        return "itemList";
    }

    @GetMapping("all/test")
    public String getAllTestItems(){
        List<ItemModel> list = itemService.findAll();
        for(var item: list){
            System.out.println(item.getId());
        }
        return "itemList";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getItemById(@PathVariable("id") UUID id, Model model){
        model.addAttribute("items", itemService.findById(id));
        model.addAttribute("item", new ItemModel());
        return "itemList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String addItem(@Valid @ModelAttribute("item") ItemModel itemModel, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("items", itemService.findAll());
            model.addAttribute("types", typeService.findAll());
            return "itemList";
        }
        itemService.createNote(itemModel);
        return "redirect:/items/all";
    }

    // @PostMapping("/add")
    // public String addItem(@Valid @ModelAttribute("item") ItemModel itemModel, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         model.addAttribute("items", itemService.findAll());
    //         model.addAttribute("tags", tagService.findAll());
    //         model.addAttribute("types", typeService.findAll());
    //         return "itemList";
    //     }
    //     itemService.createNote(itemModel);
        
    //     if (itemModel.getTags() != null) {
    //         for (TagModel tag : itemModel.getTags()) {
    //             ItemTagModel itemTag = new ItemTagModel();
    //             itemTag.setItem(itemModel); 
    //             itemTag.setTag(tag);
    //             itemTagService.createNote(itemTag); 
    //         }
    //     }

    //     return "redirect:/items/all";
    // }
    
    
    // @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    // @PostMapping("/update")
    // public String updateItem(@Valid @ModelAttribute("item") ItemModel itemModel, BindingResult result){
        //     itemService.updateNote(itemModel, itemModel.getId());
        //     return "redirect:/items/all";
        // }
        
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/update")
    public String updateItem(@Valid @ModelAttribute("item") ItemModel itemModel, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/items/all";
        }
    
        if (itemModel.getType() != null && itemModel.getType().getId() != null) {
            TypeModel existingType = typeService.findById(itemModel.getType().getId());
            System.out.println("Тип найден и устанавливается: " + existingType);
            itemModel.setType(existingType);
        } else {
            System.out.println("Тип не установлен или отсутствует ID");
            return "redirect:/items/all";
        }
    
        itemService.updateNote(itemModel, itemModel.getId());
        return "redirect:/items/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteItem(@RequestParam UUID id){
        itemService.deleteNote(id);
        return "redirect:/items/all";
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
