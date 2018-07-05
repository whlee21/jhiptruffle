package io.github.jhipster.truffle.repository;

import io.github.jhipster.truffle.domain.PendingTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PendingTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PendingTransactionRepository extends JpaRepository<PendingTransaction, Long> {

}
