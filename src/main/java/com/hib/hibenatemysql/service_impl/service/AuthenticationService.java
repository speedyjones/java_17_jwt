package com.hib.hibenatemysql.service_impl.service;

import com.hib.hibenatemysql.controllers.CustomException;
import com.hib.hibenatemysql.domains.dto.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Slf4j
@Component
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    public Authentication authentication(LoginDTO loginDTO) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDTO.getUserId(),
                            loginDTO.getPassword()
                    )
            );
            return authentication;
        } catch (BadCredentialsException e) {
            throw new CustomException("Invalid Login Details");
        } catch (Exception e) {
            throw new CustomException("Error Msg : " + e.getMessage());
        }
    }

}
