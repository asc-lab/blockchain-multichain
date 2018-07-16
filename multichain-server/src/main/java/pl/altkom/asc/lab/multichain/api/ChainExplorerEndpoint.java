package pl.altkom.asc.lab.multichain.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.altkom.asc.lab.multichain.blockchaindomain.Block;
import pl.altkom.asc.lab.multichain.blockchaindomain.Chain;
import pl.altkom.asc.lab.multichain.blockchaindomain.ChainExplorer;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChainExplorerEndpoint {

    private final ChainExplorer chainExplorer;

    @GetMapping("/api/blocks")
    public List<Block> getLastBlocks() {
        return chainExplorer.getLastBlocks();
    }

    @GetMapping("/api/chain")
    public Chain getChain() {
        return chainExplorer.getChainInfo();
    }

}
