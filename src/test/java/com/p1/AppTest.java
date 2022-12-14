package com.p1;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import com.google.common.hash.Hashing;
import com.p1.dao.UserDao;
import com.p1.models.User;
import com.p1.service.UserService;

/**
 * Unit test for simple App.
 */
class AppTest {
    UserDao userDao = Mockito.mock(UserDao.class);
    UserService userService = new UserService(userDao);

    @Test
    void testValidateCredentialsValidCredentials() {
        // arrange
        User objToPass = new User("silas839",
                Hashing.sha256().hashString("password", StandardCharsets.UTF_8).toString());
        Mockito.when(userDao.getUserByUsername(objToPass.getUsername())).thenReturn(objToPass);

        // act
        Boolean actualResult = userService.validateCredentials(new User("silas839", "password"));

        // assert
        Assertions.assertTrue(actualResult);
    }

    @Test
    void testValidateCredentialsInvalidUsername() {
        // arrange
        User objToPass = new User("silas", "verysecurepassword");
        Mockito.when(userDao.getUserByUsername(objToPass.getUsername())).thenReturn(null);

        // act
        Boolean actualResult = userService.validateCredentials(objToPass);

        // assert
        Assertions.assertFalse(actualResult);
    }

    @Test
    void testValidateCredentialsInvalidPassword() {
        // arrange
        User objToPass = new User("silas839", "securepassword");
        User objReturnedFromDb = new User("silas839", "verysecurepassword");
        Mockito.when(userDao.getUserByUsername(objToPass.getUsername())).thenReturn(objReturnedFromDb);

        // act
        Boolean actualResult = userService.validateCredentials(objToPass);

        // assert
        Assertions.assertFalse(actualResult);
    }

}
