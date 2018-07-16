package pl.altkom.asc.lab.multichain.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT user "
            + "FROM User user "
            + "JOIN FETCH user.authorities "
            + "WHERE "
            + "user.username = :username")
    User findByUsername(@Param("username") String username);

    @Query("SELECT DISTINCT user "
            + "FROM User user "
            + "WHERE "
            + "user.blockchainAddress IN :address")
    Collection<User> findByBlockchainAddresses(@Param("address") Collection<String> addresses);
}
