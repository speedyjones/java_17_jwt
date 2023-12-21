package com.hib.hibenatemysql.service_impl.impl;

import com.hib.hibenatemysql.domains.dto.LoginDTO;
import com.hib.hibenatemysql.domains.dto.UsersDTO;
import com.hib.hibenatemysql.domains.entity.Users;
import com.hib.hibenatemysql.repo.UserRepo;
import com.hib.hibenatemysql.service_impl.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private final ModelMapper modelMapper;

    public String createUserId() {
        Optional<Users> getId = userRepo.findLastId();
        int id = 0;
        if (getId.isPresent()) {
            id = getId.get().getId();
        }
        return String.format("VT" + "%04d", id + 1);

    }


    @Override
    public UsersDTO createUser(UsersDTO usersDTO) {
        String userId = createUserId();
        userRepo.save(Users.builder()
                .firstName(usersDTO.getFirstName())
                .middleName(usersDTO.getMiddleName())
                .lastName(usersDTO.getLastName())
                .role("USER")
                .token("-")
                .password(passwordEncoder.encode(usersDTO.getPassword()))
                .userId(userId)
                .build()
        );

        usersDTO.setToken("-");
        usersDTO.setPassword("SECRET");
        usersDTO.setUserId(userId);
        return usersDTO;
    }

    @Override
    public UsersDTO getById(String userId) {

        Optional<Users> usersDetail = userRepo.findByUserId(userId);
        return UsersDTO.builder()
                .userId(usersDetail.get().getUserId())
                .firstName(usersDetail.get().getFirstName())
                .middleName(usersDetail.get().getMiddleName())
                .lastName(usersDetail.get().getLastName())
                .password("SECRET")
                .token("-")
                .build();
    }


    @Override
    public UsersDTO login(LoginDTO loginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUserId(), loginDTO.getPassword()));
        Optional<Users> users = userRepo.findByUserId(loginDTO.getUserId());
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

    @Override
    public List<UsersDTO> fetchUsers() {
        List<Users> fetchAll = userRepo.findAll(Sort.by(Sort.Direction.DESC, "id"));
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
