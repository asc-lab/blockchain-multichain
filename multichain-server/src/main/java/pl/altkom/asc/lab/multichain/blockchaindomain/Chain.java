package pl.altkom.asc.lab.multichain.blockchaindomain;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Chain {

    private String status;
    private String name;
    private long blocks;
    private long transactions;
    private long assets;
    private long addresses;
    private long streams;
    private long peers;
    private LocalDate start;
    private double ageInDays;

    private String rawString;
}
