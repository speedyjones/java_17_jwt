package com.hib.hibenatemysql.service_impl.impl;

import com.hib.hibenatemysql.domains.dto.LoginDTO;
import com.hib.hibenatemysql.domains.dto.UsersDTO;
import com.hib.hibenatemysql.domains.entity.Users;
import com.hib.hibenatemysql.repo.UserRepo;
import com.hib.hibenatemysql.service_impl.service.JwtService;
import com.hib.hibenatemysql.service_impl.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {

    private final UserRepo userRepo;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    private String createUserId() {
        Users getId = userRepo.findLastId();
        int id = 0;
        if (getId != null) {
            id = getId.getId();
        }
        return String.format("VT" + "%04d", id + 1);

    }

    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        userRepo.save(Users.builder()
                .firstName(usersDTO.getFirstName())
                .middleName(usersDTO.getMiddleName())
                .lastName(usersDTO.getLastName())
                .role("USER")
                .token("-")
                .password(passwordEncoder.encode(usersDTO.getPassword()))
                .userId(createUserId())
                .build()
        );

        usersDTO.setToken("-");
        usersDTO.setPassword("SECRET");
        return usersDTO;
    }

    @Override
    public UsersDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getPassword()));
        Optional<Users> users = userRepo.findByUserid(loginDTO.getUserId());
        Users findUser = userRepo.findUser(loginDTO.getUserId());

        String userId = loginDTO.getUserId();
        String token = jwtService.generateToken(userId);

        findUser.setToken(token);
        userRepo.save(findUser);

        return UsersDTO.builder()
                .firstName(users.get().getFirstName())
                .middleName(users.get().getMiddleName())
                .lastName(users.get().getLastName())
                .password("SECRET")
                .userId(users.get().getUserId())
                .token(token)
                .build();

    }


    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String userId) {
                return (UserDetails) userRepo.findByUserid(userId)
                        .orElseThrow(() -> new UsernameNotFoundException("User Not Found " + userId));
            }
        };
    }

    @Override
    public List<UsersDTO> fetchUsers() {
        List<Users> fetchAll = userRepo.findAll();
        List<UsersDTO> usersList = new ArrayList<>();

        for (Users users : fetchAll) {
            UsersDTO singleUser = UsersDTO.builder()
                    .userId(users.getUserId().strip())
                    .firstName(users.getFirstName().strip())
                    .middleName(users.getMiddleName().strip())
                    .lastName(users.getLastName().strip())
                    .token("-")
                    .password("-")
                    .build();
            usersList.add(singleUser);
        }
        return usersList;
    }
}
