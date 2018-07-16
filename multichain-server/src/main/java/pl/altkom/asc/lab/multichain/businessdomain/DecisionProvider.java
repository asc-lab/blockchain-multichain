package pl.altkom.asc.lab.multichain.businessdomain;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import pl.altkom.asc.lab.multichain.security.User;
import pl.altkom.asc.lab.multichain.security.UserService;

import java.util.*;

@Component
@RequiredArgsConstructor
public class DecisionProvider {
    private static final Logger logger = LoggerFactory.getLogger(DecisionProvider.class);

    private final Ledger ledger;
    private final UserService userService;

    public Collection<Decision> getDecisions() {
        try {
            List<Decision> decisions = ledger.getDecisions();
            addUserInfo(decisions);
            return decisions;
        } catch (LedgerException e) {
            logger.error(e.getMessage());
            return Collections.emptyList();
        }
    }

    private void addUserInfo(List<Decision> decisions) {
        DecisionCollection decisionCollection = new DecisionCollection(decisions);
        Set<String> addresses = decisionCollection.getRelatedAddresses();
        Collection<User> users = userService.findByBlockchainAddresses(addresses);
        decisionCollection.updateInfoAboutUsers(users);
    }

}
