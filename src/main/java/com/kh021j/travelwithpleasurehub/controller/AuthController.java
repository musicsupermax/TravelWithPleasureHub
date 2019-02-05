package com.kh021j.travelwithpleasurehub.controller;

import com.kh021j.travelwithpleasurehub.model.User;
import com.kh021j.travelwithpleasurehub.service.UserService;
import com.kh021j.travelwithpleasurehub.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by ${JDEEK} on ${11.11.2018}.
 */
@RestController
@CrossOrigin(origins="http://localhost:3000")
@RequestMapping("/")
public class AuthController {

    private UserService userService;

    private AuthenticationManager authenticationManager;

    private UserDetailsServiceImpl userDetailsService;

    private BCryptPasswordEncoder passwordEncoder;

    private SecurityContext securityContext;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;


    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @CrossOrigin(origins="http://localhost:3000")
    @ResponseBody
    public ResponseEntity<?> registration(@Valid @ModelAttribute User user){
        if (userService.getUser(user.getEmail()) == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setStatus(true);
            userService.create(user);
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            token.setDetails(userDetails);
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<User>(userService.getUser(authentication.getName()), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestParam("j_username") String email,
                                   @RequestParam("j_password") String password){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(email,password);
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        token.setDetails(userDetails);
        try {
            Authentication authentication = authenticationManager.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            securityContext = SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            setSecurityContext(securityContext);
            return new ResponseEntity<Object>(userService.getUser(authentication.getName()), HttpStatus.OK);
        }catch (BadCredentialsException e){
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser(){
        try {
            while (getSecurityContext().getAuthentication() != null) {
                Authentication authentication = getSecurityContext().getAuthentication();
                if (!(authentication instanceof AnonymousAuthenticationToken)) {
                    return new ResponseEntity<Object>(userService.getUser(authentication.getName()), HttpStatus.OK);
                }
                if (authentication.getName() == null) {
                    return new ResponseEntity<Object>(HttpStatus.NOT_FOUND);
                }
            }
        }
        catch (NullPointerException e){
            return new ResponseEntity<Object>(e.getMessage(),HttpStatus.NOT_FOUND);

        }
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logout(){
        SecurityContextHolder.clearContext();
        setSecurityContext(null);
        return new ResponseEntity<Object>(HttpStatus.OK);
    }

    @RequestMapping(value = "/loginCheck", method = RequestMethod.GET)
    public boolean isLoginUnique(@RequestParam("email") String email){
        if (userService.getUser(email) !=null){
            return false;
        }
        else {
            return true;
        }
    }

    public SecurityContext getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }
}
