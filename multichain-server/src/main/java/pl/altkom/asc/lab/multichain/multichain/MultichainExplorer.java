package pl.altkom.asc.lab.multichain.multichain;

import lombok.RequiredArgsConstructor;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.StreamKeyItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.altkom.asc.lab.multichain.blockchaindomain.Block;
import pl.altkom.asc.lab.multichain.blockchaindomain.Chain;
import pl.altkom.asc.lab.multichain.blockchaindomain.ChainExplorer;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Profile("!dev")
public class MultichainExplorer implements ChainExplorer {
    private static final Logger logger = LoggerFactory.getLogger(MultichainExplorer.class);

    private final MultiChainCommand mcc;

    @Override
    public List<Block> getLastBlocks() {
        List<Block> blocks = new ArrayList<>();

        try {
            blocks.addAll(getBlocks(MultichainConfig.REQ_STREAM));
            blocks.addAll(getBlocks(MultichainConfig.RES_STREAM));
        } catch (MultichainException e) {
            logger.error(e.toString());
        }

        return blocks.stream()
                .sorted(Comparator.comparing(Block::getTime).reversed())
                .collect(Collectors.toList());
    }

    private List<Block> getBlocks(String streamName) throws MultichainException {
        return mcc.getStreamCommand()
                .listStreamItems(streamName)
                .stream()
                .map(x -> map(x, streamName))
                .collect(Collectors.toList());
    }

    private Block map(StreamKeyItem item, String streamName) {
        return new Block(
                item.getTxid(),
                streamName,
                item.getConfirmations(),
                Instant.ofEpochMilli(item.getBlocktime() * 1000).atZone(ZoneId.systemDefault()).toLocalDateTime(),
                String.join(", ", item.getPublishers())
        );
    }

    @Override
    public Chain getChainInfo() {
        try {
            String info = mcc.getChainCommand().getInfo();
            return Chain.builder()
                    .rawString(info.substring(1, info.length() - 1))
                    .build();
        } catch (MultichainException e) {
            throw new RuntimeException(e);
        }
    }
}
