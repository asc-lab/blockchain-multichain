package pl.altkom.asc.lab.multichain.blockchaindomain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.altkom.asc.lab.multichain.security.User;

/**
 * Address from blockchain, one application user has one blockchain address.
 * See also:  {@link User}
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserAddress {
    private String address;
    private String pubKey;
}
