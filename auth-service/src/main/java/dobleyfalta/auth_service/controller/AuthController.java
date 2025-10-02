package dobleyfalta.auth_service.controller;

import dobleyfalta.auth_service.dto.LoginRequest;
import dobleyfalta.auth_service.dto.LoginResponse;
import dobleyfalta.auth_service.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity; 
import org.springframework.http.HttpStatus;  
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        
        LoginResponse response = authService.login(request);
        
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }
}
