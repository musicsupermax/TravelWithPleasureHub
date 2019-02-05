package com.kh021j.travelwithpleasurehub.service.impl;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.model.enumiration.UserRole;
import com.kh021j.travelwithpleasurehub.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
        return buildUserForAuthentication(user,authorities);
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities){
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),user.getPassword(),user.isStatus(),
                true,true,true,authorities
        );
    }

    private List<GrantedAuthority> buildUserAuthority(UserRole userRole){
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(userRole.toString()));
        return new ArrayList<>(grantedAuthorities);
    }
}
