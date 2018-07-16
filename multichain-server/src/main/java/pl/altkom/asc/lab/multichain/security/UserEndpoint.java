package pl.altkom.asc.lab.multichain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
class UserEndpoint {

    private final UserService userService;

    @GetMapping("/api/users/me")
    public User getMyProfile() {
        return userService.getActualLoggedUser();
    }

    @GetMapping("/api/users")
    public Iterable<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/api/managers")
    public Iterable<User> getAllManagers() {
        return userService.findAllManagers();
    }
}
