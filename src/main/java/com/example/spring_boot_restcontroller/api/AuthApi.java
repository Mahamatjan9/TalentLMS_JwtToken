package com.example.spring_boot_restcontroller.api;

import com.example.spring_boot_restcontroller.converter.LoginConverter;
import com.example.spring_boot_restcontroller.dto.request.UserRequest;
import com.example.spring_boot_restcontroller.dto.response.LoginResponse;
import com.example.spring_boot_restcontroller.dto.response.UserResponse;
import com.example.spring_boot_restcontroller.entity.User;
import com.example.spring_boot_restcontroller.repository.UserRepository;
import com.example.spring_boot_restcontroller.service.security.ValidationExceptionType;
import com.example.spring_boot_restcontroller.service.security.jwt.JwtTokenUtil;
import com.example.spring_boot_restcontroller.service.Impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/jwt")
public class AuthApi { private final UserServiceImpl userService;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final LoginConverter loginConverter;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> getLogin(@RequestBody UserRequest request) {
        try {
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                            request.getPassword());
            User user = userRepository.findByEmail(token.getName()).get();
            return ResponseEntity.ok().body(loginConverter.
                    loginView(jwtTokenUtil.generateToken(user),
                            ValidationExceptionType.SUCCESSFUL, user));

        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).
                    body(loginConverter.
                            loginView("", ValidationExceptionType
                                    .LOGIN_FAILED, null));
        }
    }

    @PostMapping("/registration")
    @PreAuthorize("hasAuthority('Admin')")
    public UserResponse create(@RequestBody UserRequest request) {
        return userService.create(request);
    }

    @GetMapping("/getAllUser")
    @PreAuthorize("isAuthenticated()")
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("isAuthenticated()")
    public UserResponse getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @PutMapping("/updateUser/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public UserResponse updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {
        return userService.updateUser(id, userRequest);
    }

    @DeleteMapping("/deleteUser/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public UserResponse deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/changeRole/{roleId}/{userId}")
    @PreAuthorize("hasAuthority('Admin')")
    public UserResponse changeRole(@PathVariable("roleId") Long roleId, @PathVariable("userId") Long userId) throws IOException {
        return userService.changeRole(roleId, userId);
    }


}
