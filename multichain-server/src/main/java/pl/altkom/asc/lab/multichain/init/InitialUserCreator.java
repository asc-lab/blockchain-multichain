package pl.altkom.asc.lab.multichain.init;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.altkom.asc.lab.multichain.blockchaindomain.UserAddress;
import pl.altkom.asc.lab.multichain.businessdomain.Ledger;
import pl.altkom.asc.lab.multichain.businessdomain.LedgerException;
import pl.altkom.asc.lab.multichain.security.Authority;
import pl.altkom.asc.lab.multichain.security.AuthorityRepository;
import pl.altkom.asc.lab.multichain.security.User;
import pl.altkom.asc.lab.multichain.security.UserRepository;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional
public class InitialUserCreator {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final Ledger ledger;

    void init() {
        addInitialAuthorities();
        addInitialUsers();
    }

    private void addInitialAuthorities() {
        Authority managerAuth = authorityRepository.findByName(Authority.getManagerAuthority());
        if (managerAuth == null)
            authorityRepository.save(new Authority(Authority.getManagerAuthority()));

        Authority userAuth = authorityRepository.findByName(Authority.getUserAuthority());
        if (userAuth == null)
            authorityRepository.save(new Authority(Authority.getUserAuthority()));
    }

    private void addInitialUsers() {
        Authority managerAuth = authorityRepository.findByName(Authority.getManagerAuthority());
        Authority userAuth = authorityRepository.findByName(Authority.getUserAuthority());

        addFakeData(managerAuth, userAuth);
        addAlmostRealData(managerAuth, userAuth);
    }

    private void addFakeData(Authority managerAuth, Authority userAuth) {
        addUser("manager1", Arrays.asList(managerAuth, userAuth), true);
        addUser("manager2", Arrays.asList(managerAuth, userAuth), true);
        addUser("user1", Collections.singletonList(userAuth), false);
        addUser("user2", Collections.singletonList(userAuth), false);
    }

    private void addAlmostRealData(Authority managerAuth, Authority userAuth) {
        addUser("robert_witkowski", Collections.singletonList(userAuth), false);
        addUser("robert_kusmierek", Collections.singletonList(userAuth), false);
        addUser("jan_kowalski", Collections.singletonList(userAuth), false);
        addUser("anna_nowak", Collections.singletonList(userAuth), false);
    }

    private void addUser(String username, List<Authority> authorities, boolean isManager) {
        User byUsername = userRepository.findByUsername(username);
        if (byUsername != null)
            return;

        try {
            UserAddress address = isManager ? ledger.createManager() : ledger.createUser();
            userRepository.save(User.builder()
                    .username(username)
                    .password(passwordEncoder.encode(username))
                    .authorities(authorities)
                    .blockchainAddress(address.getAddress())
                    .blockchainPubKey(address.getPubKey())
                    .build());
        } catch (LedgerException e) {
            throw new RuntimeException(e);
        }
    }
}
