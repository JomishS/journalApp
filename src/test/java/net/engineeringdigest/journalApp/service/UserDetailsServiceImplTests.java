package net.engineeringdigest.journalApp.service;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;




@ActiveProfiles("dev")
public class UserDetailsServiceImplTests {

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder()
        .userName("ram").password("jlkdsfj").roles(new ArrayList<>()).build());
        UserDetails user=userDetailsService.loadUserByUsername("ram");
        Assertions.assertNotNull(user);
    }

}


// when using spring context to do mocking we use like this. but when we do like this other components related to 
// test will also be loaded. Inorder to not do that we are using @InjectMocks

// @SpringBootTest
// public class UserDetailsServiceImplTests {
    
//     @Autowired
//     private UserDetailsServiceImpl userDetailsService;

//     @MockBean
//     private UserRepository userRepository;

//     @Test
//     void loadUserByUserNameTest(){
//         when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder()
//         .userName("Ram").password("sjlfsw").roles(new ArrayList<>()).build());
//         UserDetails user=userDetailsService.loadUserByUsername("Ram");
//         Assertions.assertNotNull(user);
//     }

// }
