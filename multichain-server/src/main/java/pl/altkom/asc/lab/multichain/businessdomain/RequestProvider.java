package pl.altkom.asc.lab.multichain.businessdomain;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.altkom.asc.lab.multichain.security.User;
import pl.altkom.asc.lab.multichain.security.UserService;

import java.util.*;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestProvider {
    private static final Logger logger = LoggerFactory.getLogger(RequestProvider.class);

    private final Ledger ledger;
    private final UserService userService;

    public Collection<Request> getRequests() {
        try {
            List<Request> requests = ledger.getRequests();
            addUserInfo(requests);
            return requests;
        } catch (LedgerException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    public Collection<Request> getUserRequests(String username) {
        try {
            String address = userService.findOne(username).getBlockchainAddress();
            List<Request> requests = ledger.getUserRequests(address);
            addUserInfo(requests);
            return requests;
        } catch (LedgerException e) {
            logger.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private void addUserInfo(List<Request> requests) {
        RequestCollection requestCollection = new RequestCollection(requests);
        Set<String> addresses = requestCollection.getRelatedAddresses();
        Collection<User> users = userService.findByBlockchainAddresses(addresses);
        requestCollection.updateInfoAboutUsers(users);
    }
}
