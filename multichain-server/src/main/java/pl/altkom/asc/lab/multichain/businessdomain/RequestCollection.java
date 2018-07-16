package pl.altkom.asc.lab.multichain.businessdomain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.lab.multichain.security.User;
import pl.altkom.asc.lab.multichain.security.UserCollection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
class RequestCollection {

    private final Collection<Request> requests;

    Set<String> getRelatedAddresses() {
        Set<String> addresses = new HashSet<>();

        requests.forEach(r -> {
            addresses.add(r.getManagerId());
            addresses.add(r.getUserId());
        });

        return addresses;
    }

    void updateInfoAboutUsers(Collection<User> users) {
        UserCollection userCollection = new UserCollection(users);
        for (Request r : requests) {
            Optional<User> manager = userCollection.findByBlockchainAddress(r.getManagerId());
            manager.ifPresent(u -> r.setManagerId(u.createUserInfo()));

            Optional<User> user = userCollection.findByBlockchainAddress(r.getUserId());
            user.ifPresent(u -> r.setUserId(u.createUserInfo()));
        }
    }
}
