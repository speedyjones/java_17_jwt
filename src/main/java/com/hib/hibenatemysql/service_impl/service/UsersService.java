package com.hib.hibenatemysql.service_impl.service;

import com.hib.hibenatemysql.domains.dto.LoginDTO;
import com.hib.hibenatemysql.domains.dto.UsersDTO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UsersService {
    UsersDTO createUser(UsersDTO usersDTO);

    UsersDTO login(LoginDTO loginDTO);

    List<UsersDTO> fetchUsers();

    UserDetailsService userDetailsService();
}
