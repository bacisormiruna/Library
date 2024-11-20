package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import repository.security.RightsRolesRepository;

import repository.user.UserRepository;

import javax.naming.AuthenticationException;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.CUSTOMER;
public class AuthentificationServiceMySQL implements AuthentificationService {
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;

    public AuthentificationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Boolean register(String username, String password) {
        String encodedPassword = hashPassword(password);
        Role customerRole = rightsRolesRepository.findRoleByTitle(CUSTOMER);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(encodedPassword)
                .setRoles(Collections.singletonList(customerRole))
                .build();

        return userRepository.save(user);
    }

    @Override
    public User login(String username, String password) {
        try {
            return userRepository.findByUsernameAndPassword(username, hashPassword(password));
        } catch (AuthenticationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    private String hashPassword(String password) {
        try {
            //Secured Hash Algorithm - 256
            //1 byte = 8 biti
            //1 byte =1 char
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}