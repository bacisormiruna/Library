package service.user;

import model.Role;
import model.User;
import model.builder.UserBuilder;
import model.validation.Notification;
import model.validation.UserValidator;
import repository.security.RightsRolesRepository;

import repository.user.UserRepository;

import javax.naming.AuthenticationException;
import java.security.MessageDigest;
import java.util.Collections;

import static database.Constants.Roles.*;

public class AuthentificationServiceMySQL implements AuthentificationService {
    private final UserRepository userRepository;
    private final RightsRolesRepository rightsRolesRepository;
    private User currentUser;

    public AuthentificationServiceMySQL(UserRepository userRepository, RightsRolesRepository rightsRolesRepository) {
        this.userRepository = userRepository;
        this.rightsRolesRepository = rightsRolesRepository;
    }

    @Override
    public Notification<Boolean> register(String username, String password) {
        Role employeeRole = rightsRolesRepository.findRoleByTitle(EMPLOYEE);
        User user = new UserBuilder()
                .setUsername(username)
                .setPassword(password)
                .setRoles(Collections.singletonList(employeeRole))
                .build();
        UserValidator userValidator = new UserValidator(user);
        boolean userValid= userValidator.validate();
        Notification<Boolean> userRegisterNotification= new Notification<>();
        if (!userValid){
            userValidator.getErrors().forEach(userRegisterNotification::addError);
            userRegisterNotification.setResult(Boolean.FALSE);
        }else{
            user.setPassword((hashPassword(password)));
            userRegisterNotification.setResult(userRepository.save(user));
        }
        return userRegisterNotification;
    }

    @Override
    public Notification<User> login(String username, String password) {
        try {
            Notification<User> loginNotification = userRepository.findByUsernameAndPassword(username, hashPassword(password));

            if (loginNotification.hasErrors() || loginNotification.getResult() == null) {
                loginNotification.addError("Autentificare esuata");
                return loginNotification;
            }

            currentUser = loginNotification.getResult();

            return loginNotification;
        } catch (AuthenticationException e) {
            throw new RuntimeException("Eroare la autentificare: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean logout(User user) {
        return false;
    }

    public Long getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getId();
        }
        return 13L;//pun un anumit angajat care stiu ca exista
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