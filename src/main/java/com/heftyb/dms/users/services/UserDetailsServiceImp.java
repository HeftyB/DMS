package com.heftyb.dms.users.services;

import com.heftyb.dms.users.User;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service(value = "userDetailsService")
@Transactional
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImp(final UserService userService) {this.userService = userService;}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userService.findUserByUsername(username);

        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), u.isEnabled(), true, true, true, u.getAuthority()
        );
    }
}
