package project.movie4you.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserEndpoint {
    private final UserRepository userRepository;

    public UserEndpoint(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
   /* @PostMapping
    public ResponseEntity<Void> register(@RequestBody UserDefinition userDefinition) {
        User user = new User(userDefinition.login, passwordEncoder.encode(userDefinition.password));
        userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
*/
    @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
    protected static class UserDefinition {
        String login;
        String password;
        public static UserDefinition from(User user) {
            UserDefinition userDefinition = new UserDefinition();
            userDefinition.login = user.getLogin();
            userDefinition.password = user.getPassword();
            return userDefinition;
        }
    }
}
