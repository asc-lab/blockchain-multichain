package pl.altkom.asc.lab.multichain.blockchaindomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Block {

    private String txid;
    private String streamName;
    private long confirmation;
    private LocalDateTime time;
    private String publishers;
}
