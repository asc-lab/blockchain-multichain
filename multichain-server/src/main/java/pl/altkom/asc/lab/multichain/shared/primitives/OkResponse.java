package pl.altkom.asc.lab.multichain.shared.primitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OkResponse implements Response {

    private String message;
}
