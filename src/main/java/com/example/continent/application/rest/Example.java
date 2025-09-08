//package com.example.continent.application.rest;
//
//import com.example.continent.application.domain.model.User;
//import com.example.continent.application.domain.repository.UserRepository;
//import com.example.continent.application.domain.service.UserService;
//import com.example.continent.application.dto.UserDto;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Optional;
//
//
//@RestController
//@RequestMapping("/test")
//@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
//@RequiredArgsConstructor
//public class Example {
//
//    UserService userService;
//
//    @GetMapping()
//    public ResponseEntity<User> findById(@RequestParam("username") String username){
//      return  ResponseEntity.ok(userService.getByUsername(username));
//    }
//}
