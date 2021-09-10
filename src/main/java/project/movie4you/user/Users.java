package project.movie4you.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class Users implements UserDetailsService {

    private final UserRepository userRepository;

    public Users(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userCandidate = userRepository.findById(login);
        if (userCandidate.isEmpty()) {
            throw new UsernameNotFoundException("");
        }
        User user = userCandidate.get();
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new UserAccount(user.getLogin(), user.getPassword(), authorities);
    }

    public static class UserAccount implements UserDetails {

        private final String login;
        private final String password;
        private final List<GrantedAuthority> authorities;

        public UserAccount(String login, String password, List<GrantedAuthority> authorities) {
            this.login = login;
            this.password = password;
            this.authorities = authorities;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return authorities;
        }

        @Override
        public String getPassword() {
            return password;
        }

        @Override
        public String getUsername() {
            return login;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
