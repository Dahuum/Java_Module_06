package fr.school42.services;

import fr.school42.exceptions.AlreadyAuthenticatedException;
import fr.school42.exceptions.EntityNotFoundException;
import fr.school42.models.User;
import fr.school42.repositories.UsersRepository;

public class UsersServiceImpl {
    
    private final UsersRepository usersRepository;
    
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }
    
    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        
        if (user.isAuthenticationSuccess()) {
            throw new AlreadyAuthenticatedException("User already authenticated");
        }
        
        if (user.getPassword().equals(password)) {
            user.setAuthenticationSuccess(true);
            usersRepository.update(user);
            return true;
        } else {
            return false;
        }
    }
}
