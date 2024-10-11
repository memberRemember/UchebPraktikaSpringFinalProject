// package com.shopFinal.shopFinal.controllers;

// import com.shopFinal.shopFinal.model.AssortmentModel;
// import com.shopFinal.shopFinal.model.ItemModel;
// import com.shopFinal.shopFinal.model.OrderModel;
// import com.shopFinal.shopFinal.service.InMemoryAssortmentService;
// import com.shopFinal.shopFinal.service.InMemoryItemService;
// import com.shopFinal.shopFinal.service.InMemoryOrderService;
// import jakarta.validation.Valid;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.access.prepost.PreAuthorize;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.validation.BindingResult;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.UUID;

// @Controller
// @RequestMapping("/assortments")
// public class AssortmentControllerOld {
//     @Autowired
//     public InMemoryAssortmentService assortmentService;



//     @PreAuthorize("hasAnyAuthority('ADMIN', 'EMPLOYEE', 'MANAGER')")
//     @GetMapping("/all")
//     public String getAllAssortments(Model model) {
//         model.addAttribute("assortments", assortmentService.findAll());
//         model.addAttribute("assortment", new AssortmentModel());
//         return "assortmentList";
//     }

//     @PostMapping("/add")
//     public String addAssortment(@Valid @ModelAttribute("assortment") AssortmentModel assortment, BindingResult result, Model model) {
//         if(result.hasErrors()){
//             model.addAttribute("assortments", assortmentService.findAll());
//             return "assortmentList";
//         }
//         assortmentService.createNote(assortment);
//         return "redirect:/assortments/all";

//     }

//     @PostMapping("/update")
//     public String updateAssortment(@Valid @ModelAttribute("assortment") AssortmentModel assortment, BindingResult result) {
//         assortmentService.updateNote(assortment, assortment.getId());
//         return "redirect:/assortments/all";

//     }

//     @PreAuthorize("hasAnyAuthority('ADMIN', 'MANAGER')")
//     @PostMapping("/delete")
//     public String deleteAssortment(@RequestParam UUID id){
//         assortmentService.deleteNote(id);
//         return "redirect:/assortments/all";
//     }

//     @GetMapping("/all/{id}")
//     public String getIdAssortment(@PathVariable("id") UUID id, Model model){
//         model.addAttribute("assortments", assortmentService.findById(id));
//         model.addAttribute("assortment", new AssortmentModel());
//         return "assortmentList";
//     }

// }
