package ru.ostap.userservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.ostap.userservice.models.User;
import ru.ostap.userservice.repository.UserRepository;
import ru.ostap.userservice.security.UserDetailsImpl;
import ru.ostap.userservice.util.exception.UserNotFoundException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Optional<User> user = userRepository.findByUsername(s);

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        return new UserDetailsImpl(user.get());
    }
}
