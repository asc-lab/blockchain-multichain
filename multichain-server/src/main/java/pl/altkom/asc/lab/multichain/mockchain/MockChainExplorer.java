package pl.altkom.asc.lab.multichain.mockchain;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.altkom.asc.lab.multichain.blockchaindomain.Block;
import pl.altkom.asc.lab.multichain.blockchaindomain.Chain;
import pl.altkom.asc.lab.multichain.blockchaindomain.ChainExplorer;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Profile("dev")
public class MockChainExplorer implements ChainExplorer {

    @Override
    public List<Block> getLastBlocks() {
        return getMockBlocks();
    }

    @Override
    public Chain getChainInfo() {
        return getMockChainInfo();
    }

    private Chain getMockChainInfo() {
        return new Chain(
                "Connected",
                "MyChain",
                70,
                165,
                0,
                18,
                3,
                2,
                LocalDate.of(2016, 6, 18),
                1,
                ""
        );
    }

    private List<Block> getMockBlocks() {
        return Arrays.asList(
                new Block(
                        "2ad267a2fb5a334128962aefd54bdd6324df81402c028c46744660c91838986f",
                        "decisions",
                        11,
                        LocalDateTime.now().minusMinutes(99),
                        "1RctHpuyjdKWW37YzcTwZVGPA3YLuio8HT9JNA, 3wijf04jfrv294fj204jf204f9j"
                ),
                new Block(
                        "62b831a038ee85815002fc72c2a91d663d1815769b428f658839f692208a46ea",
                        "requests",
                        11,
                        LocalDateTime.now().minusMinutes(114),
                        "1RctHpuyjdKWW37YzcTwZVGPA3YLuio8HT9JNA, 3wijf04jfrv294fj204jf204f9j"
                ),
                new Block(
                        "7a733baedf0b5a4d820310a9fcb9dd7ef228ff624c0b8c08d744d76abc61ec6d",
                        "",
                        11,
                        LocalDateTime.now().minusMinutes(134),
                        "1RctHpuyjdKWW37YzcTwZVGPA3YLuio8HT9JNA, 3wijf04jfrv294fj204jf204f9j"
                )
        );
    }
}
