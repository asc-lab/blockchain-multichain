package pl.altkom.asc.lab.multichain.businessdomain;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Test;
import pl.altkom.asc.lab.multichain.config.json.ObjectMapperProvider;

import java.time.LocalDateTime;

public class RequestTest {

    @Test
    public void shouldSerializeToJson() throws JsonProcessingException {
        LocalDateTime now = LocalDateTime.now().plusNanos(1);
        Request req = new Request(
                "user1",
                "manager1",
                now,
                "Prosze o urlop");

        Assert.assertEquals(
                "{" +
                        "\"userId\":\"user1\"," +
                        "\"managerId\":\"manager1\"," +
                        "\"creationTime\":\"" + now.toString() + "\"," +
                        "\"requestContent\":\"Prosze o urlop\"" +
                        "}",
                ObjectMapperProvider.get().writeValueAsString(req)
        );
    }
}