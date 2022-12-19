package com.example.spring_boot_restcontroller.service.Impl;

import com.example.spring_boot_restcontroller.dto.request.UserRequest;
import com.example.spring_boot_restcontroller.dto.response.UserResponse;
import com.example.spring_boot_restcontroller.entity.Role;
import com.example.spring_boot_restcontroller.entity.User;
import com.example.spring_boot_restcontroller.repository.RoleRepository;
import com.example.spring_boot_restcontroller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponse create(UserRequest request) {
        User user = mapToEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        Role role = roleRepository.findById(3L).get();
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
        return mapToResponse(user);
    }

    private User mapToEntity(UserRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setFirstName(request.getFirstName());
        user.setPassword(request.getPassword());
        return user;
    }

    private UserResponse mapToResponse(User user) {
        if (user == null) {
            return null;
        }
        UserResponse response = new UserResponse();
        if (user.getId() != null) {
            response.setId(String.valueOf(user.getId()));
        }
        response.setEmail(user.getEmail());
        response.setFirstName(user.getFirstName());
        return response;
    }

    private List<UserResponse> mapToResponseList(List<User> users){
        List<UserResponse> registerResponses = new ArrayList<>();
        for (User user: users) {
            registerResponses.add(mapToResponse(user));
        }

        return registerResponses;
    }


    @PostConstruct
    public void initMethod(){
        Role role1 = new Role();
        role1.setRoleName("Admin");

        Role role2 = new Role();
        role2.setRoleName("Instructor");

        Role role3 = new Role();
        role3.setRoleName("Student");

        UserRequest request = new UserRequest();
        request.setEmail("esen@gmail.com");
        request.setPassword(passwordEncoder.encode("1234"));
        request.setFirstName("Esen");

        User user2 = mapToEntity(request);

        user2.setRoles(Arrays.asList(role1));
        role1.setUsers(Arrays.asList(user2));

        userRepository.save(user2);
        roleRepository.save(role1);
        roleRepository.save(role2);
        roleRepository.save(role3);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("not found email"));
    }

    public List<UserResponse> getAllUsers(){
        return mapToResponseList(userRepository.findAll());
    }

    public UserResponse getUserById(Long id){
        return mapToResponse(userRepository.findById(id).get());
    }

    public UserResponse updateUser(Long id, UserRequest userRequest){
        User user = userRepository.findById(id).get();
        if (userRequest.getEmail() != null)
            user.setEmail(userRequest.getEmail());
        if (userRequest.getPassword() != null)
            user.setFirstName(userRequest.getFirstName());
        if (userRequest.getFirstName() != null)
            user.setPassword(userRequest.getPassword());
        return mapToResponse(user);
    }

    public UserResponse deleteUser(Long id){
        User user = userRepository.findById(id).get();
        userRepository.delete(user);
        return mapToResponse(user);
    }

    public UserResponse changeRole(Long roleId, Long userId) throws IOException {
        User user = userRepository.findById(userId).get();
        Role role = roleRepository.findById(roleId).get();
        if (role.getRoleName().equalsIgnoreCase("admin")){
            throw new IOException("only 1 user can be admin");
        }

        user.getRoles().add(role);
        role.getUsers().add(user);

        userRepository.save(user);
        roleRepository.save(role);

        return mapToResponse(user);
    }
}
