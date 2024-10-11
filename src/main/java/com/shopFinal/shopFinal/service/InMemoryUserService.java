package com.shopFinal.shopFinal.service;

import com.shopFinal.shopFinal.model.UserModel;
import com.shopFinal.shopFinal.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class InMemoryUserService extends InMemoryAbstractService<UserModel, UUID, UserRepository> {
    
    private final UserRepository userRepository;

    public InMemoryUserService(UserRepository jpaRepository, UserRepository repository) {
        super(jpaRepository);
        this.userRepository = repository;
    }

    public UserModel updateUser(UserModel model, UUID id) {
        if(userRepository.existsById(model.getId())){
            return userRepository.save(model);
        }
        return null;
    }


    public List<UserModel> findUsersByParam(String param, String value) {
        switch (param){
            case "username":
                return userRepository.findAll().stream().filter(userModel -> userModel.getUsername().equals(value) && !userModel.isDeleted()).collect(Collectors.toList());
            case "password":
                return userRepository.findAll().stream().filter(userModel -> userModel.getPassword().equals(value) && !userModel.isDeleted()).collect(Collectors.toList());
            case "balance":
                return userRepository.findAll().stream().filter(userModel -> String.valueOf(userModel.getBalance()).contains(value) && !userModel.isDeleted()).collect(Collectors.toList());
            default:
                return null;
        }
    }

    // public List<UserModel> filterUsers(String when, String time, String gender) {

    //     DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //     DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    //     LocalDateTime dateTime = LocalDateTime.parse(time, inputFormatter);
    //     DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    //     String outputDate = dateTime.format(outputFormatter);

    //     LocalDate formattedData = !time.isEmpty() ? LocalDate.parse(outputDate,formatter) : null;

    //     return userRepository.findAll().stream().filter(userModel -> {
    //         boolean isGender = gender.equals("none") || userModel.getGender().equals(gender);
    //         if(when.isEmpty() || time.isEmpty()){
    //             return isGender;
    //         }else{
    //             return isGender && when.equals("before") ? LocalDate.parse(userModel.getDateCreate(),formatter).isBefore(formattedData) :
    //                     LocalDate.parse(userModel.getDateCreate(), formatter).isAfter(formattedData);
    //         }



    //     }).collect(Collectors.toList());
    // }

    public void softDeleteUser(UUID id) {
        UserModel tempUser = userRepository.findById(id).orElse(null);
        tempUser.setDeleted(false);
        userRepository.save(tempUser);
    }

    public List<UserModel> paginUsers(int page, int size) {
        int start = page * size;


        if(start >= userRepository.findAll().stream().filter(userModel -> !userModel.isDeleted()).toList().size()){
            return new ArrayList<>();
        }

        int end = Math.min(start + size, userRepository.findAll().stream().filter(userModel -> !userModel.isDeleted()).toList().size());
        return userRepository.findAll().stream().filter(userModel -> !userModel.isDeleted()).toList().subList(start, end);
    }


    public int getSizePaginUsers() {
        return userRepository.findAll().stream().filter(userModel -> !userModel.isDeleted()).toList().size();
    }
}
