package com.hib.hibenatemysql.controllers;

import com.hib.hibenatemysql.domains.dto.LoginDTO;
import com.hib.hibenatemysql.domains.dto.UsersDTO;
import com.hib.hibenatemysql.service_impl.service.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UsersService usersService;

    @PostMapping("/createUser")
    private UsersDTO createUser(@RequestBody UsersDTO usersDTO) {
        return usersService.createUser(usersDTO);
    }

    @PostMapping("/login")
    private UsersDTO login(@RequestBody LoginDTO loginDTO) {
        return usersService.login(loginDTO);
    }

    @GetMapping("/fetchUsers")
    private List<UsersDTO> fetchUsers() {
        return usersService.fetchUsers();
    }


}
