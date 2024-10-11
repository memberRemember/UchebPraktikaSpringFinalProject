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
@RequestMapping("/itemtags")
public class ItemTagController {
    @Autowired
    public InMemoryItemTagService itemTagService;

    @Autowired
    public InMemoryTagService tagService;

    @Autowired
    public InMemoryItemService itemService;

    
    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllItemTags(Model model){
        List<ItemTagModel> itemTags = itemTagService.findAll();

        model.addAttribute("itemTags", itemTagService.findAll());
        model.addAttribute("tags", tagService.findAll());
        model.addAttribute("items", itemService.findAll());
        model.addAttribute("item", new ItemModel());
        model.addAttribute("itemTag", new ItemTagModel());
        model.addAttribute("userRole", getRole());

        for (ItemTagModel itemTag : itemTags) {
            System.out.println(itemTag);
        }
        return "itemTagList";
    }

    @GetMapping("all/test")
    public String getAllTestItemTags(){
        List<ItemTagModel> list = itemTagService.findAll();
        for(var itemTag: list){
            System.out.println(itemTag.getId());
        }
        return "itemTagList";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getItemTagById(@PathVariable("id") UUID id, Model model){
        model.addAttribute("itemTags", itemTagService.findById(id));
        model.addAttribute("itemTag", new ItemTagModel());
        return "itemTagList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String addItemTag(@Valid @ModelAttribute("itemTag") ItemTagModel itemTagModel, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("itemTags", itemTagService.findAll());
            model.addAttribute("items", itemService.findAll());
            model.addAttribute("tags", tagService.findAll());
            return "itemTagList";
        }
        itemTagService.createNote(itemTagModel);
        return "redirect:/itemtags/all";
    }

    // @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    // @PostMapping("/add")
    // public String addItemTag(@Valid @ModelAttribute("itemTag") ItemTagModel itemTagModel, BindingResult result, Model model) {
    //     if (result.hasErrors()) {
    //         model.addAttribute("itemTags", itemTagService.findAll());
    //         model.addAttribute("tags", tagService.findAll());
    //         model.addAttribute("types", typeService.findAll());
    //         return "itemTagList";
    //     }
    //     itemTagService.createNote(itemTagModel);
        
    //     if (itemTagModel.getTags() != null) {
    //         for (TagModel tag : itemTagModel.getTags()) {
    //             ItemTagTagModel itemTagTag = new ItemTagTagModel();
    //             itemTagTag.setItemTag(itemTagModel); 
    //             itemTagTag.setTag(tag);
    //             itemTagTagService.createNote(itemTagTag); 
    //         }
    //     }

    //     return "redirect:/itemTags/all";
    // }


    // @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    // @PostMapping("/update")
    // public String updateItemTag(@Valid @ModelAttribute("itemTag") ItemTagModel itemTagModel, BindingResult result){
    //     itemTagService.updateNote(itemTagModel, itemTagModel.getId());
    //     return "redirect:/itemTags/all";
    // }

    @PostMapping("/update")
    public String updateItemTag(@Valid @ModelAttribute("itemTag") ItemTagModel itemTagModel, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/itemTags/all";
        }
    
        if (itemTagModel.getItem() != null && itemTagModel.getTag() != null) {
            ItemModel existingItem = itemService.findById(itemTagModel.getItem().getId());
            TagModel existingTag = tagService.findById(itemTagModel.getTag().getId());

            System.out.println("Предмет найден и устанавливается: " + existingItem);
            System.out.println("Тег найден и устанавливается: " + existingTag);
            
            itemTagModel.setItem(existingItem);
            itemTagModel.setTag(existingTag);
        } else {
            System.out.println("Тег или предмет не установлен \n" + itemTagModel);

            return "redirect:/itemtags/all"; 
        }
    
        itemTagService.updateNote(itemTagModel, itemTagModel.getId());
        return "redirect:/itemtags/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteItemTag(@RequestParam UUID id){
        itemTagService.deleteNote(id);
        return "redirect:/itemtags/all";
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
