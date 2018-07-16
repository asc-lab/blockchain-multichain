package pl.altkom.asc.lab.multichain.multichain;

import multichain.command.MultiChainCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultichainConfig {

    static final String REQ_STREAM = "requests";
    static final String RES_STREAM = "decisions";

    @Value("${multichain.host}")
    private String host;

    @Value("${multichain.port}")
    private String port;

    @Value("${multichain.login}")
    private String login;

    @Value("${multichain.password}")
    private String password;

    @Bean
    public MultiChainCommand getMultichainCommand(){
        return new MultiChainCommand(host, port, login, password);
    }
}