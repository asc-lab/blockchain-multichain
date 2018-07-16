package pl.altkom.asc.lab.multichain.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.altkom.asc.lab.multichain.shared.primitives.AppInfo;

@RestController
public class ApplicationMainEndpoint {

    @GetMapping("/")
    public ResponseEntity<AppInfo> getAppInfo() {
        return ResponseEntity.ok(new AppInfo("MultiChain API", "1.0"));
    }
}
