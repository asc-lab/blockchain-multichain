package pl.altkom.asc.lab.multichain.businessdomain;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.altkom.asc.lab.multichain.config.json.ObjectMapperProvider;

import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Decision {

    @Setter
    private String managerId;
    private Request request;
    private LocalDateTime decisionTime;
    private DecisionResult decisionResult;

    public String toJsonString() throws JsonProcessingException {
        return ObjectMapperProvider.get().writeValueAsString(this);
    }

    public static Decision fromJsonString(String json) throws IOException {
        return ObjectMapperProvider.get().readValue(json, Decision.class);
    }

    public enum DecisionResult {
        ACCEPTED, DECLINED
    }
}
