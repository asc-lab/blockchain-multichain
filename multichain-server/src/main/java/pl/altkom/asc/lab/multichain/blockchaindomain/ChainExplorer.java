package pl.altkom.asc.lab.multichain.blockchaindomain;

import java.util.List;

/**
 * Return main information about blockchain
 */
public interface ChainExplorer {

    List<Block> getLastBlocks();

    Chain getChainInfo();
}
