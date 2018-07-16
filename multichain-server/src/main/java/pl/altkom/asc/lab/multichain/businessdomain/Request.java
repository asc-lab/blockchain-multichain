package pl.altkom.asc.lab.multichain.businessdomain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.codec.binary.Hex;
import pl.altkom.asc.lab.multichain.config.json.ObjectMapperProvider;

import java.io.IOException;
import java.security.MessageDigest;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    private static final String DIGEST_ALGORITHM = "SHA-256";

    @Setter
    private String userId;
    @Setter
    private String managerId;
    private LocalDateTime creationTime;
    private String requestContent;

    public String toJsonString() throws JsonProcessingException {
        return ObjectMapperProvider.get().writeValueAsString(this);
    }

    public static Request fromJsonString(String json) throws IOException {
        return ObjectMapperProvider.get().readValue(json, Request.class);
    }

    @JsonIgnore
    public String getId() throws LedgerException {
        String requestId;
        try {
            MessageDigest digest = MessageDigest.getInstance(DIGEST_ALGORITHM);
            requestId = Hex.encodeHexString(digest.digest(toJsonString().getBytes()));
        } catch (Exception e) {
            throw new LedgerException("Unable to calculate digest of the request", e);
        }
        return requestId;
    }
}
