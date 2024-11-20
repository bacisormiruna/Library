package service.user;

import model.User;
public interface AuthentificationService {
    Boolean register(String username, String password);

    User login(String username, String password);

    boolean logout(User user);
}
