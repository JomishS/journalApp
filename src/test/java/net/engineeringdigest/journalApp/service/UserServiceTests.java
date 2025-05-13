package net.engineeringdigest.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.repository.UserRepository;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Disabled
    @Test
    public void testFindByUserName(){
        assertEquals(4, 2+2);
        assertNotNull(userRepository.findByUserName("Ram"));
        User user=userRepository.findByUserName("Ram");
        assertTrue(!user.getJournalEntries().isEmpty());

    }

    @ParameterizedTest
    @CsvSource({
        "1,1,2",
        "2,10,12",
        "2,3,6"
    })
    public void test(int a, int b, int expected){
        assertEquals(expected, a+b);
    }

    @ParameterizedTest
    @ValueSource(strings={
        "Ram",
        "Vipul",
        "Arjun"
    })
    public void csvTestFindByUserName(String name){
        assertNotNull(userRepository.findByUserName(name));
    }

    @ParameterizedTest
    @ArgumentsSource(UserArgumentsProvider.class)
    public void argumentFindByUserName(User user){
        assertTrue(userService.saveNewUser(user));
    }
}
