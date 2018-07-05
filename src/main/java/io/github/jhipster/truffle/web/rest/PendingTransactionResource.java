package io.github.jhipster.truffle.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.truffle.domain.PendingTransaction;
import io.github.jhipster.truffle.repository.PendingTransactionRepository;
import io.github.jhipster.truffle.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.truffle.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PendingTransaction.
 */
@RestController
@RequestMapping("/api")
public class PendingTransactionResource {

    private final Logger log = LoggerFactory.getLogger(PendingTransactionResource.class);

    private static final String ENTITY_NAME = "pendingTransaction";

    private final PendingTransactionRepository pendingTransactionRepository;

    public PendingTransactionResource(PendingTransactionRepository pendingTransactionRepository) {
        this.pendingTransactionRepository = pendingTransactionRepository;
    }

    /**
     * POST  /pending-transactions : Create a new pendingTransaction.
     *
     * @param pendingTransaction the pendingTransaction to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pendingTransaction, or with status 400 (Bad Request) if the pendingTransaction has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pending-transactions")
    @Timed
    public ResponseEntity<PendingTransaction> createPendingTransaction(@RequestBody PendingTransaction pendingTransaction) throws URISyntaxException {
        log.debug("REST request to save PendingTransaction : {}", pendingTransaction);
        if (pendingTransaction.getId() != null) {
            throw new BadRequestAlertException("A new pendingTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PendingTransaction result = pendingTransactionRepository.save(pendingTransaction);
        return ResponseEntity.created(new URI("/api/pending-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pending-transactions : Updates an existing pendingTransaction.
     *
     * @param pendingTransaction the pendingTransaction to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pendingTransaction,
     * or with status 400 (Bad Request) if the pendingTransaction is not valid,
     * or with status 500 (Internal Server Error) if the pendingTransaction couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pending-transactions")
    @Timed
    public ResponseEntity<PendingTransaction> updatePendingTransaction(@RequestBody PendingTransaction pendingTransaction) throws URISyntaxException {
        log.debug("REST request to update PendingTransaction : {}", pendingTransaction);
        if (pendingTransaction.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PendingTransaction result = pendingTransactionRepository.save(pendingTransaction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, pendingTransaction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pending-transactions : get all the pendingTransactions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of pendingTransactions in body
     */
    @GetMapping("/pending-transactions")
    @Timed
    public List<PendingTransaction> getAllPendingTransactions() {
        log.debug("REST request to get all PendingTransactions");
        return pendingTransactionRepository.findAll();
    }

    /**
     * GET  /pending-transactions/:id : get the "id" pendingTransaction.
     *
     * @param id the id of the pendingTransaction to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pendingTransaction, or with status 404 (Not Found)
     */
    @GetMapping("/pending-transactions/{id}")
    @Timed
    public ResponseEntity<PendingTransaction> getPendingTransaction(@PathVariable Long id) {
        log.debug("REST request to get PendingTransaction : {}", id);
        Optional<PendingTransaction> pendingTransaction = pendingTransactionRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(pendingTransaction);
    }

    /**
     * DELETE  /pending-transactions/:id : delete the "id" pendingTransaction.
     *
     * @param id the id of the pendingTransaction to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pending-transactions/{id}")
    @Timed
    public ResponseEntity<Void> deletePendingTransaction(@PathVariable Long id) {
        log.debug("REST request to delete PendingTransaction : {}", id);

        pendingTransactionRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
