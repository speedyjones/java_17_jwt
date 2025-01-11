package com.hib.hibenatemysql.service_impl.service;

import com.hib.hibenatemysql.domains.dto.LoginDTO;
import com.hib.hibenatemysql.domains.dto.UsersDTO;

import java.util.List;

public interface UsersService {
    UsersDTO login(LoginDTO loginDTO);

    List<UsersDTO> fetchUsers();

    UsersDTO createUser(UsersDTO usersDTO);

    UsersDTO getById(String userId);

    String testJarApi();
}
