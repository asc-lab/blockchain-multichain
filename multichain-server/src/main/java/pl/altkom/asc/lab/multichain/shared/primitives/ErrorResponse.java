package pl.altkom.asc.lab.multichain.shared.primitives;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse implements Response {

    private Collection<String> errors;
}

