package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.AssortmentModel;
import com.shopFinal.shopFinal.repository.AssortmentRepository;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InMemoryAssortmentService extends InMemoryAbstractService<AssortmentModel, UUID, AssortmentRepository> {
    
    private final AssortmentRepository assortmentRepository;

    public InMemoryAssortmentService(AssortmentRepository jpaRepository, AssortmentRepository repository) {
        super(jpaRepository);
        this.assortmentRepository = repository;
    }

    public AssortmentModel updateAssortment(AssortmentModel model, UUID id) {
        if(assortmentRepository.existsById(model.getId())){
            return assortmentRepository.save(model);
        }
        return null;
    }


    public List<AssortmentModel> findAssortmentsByParam(String param, String value) {
        switch (param){
            case "quantity":
                return assortmentRepository.findAll().stream().filter(assortmentModel -> String.valueOf(assortmentModel.getQuantity()).contains(value)).collect(Collectors.toList());
            case "price":
                return assortmentRepository.findAll().stream().filter(assortmentModel -> String.valueOf(assortmentModel.getPrice()).contains(value)).collect(Collectors.toList());
            default:
                return null;
        }
    }

    // public List<AssortmentModel> filterAssortments(String when, String time, String gender) {

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //     DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    //     LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);
    //     DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //     String outputDate = dateTime.format(outputFormatter);

    //     LocalDate formattedData = !time.isEmpty() ? LocalDate.parse(outputDate,formatter) : null;

    //     return assortmentRepository.findAll().stream().filter(assortmentModel -> {
    //         boolean isGender = gender.equals("none") || assortmentModel.getGender().equals(gender);
    //         if(when.isEmpty() || time.isEmpty()){
    //             return isGender;
    //         }else{
    //             return isGender && when.equals("before") ? LocalDate.parse(assortmentModel.getDateCreate(),formatter).isBefore(formattedData) :
    //                     LocalDate.parse(assortmentModel.getDateCreate(), formatter).isAfter(formattedData);
    //         }



    //     }).collect(Collectors.toList());
    // }

    public List<AssortmentModel> paginAssortments(int page, int size) {
        int start = page * size;


        if(start >= assortmentRepository.findAll().stream().toList().size()){
            return new ArrayList<>();
        }

        int end = Math.min(start + size, assortmentRepository.findAll().stream().toList().size());
        return assortmentRepository.findAll().stream().toList().subList(start, end);
    }


    public int getSizePaginAssortments() {
        return assortmentRepository.findAll().stream().toList().size();
    }
}
