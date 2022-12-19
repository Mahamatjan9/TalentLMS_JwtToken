//package com.example.spring_boot_restcontroller.converter.user;
//
//import com.example.spring_boot_restcontroller.dto.request.UserRequest;
//import com.example.spring_boot_restcontroller.entity.User;
//import org.springframework.stereotype.Component;
//
//@Component
//public class UserConverterRequest {
//
//    public User create(UserRequest userRequest){
//        User user = new User();
//        user.setEmail(userRequest.getEmail());
//        user.setFirstName(userRequest.getFirstName());
//        user.setPassword(userRequest.getPassword());
//        return user;
//    }
//
//    public  void  update(User user, UserRequest userRequest){
//        if(userRequest.getEmail() != null)
//            user.setEmail(userRequest.getEmail());
//        if (userRequest.getPassword() != null)
//            user.setPassword(userRequest.getPassword());
//        if (userRequest.getFirstName() != null)
//            user.setFirstName(userRequest.getFirstName());
//    }
//}
