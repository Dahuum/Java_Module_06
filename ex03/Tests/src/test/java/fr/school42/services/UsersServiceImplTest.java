package fr.school42.services;

import fr.school42.exceptions.AlreadyAuthenticatedException;
import fr.school42.exceptions.EntityNotFoundException;
import fr.school42.models.User;
import fr.school42.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UsersServiceImplTest {

    @Mock
    private UsersRepository usersRepository;  // ← FAKE repository!
    
    private UsersServiceImpl usersService;
    
    @BeforeEach
    void setUp() {
        usersService = new UsersServiceImpl(usersRepository);
    }
    
    @Test
    void testAuthenticateCorrectLoginAndPassword() {
        // ARRANGE: Create fake user
        User fakeUser = new User(1L, "john", "password123", false);
        when(usersRepository.findByLogin("john")).thenReturn(fakeUser);
        
        // ACT: Call the method
        boolean result = usersService.authenticate("john", "password123");
        
        // ASSERT: Check results
        assertTrue(result);
        assertTrue(fakeUser.isAuthenticationSuccess());
        verify(usersRepository).update(fakeUser);  // ← Check update was called!
    }
    
    @Test
    void testAuthenticateIncorrectLogin() {
        // ARRANGE: Tell fake to return null (user not found)
        when(usersRepository.findByLogin("nobody")).thenReturn(null);
        
        // ACT & ASSERT: Should throw exception
        assertThrows(EntityNotFoundException.class, () -> {
            usersService.authenticate("nobody", "anypassword");
        });
        
        // ASSERT: Update should never be called
        verify(usersRepository, never()).update(any());
    }
    
    @Test
    void testAuthenticateIncorrectPassword() {
        // ARRANGE: Create fake user
        User fakeUser = new User(1L, "john", "password123", false);
        when(usersRepository.findByLogin("john")).thenReturn(fakeUser);
        
        // ACT: Try with wrong password
        boolean result = usersService.authenticate("john", "wrongpassword");
        
        // ASSERT: Should return false, user not authenticated
        assertFalse(result);
        assertFalse(fakeUser.isAuthenticationSuccess());
        verify(usersRepository, never()).update(any());
    }
    
    @Test
    void testAuthenticateAlreadyAuthenticated() {
        // ARRANGE: Create fake user already authenticated
        User fakeUser = new User(1L, "john", "password123", true);  // ← true = already authenticated!
        when(usersRepository.findByLogin("john")).thenReturn(fakeUser);
        
        // ACT & ASSERT: Should throw exception
        assertThrows(AlreadyAuthenticatedException.class, () -> {
            usersService.authenticate("john", "password123");
        });
        
        // ASSERT: Update should never be called
        verify(usersRepository, never()).update(any());
    }
}
