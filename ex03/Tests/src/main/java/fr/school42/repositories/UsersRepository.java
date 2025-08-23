package fr.school42.repositories;

import fr.school42.models.User;

public interface UsersRepository {
    User findByLogin(String login);
    void update(User user);
}
