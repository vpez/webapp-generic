package org.vap.webapp.service.user;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vap.webapp.data.Repository;
import org.vap.webapp.model.User;
import org.vap.webapp.model.UserLogin;
import org.vap.webapp.validator.Validator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vahe Pezeshkian
 */

@Service
public class UserAuthenticationService implements AuthenticationService {

    @Autowired
    private Repository<User> userRepository;

    @Autowired
    private Validator<User> userValidator;

    @Override
    public User register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(hash(password));
        userValidator.validate(user);

        user = userRepository.insert(user);

        if (user == null) {
            throw new RuntimeException(String.format("Username %s already taken", username));
        }

        return user;
    }

    @Override
    public User confirm(String username, String token) {
        return null;
    }

    @Override
    public User login(String username, String password) throws AuthException {
        User user = userRepository.getByValue("username", username).get(0);

        if (user == null) {
            throw new AuthException("User not found");
        }

        if (!hash(password).equals(user.getPassword())) {
            throw new AuthException("Wrong password");
        }

        // Remember this login
        UserLogin login = new UserLogin();
        login.setUserId(user.getId());
        login.setTimestamp(Instant.now().toEpochMilli());
        login.setHost("test_host");     // TODO just for test

        if (user.getUserLogins() == null) {
            user.setUserLogins(new ArrayList<>());
        }
        user.getUserLogins().add(login);
        userRepository.update(user);

        return user;
    }

    @Override
    public User logout(String username) {
        return null;
    }

    public void clear() {
        List<User> users = userRepository.getAll();
        users.forEach(user -> userRepository.deleteById(user.getId()));
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    private String hash(String password) {
        if (password.isEmpty()) {
            return "";
        }

        return DigestUtils.md5Hex(password);
    }
}
