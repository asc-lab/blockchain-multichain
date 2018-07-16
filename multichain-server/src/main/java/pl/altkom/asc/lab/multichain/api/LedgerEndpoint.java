package pl.altkom.asc.lab.multichain.api;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.altkom.asc.lab.multichain.businessdomain.*;
import pl.altkom.asc.lab.multichain.security.UserService;
import pl.altkom.asc.lab.multichain.shared.primitives.ErrorResponse;
import pl.altkom.asc.lab.multichain.shared.primitives.OkResponse;
import pl.altkom.asc.lab.multichain.shared.primitives.Response;

import java.util.Collection;
import java.util.Collections;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class LedgerEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(LedgerEndpoint.class);

    private final Ledger ledger;
    private final DecisionProvider decisionProvider;
    private final RequestProvider requestProvider;
    private final UserService userService;

    @GetMapping("/api/requests")
    public Collection<Request> getRequests() {
        return requestProvider.getRequests();
    }

    @GetMapping("/api/decisions")
    public Collection<Decision> getDecisions() {
        return decisionProvider.getDecisions();
    }

    @GetMapping("/api/users/{username}/requests")
    public Collection<Request> getUserRequests(@PathVariable String username) {
        return requestProvider.getUserRequests(username);
    }

    @PostMapping("/api/requests")
    public ResponseEntity<Response> createRequest(@RequestBody Request request) {
        setUserContext(request);
        try {
            return ResponseEntity.ok(new OkResponse(ledger.createRequest(request)));
        } catch (LedgerException e) {
            logger.error(e.getMessage(), e);
            return createErrorFromLedgerException(e);
        }
    }

    @PostMapping("/api/decisions")
    public ResponseEntity<Response> createDecision(@RequestBody Decision decision) {
        setUserContext(decision);
        try {
            return ResponseEntity.ok(new OkResponse(ledger.createDecision(decision)));
        } catch (LedgerException e) {
            logger.error(e.getMessage(), e);
            return createErrorFromLedgerException(e);
        }
    }

    private ResponseEntity<Response> createErrorFromLedgerException(LedgerException e) {
        return ResponseEntity.badRequest().body(new ErrorResponse(Collections.singletonList(e.getMessage())));
    }

    private void setUserContext(Decision decision) {
        decision.setManagerId(userService.getActualLoggedUser().getBlockchainAddress());
    }

    private void setUserContext(Request request) {
        request.setUserId(userService.getActualLoggedUser().getBlockchainAddress());
    }
}
