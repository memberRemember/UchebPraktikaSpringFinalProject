package com.shopFinal.shopFinal.controllers;
import com.shopFinal.shopFinal.model.AssortmentModel;
import com.shopFinal.shopFinal.model.ItemModel;
import com.shopFinal.shopFinal.model.ItemTagModel;
import com.shopFinal.shopFinal.model.OrderModel;
import com.shopFinal.shopFinal.service.InMemoryAbstractService;
import com.shopFinal.shopFinal.service.InMemoryAssortmentService;
import com.shopFinal.shopFinal.service.InMemoryItemTagService;
import com.shopFinal.shopFinal.service.InMemoryOrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/assortments")
public class AssortmentController {

    @Autowired
    public InMemoryAssortmentService assortmentService;

    @Autowired
    public InMemoryItemTagService itemTagService;

    @Autowired
    public InMemoryOrderService orderService;


    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/all")
    public String findAllAssortments(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size,
                                 Model model) {
        model.addAttribute("assortments", assortmentService.paginAssortments(page, size));
        model.addAttribute("page", page);
        model.addAttribute("count", assortmentService.getSizePaginAssortments());
        model.addAttribute("assortment", new AssortmentModel());
        model.addAttribute("orders", orderService.findAll());
        model.addAttribute("order", new OrderModel());
        model.addAttribute("itemTags", itemTagService.findAll());
        model.addAttribute("itemTag", new ItemTagModel());
        model.addAttribute("userRole", getRole());
        return "assortmentList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @GetMapping("/all/{id}")
    public String findAssortmentById(@PathVariable("id") UUID id, Model model) {
        model.addAttribute("assortments", assortmentService.findById(id));
        model.addAttribute("page", 0);
        model.addAttribute("count", 0);
        model.addAttribute("assortment", new AssortmentModel());
        return "assortmentList";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @PostMapping("/add")
    public String createAssortment(@Valid @ModelAttribute("assortment") AssortmentModel assortmentModel, BindingResult result, Model model) {
        if(result.hasErrors()){
            model.addAttribute("assortments", assortmentService.paginAssortments(0, 0));
            model.addAttribute("page", 0);
            model.addAttribute("count", assortmentService.getSizePaginAssortments());
            return "assortmentList";
        }
        assortmentService.createNote(assortmentModel);
        return "redirect:/assortments/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/update")
    public String updateCreate(@Valid @ModelAttribute("assortment") AssortmentModel assortmentModel, @RequestParam(value = "assortment.order", required = false) List<OrderModel> orderList, BindingResult result) {
        assortmentModel.setOrders(orderList);
        assortmentService.updateAssortment(assortmentModel, assortmentModel.getId());
        return "redirect:/assortments/all";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/delete")
    public String deleteAssortment(@RequestParam UUID id){
        assortmentService.deleteNote(id);
        return "redirect:/assortments/all";
    }

    @PostMapping("/search")
    public String findAssortmentsByParam(@RequestParam String param, @RequestParam String value, Model model){
        model.addAttribute("assortments", assortmentService.findAssortmentsByParam(param, value));
        return "assortmentsFilterPage";
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
    @PostMapping("/deleteAll")
    public String allDeleteUsers(@RequestParam List<UUID> assortmentIds){
        for(var i : assortmentIds){
            assortmentService.deleteNote(i);
            System.out.println(i);
        }
        return "redirect:/assortments/all";
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

//    @PostMapping("/filter")
//     public String filerAssortments(@RequestParam String when,
//                                @RequestParam String time,
//                                @RequestParam String gender, Model model){

//         model.addAttribute("assortments", assortmentService.filterAssortments(when, time, gender));
//         return "assortmentsFilterPage";
//    }

//    @PostMapping("/softdelete")
//    public String softDeleteAssortment(@RequestParam UUID id) {
//        assortmentService.softDeleteAssortment(id);
//        return "redirect:/assortments/all";
//    }


//     @PostMapping("/softDelete")
//     public String allDeleteAssortments(@RequestParam List<UUID> assortmentIds){
//         for(var i : assortmentIds){
//             assortmentService.softDeleteAssortment(i);
//             System.out.println(i);
//         }
//         return "redirect:/assortments/all";
//     }
}
