package pl.altkom.asc.lab.multichain.mockchain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.altkom.asc.lab.multichain.blockchaindomain.UserAddress;
import pl.altkom.asc.lab.multichain.businessdomain.Decision;
import pl.altkom.asc.lab.multichain.businessdomain.Ledger;
import pl.altkom.asc.lab.multichain.businessdomain.Request;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@Profile("dev")
public class MockLedger implements Ledger {

    private static final String USER_1 = "user1";
    private static final String USER_2 = "user2";
    private static final String MANAGER_1 = "manager1";
    private static final String MANAGER_2 = "manager2";

    @Override
    public List<Request> getRequests() {
        return Arrays.asList(
                new Request(USER_1, MANAGER_1, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-20"),
                new Request(USER_2, MANAGER_2, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-21")
        );
    }

    @Override
    public List<Decision> getDecisions() {
        Request request1 = new Request(USER_1, MANAGER_1, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-20");
        Request request2 = new Request(USER_2, MANAGER_2, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-21");
        return Arrays.asList(
                new Decision(MANAGER_1, request1, LocalDateTime.now(), Decision.DecisionResult.ACCEPTED),
                new Decision(MANAGER_2, request2, LocalDateTime.now(), Decision.DecisionResult.DECLINED)
        );
    }

    @Override
    public List<Request> getUserRequests(String userId) {
        switch (userId) {
            case USER_1:
                return Arrays.asList(
                        new Request(USER_1, MANAGER_1, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-20"),
                        new Request(USER_1, MANAGER_1, LocalDateTime.now().minusDays(2), "Proszę o nowy monitor")
                );
            case USER_2:
                return Arrays.asList(
                        new Request(USER_2, MANAGER_2, LocalDateTime.now(), "Proszę o urlop w dniu 2018-06-21"),
                        new Request(USER_2, MANAGER_2, LocalDateTime.now().minusDays(5), "Proszę o nowy komputer")
                );
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public String createRequest(Request request) {
        return "OK";
    }

    @Override
    public String createDecision(Decision decision) {
        return "OK";
    }

    @Override
    public UserAddress createUser() {
        return new UserAddress(
                "1DTm68MtZ5tH4LCWeZmFxF18uHhZkzctPhV3Fu",
                "02bb5d1599d110bf81f5a89d001299120c7af9533a780432cef0b410188f3f0f8a");
    }

    @Override
    public UserAddress createManager() {
        return new UserAddress(
                "13ieH6F6jPbkAEJbweyxK9S8bkRMbsQYZ3ai6T",
                "0313061de0b6e2d3dc2bd8f1e5c00dcb6229cfe4168fe29f263eb35c3e477efb5f");
    }
}
