package tj.alimov.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tj.alimov.userservice.dto.AuthenticationResponseDto;
import tj.alimov.userservice.model.User;
import tj.alimov.userservice.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    @GetMapping("")
    public String getString(){
        String username = (String)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println("Current user is " + username);
        return "Hello from user";
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> registerUser(@RequestBody User user){
        AuthenticationResponseDto responseDto = userService.registerUser(user);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
