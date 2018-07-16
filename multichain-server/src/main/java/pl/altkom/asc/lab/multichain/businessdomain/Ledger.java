package pl.altkom.asc.lab.multichain.businessdomain;

import pl.altkom.asc.lab.multichain.blockchaindomain.UserAddress;

import java.util.List;

public interface Ledger {

    List<Request> getRequests() throws LedgerException;

    List<Request> getUserRequests(String userId) throws LedgerException;

    String createRequest(Request request) throws LedgerException;

    List<Decision> getDecisions() throws LedgerException;

    String createDecision(Decision decision) throws LedgerException;

    UserAddress createUser() throws LedgerException;

    UserAddress createManager() throws LedgerException;
}
