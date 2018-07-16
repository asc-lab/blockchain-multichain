package pl.altkom.asc.lab.multichain.businessdomain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import pl.altkom.asc.lab.multichain.config.json.ObjectMapperProvider;

import java.time.LocalDateTime;

public class DecisionTest {

    @Test
    public void shouldSerializeToJson() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now().plusNanos(1);
        Request request = new Request(
                "user1",
                "manager1",
                now,
                "Proszę o urlop");
        Decision d = new Decision(
                "manager1",
                request,
                now,
                Decision.DecisionResult.ACCEPTED);

        Assert.assertEquals(
                "{" +
                        "\"managerId\":\"manager1\"," +
                        "\"request\":" + request.toJsonString() +
                        ",\"decisionTime\":\"" + now.toString() + "\"," +
                        "\"decisionResult\":\"ACCEPTED\"" +
                        "}",
                ObjectMapperProvider.get().writeValueAsString(d)
        );
    }
}