package net.javaguides.springboot.controller;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDto;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exception.ErrorDetails;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    //Build create user rest Api.

    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid  @RequestBody UserDto user){
    UserDto savedUser = userService.createUser(user);
    return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    ///build get user by id rest api

    @GetMapping("{id}")
    //http://localhost:8080/api/users/1
    public ResponseEntity<UserDto> getUserById (@PathVariable("id") Long userId){
       UserDto user =  userService.getUserById(userId);
       return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //build get all users rest api

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(){
      List<UserDto> users = userService.getAllUsers();
      return  new ResponseEntity<>(users, HttpStatus.OK);
    }

    //build update user rest api

    @PutMapping("{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId, @RequestBody @Valid UserDto user)
    {
        user.setId(userId);
      UserDto updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //build delete user rest api
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId){
        userService.deleteuser(userId);
        return  new ResponseEntity<>("user succesfully deleted", HttpStatus.OK);
    }

//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest){
//        ErrorDetails errorDetails = new ErrorDetails(
//                LocalDateTime.now(),
//                exception.getMessage(),
//                webRequest.getDescription(false),
//                "user not found"
//
//        );
//
//        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
//    }

}
