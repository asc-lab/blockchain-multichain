package pl.altkom.asc.lab.multichain.security;

import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.Optional;

@RequiredArgsConstructor
public class UserCollection {

    private final Collection<User> users;

    public Optional<User> findByBlockchainAddress(String address) {
        return users.stream()
                .filter(u -> u.getBlockchainAddress().equals(address))
                .findAny();
    }
}
