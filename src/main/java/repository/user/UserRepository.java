package repository.user;

import model.User;
import model.validation.Notification;


import javax.naming.AuthenticationException;
import java.util.List;


public interface UserRepository {
    List<User> findAll();

    Notification<User> findByUsernameAndPassword(String username, String password) throws AuthenticationException;

    boolean save(User user);

    void removeAll();

    boolean existsByUsername(String username);
}
