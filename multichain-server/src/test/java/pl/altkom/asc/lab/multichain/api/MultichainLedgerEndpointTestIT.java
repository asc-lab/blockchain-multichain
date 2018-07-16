package pl.altkom.asc.lab.multichain.api;

import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.oauth2.client.test.OAuth2ContextConfiguration;
import org.springframework.security.oauth2.client.test.OAuth2ContextSetup;
import org.springframework.security.oauth2.client.test.RestTemplateHolder;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestOperations;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("dev")
@OAuth2ContextConfiguration(AuthDetails.class)
public class MultichainLedgerEndpointTestIT implements RestTemplateHolder {

    @Value("http://localhost:${local.server.port}")
    @Getter
    String host;

    @Rule
    public OAuth2ContextSetup context = OAuth2ContextSetup.standard(this);

    @Getter
    @Setter
    private RestOperations restTemplate;

    @Test
    public void shouldCreateRequest() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String request = "{" +
                        "\"userId\":\"requester\"," +
                        "\"managerId\":\"acceptor\"," +
                        "\"creationTime\":\"2018-06-20T06:34:24.957Z\"," +
                        "\"requestContent\":\"alamakota\"" +
                        "}";
        HttpEntity<String> entity = new HttpEntity<>(request, headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/api/requests", HttpMethod.POST, entity, String.class);
        Assert.assertEquals(200, response.getStatusCode().value());
    }

    @Test
    public void shouldCreateDecision() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
        String request = "{" +
                        "\"userId\":\"requester\"," +
                        "\"managerId\":\"acceptor\"," +
                        "\"creationTime\":\"2018-06-20T06:34:24.957Z\"," +
                        "\"requestContent\":\"alamakota\"" +
                        "}";
        String decision = "{" +
                        "\"managerId\":\"acceptor\"," +
                        "\"request\":" + request + "," +
                        "\"decisionTime\":\"2018-06-20T06:34:24.957Z\"," +
                        "\"decisionResult\":\"ACCEPTED\"" +
                        "}";
        HttpEntity<String> entity = new HttpEntity<>(decision, headers);
        ResponseEntity<String> response = restTemplate.exchange(host + "/api/decisions", HttpMethod.POST, entity, String.class);
        Assert.assertEquals(200, response.getStatusCode().value());
    }

}

class AuthDetails extends ResourceOwnerPasswordResourceDetails {
    public AuthDetails(final Object obj) {
        MultichainLedgerEndpointTestIT it = (MultichainLedgerEndpointTestIT) obj;
        setAccessTokenUri(it.getHost() + "/oauth/token");
        setClientId("client");
        setClientSecret("secret");
        setUsername("user1");
        setPassword("user1");
    }
}
