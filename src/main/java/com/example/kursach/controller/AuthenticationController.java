package  com.example.kursach.controller;

import com.example.kursach.dto.LoginResponseDto;
import com.example.kursach.dto.UserDto;
import com.example.kursach.entity.User;
import com.example.kursach.service.AuthenticationService;
import com.example.kursach.service.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/kursach/auth")
@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<User> register(@RequestBody UserDto userDto) {
        User registeredUser = authenticationService.signup(userDto);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody UserDto userDto) {
        User authenticatedUser = authenticationService.authenticate(userDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponseDto loginResponse = new LoginResponseDto();
        loginResponse.setToken(jwtToken);
        loginResponse.setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/remainingTime")
    public ResponseEntity<String> getRemainingTime(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // Убираем "Bearer "
            if (jwtService.isTokenExpired(token)) {
                return ResponseEntity.ok().body("Token has expired");
            }

            return ResponseEntity.ok().body("Expiration: " + jwtService.getRemainingTime(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Missing or invalid token");
    }
}