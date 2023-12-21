package com.hib.hibenatemysql.service_impl.service;

import com.hib.hibenatemysql.domains.entity.UserInfoDetails;
import com.hib.hibenatemysql.domains.entity.Users;
import com.hib.hibenatemysql.repo.UserRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Users> userDetail = userRepo.findByUserId(username);
        log.error("userId " + userDetail.get().getUserId());
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found" + username));
    }

}
