package com.immobile.immobileapp.services.implement;

import com.immobile.immobileapp.doa.entities.User;
import com.immobile.immobileapp.repesitory.UserRepesitory;
import com.immobile.immobileapp.services.UserServices;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServicesImplements implements UserServices, UserDetailsService {
    @Autowired
    UserRepesitory userRepository;

    @Override
    public Optional<User> getUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return (UserDetailsService) new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String email) {
                return (UserDetails) userRepository.findByEmail(email)
                        .orElseThrow(() -> new UsernameNotFoundException("user not found"));
            }
        };
    };

    @Override
    public Optional<User> getUsersByImmeubleId(User client) {
        return this.userRepository.findById(client.getId());
    }

    @Override
    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        String passwd = user.getPassword();
        String encodedPasswod = passwordEncoder.encode(passwd);
        user.setPassword(encodedPasswod);
         userRepository.save(user);

    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> opt = userRepository.findByEmail(email);

        org.springframework.security.core.userdetails.User springUser = null;

        if (opt.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        User user = opt.get();
        List <String> roles =user.getRoles();
        Set<GrantedAuthority> ga = new HashSet<>();
        for (String role : roles) {
            ga.add(new SimpleGrantedAuthority(role));
        }

        springUser = new org.springframework.security.core.userdetails.User(
                email,
                user.getPassword(),
                ga);
        return springUser;
    }
}
