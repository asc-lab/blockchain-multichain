package pl.altkom.asc.lab.multichain.businessdomain;

import lombok.RequiredArgsConstructor;
import pl.altkom.asc.lab.multichain.security.User;
import pl.altkom.asc.lab.multichain.security.UserCollection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
class DecisionCollection {

    private final List<Decision> decisions;

    Set<String> getRelatedAddresses() {
        return decisions.stream()
                .map(Decision::getManagerId)
                .collect(Collectors.toSet());
    }

    void updateInfoAboutUsers(Collection<User> users) {
        UserCollection userCollection = new UserCollection(users);
        for (Decision d : decisions) {
            Optional<User> manager = userCollection.findByBlockchainAddress(d.getManagerId());
            manager.ifPresent(u -> d.setManagerId(u.createUserInfo()));
        }
    }
}
