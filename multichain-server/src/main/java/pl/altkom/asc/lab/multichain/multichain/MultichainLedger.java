package pl.altkom.asc.lab.multichain.multichain;

import com.fasterxml.jackson.core.JsonProcessingException;
import multichain.command.GrantCommand;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.Address;
import multichain.object.Stream;
import multichain.object.StreamKeyItem;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.altkom.asc.lab.multichain.businessdomain.Decision;
import pl.altkom.asc.lab.multichain.businessdomain.Request;
import pl.altkom.asc.lab.multichain.blockchaindomain.UserAddress;
import pl.altkom.asc.lab.multichain.businessdomain.Ledger;
import pl.altkom.asc.lab.multichain.businessdomain.LedgerException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Profile("!dev")
public class MultichainLedger implements Ledger {

    private MultiChainCommand mcc;

    @Autowired
    public MultichainLedger(MultiChainCommand mcc) throws LedgerException {
        this.mcc = mcc;
        try {
            subscribe(MultichainConfig.REQ_STREAM);
            subscribe(MultichainConfig.RES_STREAM);
        } catch (MultichainException e) {
            throw new LedgerException("Unable to create ledger", e);
        }
    }

    @Override
    public UserAddress createUser() throws LedgerException {
        Address address = createNewUser();
        return new UserAddress(address.getAddress(), address.getPubkey());
    }

    @Override
    public UserAddress createManager() throws LedgerException {
        Address address = createNewManager();
        return new UserAddress(address.getAddress(), address.getPubkey());
    }

    @Override
    public String createRequest(Request req) throws LedgerException {
        try {
            return mcc.getStreamCommand().publishFrom(
                    req.getUserId(),
                    MultichainConfig.REQ_STREAM,
                    req.getId(),
                    toHex(req.toJsonString())
            );
        } catch (JsonProcessingException e) {
            throw new LedgerException("Unable to serialize request " + req.getId(), e);
        } catch (MultichainException e) {
            throw new LedgerException("Unable to send request " + req.getId(), e);
        }
    }

    @Override
    public String createDecision(Decision decision) throws LedgerException {
        String requestId = decision.getRequest().getId();
        try {
            return mcc.getStreamCommand().publishFrom(
                    decision.getManagerId(),
                    MultichainConfig.RES_STREAM,
                    requestId,
                    toHex(decision.toJsonString())
            );
        } catch (JsonProcessingException e) {
            throw new LedgerException("Unable to serialize decision for request " + requestId, e);
        } catch (MultichainException e) {
            throw new LedgerException("Your permissions do not allow you to create decision for this request " + requestId, e);
        }
    }

    @Override
    public List<Request> getRequests() throws LedgerException {
        try {
            List<StreamKeyItem> items = mcc.getStreamCommand().listStreamItems(MultichainConfig.REQ_STREAM);
            return items.stream()
                    .flatMap(flatMapToRequests())
                    .sorted(Comparator.comparing(Request::getCreationTime).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LedgerException("Failed to get requests", e);
        }
    }

    @Override
    public List<Decision> getDecisions() throws LedgerException {
        try {
            List<StreamKeyItem> items = mcc.getStreamCommand().listStreamItems(MultichainConfig.RES_STREAM);
            return items.stream()
                    .flatMap(flatMapToDecisions())
                    .sorted(Comparator.comparing(Decision::getDecisionTime).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LedgerException("Failed to get decisions", e);
        }
    }

    @Override
    public List<Request> getUserRequests(String userId) throws LedgerException {
        try {
            List<StreamKeyItem> items = mcc.getStreamCommand().listStreamItems(MultichainConfig.REQ_STREAM);
            return items.stream()
                    .filter(i -> i.getPublishers().contains(userId))
                    .flatMap(flatMapToRequests())
                    .sorted(Comparator.comparing(Request::getCreationTime).reversed())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LedgerException("Failed to get decisions", e);
        }
    }

    String acceptRequest(Address manager, String requestId) throws LedgerException {
        Request request = getRequest(requestId);
        Decision decision = new Decision(manager.getAddress(), request, LocalDateTime.now(), Decision.DecisionResult.ACCEPTED);
        return createDecision(decision);
    }

    String declineRequest(Address manager, String requestId) throws LedgerException {
        Request request = getRequest(requestId);
        Decision decision = new Decision(manager.getAddress(), request, LocalDateTime.now(), Decision.DecisionResult.DECLINED);
        return createDecision(decision);
    }

    Request getRequest(String requestId) throws LedgerException {
        try {
            List<StreamKeyItem> items = mcc.getStreamCommand().listStreamKeyItems(MultichainConfig.REQ_STREAM, requestId);
            return Request.fromJsonString(fromHex(items.get(0).getData()));
        } catch (Exception e) {
            throw new LedgerException("Failed to get request " + requestId, e);
        }
    }

    Decision getDecisionForRequest(String requestId) throws LedgerException {
        try {
            List<StreamKeyItem> items = mcc.getStreamCommand().listStreamKeyItems(MultichainConfig.RES_STREAM, requestId);
            return Decision.fromJsonString(fromHex(items.get(0).getData()));
        } catch (Exception e) {
            throw new LedgerException("Failed to get decision for request " + requestId, e);
        }
    }

    private Function<StreamKeyItem, java.util.stream.Stream<? extends Request>> flatMapToRequests() {
        return i -> {
            try {
                return java.util.stream.Stream.of(Request.fromJsonString(fromHex(i.getData())));
            } catch (Exception e) {
                return java.util.stream.Stream.empty();
            }
        };
    }

    private Function<StreamKeyItem, java.util.stream.Stream<? extends Decision>> flatMapToDecisions() {
        return i -> {
            try {
                return java.util.stream.Stream.of(Decision.fromJsonString(fromHex(i.getData())));
            } catch (Exception e) {
                return java.util.stream.Stream.empty();
            }
        };
    }

    private void subscribe(String reqStream) throws MultichainException {
        if (streamNotExists(reqStream)) {
            this.mcc.getStreamCommand().create(reqStream, false);
        }
        this.mcc.getStreamCommand().subscribe(reqStream);
    }

    private boolean streamNotExists(String streamName) {
        boolean notExists;
        try {
            List<Stream> results = mcc.getStreamCommand().listStreams(streamName);
            notExists = (results == null || results.isEmpty());
        } catch (MultichainException e) {
            notExists = true;
        }
        return notExists;
    }

    String toHex(String input) {
        return Hex.encodeHexString(input.getBytes());
    }

    String fromHex(String input) throws DecoderException {
        byte[] bytes = Hex.decodeHex(input);
        return new String(bytes);
    }

    Address createNewUser() throws LedgerException {
        try {
            Address user = mcc.getAddressCommand().getNewAddressFilled();
            mcc.getGrantCommand().grant(user, GrantCommand.SEND);
            mcc.getGrantCommand().grant(user, GrantCommand.RECEIVE);
            mcc.getWalletTransactionCommand().sendToAddress(user.getAddress(), 0);
            mcc.getGrantCommand().grantWrite(user, MultichainConfig.REQ_STREAM);
            return user;
        } catch (MultichainException e) {
            throw new LedgerException("Unable to create user", e);
        }
    }

    Address createNewManager() throws LedgerException {
        try {
            Address user = mcc.getAddressCommand().getNewAddressFilled();
            mcc.getGrantCommand().grant(user, GrantCommand.SEND);
            mcc.getGrantCommand().grant(user, GrantCommand.RECEIVE);
            mcc.getWalletTransactionCommand().sendToAddress(user.getAddress(), 0);
            mcc.getGrantCommand().grantWrite(user, MultichainConfig.RES_STREAM);
            mcc.getGrantCommand().grantWrite(user, MultichainConfig.REQ_STREAM);
            return user;
        } catch (MultichainException e) {
            throw new LedgerException("Unable to create manager", e);
        }
    }

}
