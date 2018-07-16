package pl.altkom.asc.lab.multichain.shared.primitives;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Getter
@RequiredArgsConstructor
public class AppInfo implements Serializable {

    private final String appName;
    private final String appVersion;
}
