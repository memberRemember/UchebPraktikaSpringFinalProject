package com.shopFinal.shopFinal.controllers;

import com.shopFinal.shopFinal.model.TypeModel;
import com.shopFinal.shopFinal.service.InMemoryTypeService;
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
@RequestMapping("/types")
public class TypeController {
    @Autowired
    public InMemoryTypeService typeService;
    @Autowired
    public InMemoryItemService itemService;

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all")
    public String getAllTypes(Model model){
        // List<TypeModel> types = typeService.findAll();
        // for (TypeModel type : types) {
        //     // Здесь должна быть логика, чтобы наполнить type.items
        //     type.setItems(itemService.findByType(type.getId())); // Замените этот метод на правильный
        // }
        model.addAttribute("types", typeService.findAll());

        model.addAttribute("userRole", getRole());
        model.addAttribute("type", new TypeModel());
        return "typeList";
    }

    @GetMapping("all/test")
    public String getAllTestTypes(){
        List<TypeModel> list = typeService.findAll();
        for(var type: list){
            System.out.println(type.getId());
        }
        return "typeList";
    }


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER', 'USER')")
    @GetMapping("/all/{id}")
    public String getTypeById(@PathVariable("id") UUID id, Model model){
        model.addAttribute("types", typeService.findById(id));
        model.addAttribute("type", new TypeModel());
        return "typeList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String addType(@Valid @ModelAttribute("type") TypeModel typeModel, BindingResult result, Model model){
        if(result.hasErrors()){
            model.addAttribute("types", typeService.findAll());
            return "typeList";
        }
        typeService.createNote(typeModel);
        return "redirect:/types/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/update")
    public String updateType(@Valid @ModelAttribute("type") TypeModel typeModel, BindingResult result){
        typeService.updateNote(typeModel, typeModel.getId());
        return "redirect:/types/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteType(@RequestParam UUID id){
        typeService.deleteNote(id);
        return "redirect:/types/all";
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
