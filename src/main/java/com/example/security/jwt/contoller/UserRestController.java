package com.example.security.jwt.contoller;

import com.example.security.jwt.entity.User;
import com.example.security.jwt.entity.UserRequest;
import com.example.security.jwt.entity.UserResponse;
import com.example.security.jwt.service.IUserService;
import com.example.security.jwt.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserRestController {
    @Autowired
    private IUserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;



    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody User user) {

        Integer id = userService.saveUser(user);
        System.out.println("Id is ......" + id);
        return ResponseEntity.ok("User saved with id " + id);

    }

    @GetMapping("/login")
    public ResponseEntity<UserResponse>  loginUser(@RequestBody UserRequest request)
    {
        // validate the user:
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUserName() , request.getPassword()
                )
        );

        // If above is authnticated it goes down, otherwise it calls the authentication entryPoint


        String token = jwtUtils.generateToken(request.getUserName());
        return ResponseEntity.ok(new UserResponse(token , "Success! Token Generated"));

    }


}
