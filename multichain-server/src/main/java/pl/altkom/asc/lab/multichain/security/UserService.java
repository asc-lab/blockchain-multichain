package pl.altkom.asc.lab.multichain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service(value = "userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        User user = findOne(userId);
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }

    public User getActualLoggedUser() {
        UserDetails details = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(details.getUsername());
    }


    public Collection<User> findByBlockchainAddresses(Collection<String> addresses) {
        return userRepository.findByBlockchainAddresses(addresses);
    }

    public User findOne(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("Invalid username or password.");
        return user;
    }

    Collection<User> findAll() {
        return userRepository.findAll();
    }

    Collection<User> findAllManagers() {
        return userRepository.findAll()
                .parallelStream()
                .filter(user ->
                        user.getAuthorities()
                                .parallelStream()
                                .map(Authority::getName)
                                .anyMatch(name -> Authority.getManagerAuthority().equals(name))
                )
                .collect(Collectors.toList());
    }

}
