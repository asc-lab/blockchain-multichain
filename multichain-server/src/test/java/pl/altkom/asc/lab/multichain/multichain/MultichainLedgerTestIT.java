package pl.altkom.asc.lab.multichain.multichain;

import multichain.object.Address;
import org.apache.commons.codec.DecoderException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.altkom.asc.lab.multichain.businessdomain.Decision;
import pl.altkom.asc.lab.multichain.businessdomain.LedgerException;
import pl.altkom.asc.lab.multichain.businessdomain.Request;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("prod")
public class MultichainLedgerTestIT {

    @Autowired
    private MultichainLedger ledger;

    @Test
    public void shouldExecuteScenario() throws LedgerException {
        Address user1 = ledger.createNewUser();
        Address user2 = ledger.createNewUser();
        Address manager1 = ledger.createNewManager();
        Address manager2 = ledger.createNewManager();
        Request request1 = new Request(
                user1.getAddress(),
                manager1.getAddress(),
                LocalDateTime.now(),
                "Proszę o urlop - chce jechac na konferencje.");
        String request1tx = ledger.createRequest(request1);
        String decision1tx = ledger.acceptRequest(manager1, request1.getId());
        Request request2 = new Request(
                user2.getAddress(),
                manager2.getAddress(),
                LocalDateTime.now(),
                "Proszę o urlop - jest to dla mnie bardzo wazne.");
        String request2tx = ledger.createRequest(request2);
        String decision2tx = ledger.declineRequest(manager2, request2.getId());
        Request req1verify = ledger.getRequest("req1");
        Decision dec1verify = ledger.getDecisionForRequest(request1.getId());
    }

    @Test
    public void shouldEncodeMessage() {
        String msg = "ala ma kota";
        String hex = ledger.toHex(msg);
        Assert.assertEquals("616c61206d61206b6f7461", hex);
    }

    @Test
    public void shouldDecodeMessage() throws DecoderException {
        String msg = "ala ma kota";
        String hex = ledger.toHex(msg);
        String dec = ledger.fromHex(hex);
        Assert.assertEquals(dec, msg);
    }

}