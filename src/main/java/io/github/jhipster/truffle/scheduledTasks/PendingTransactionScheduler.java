package io.github.jhipster.truffle.scheduledTasks;

import io.github.jhipster.truffle.domain.Image;
import io.github.jhipster.truffle.domain.PendingTransaction;
import io.github.jhipster.truffle.repository.ImageRepository;
import io.github.jhipster.truffle.repository.PendingTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Component
public class PendingTransactionScheduler {
    private final Logger log = LoggerFactory.getLogger(PendingTransactionScheduler.class);

    private final PendingTransactionRepository pendingTransactionRepository;

    private final ImageRepository imageRepository;

    public PendingTransactionScheduler(PendingTransactionRepository pendingTransactionRepository, ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
        this.pendingTransactionRepository = pendingTransactionRepository;
    }

    @Scheduled(fixedRate = 60000)
    public void processPendingTransactions() throws ExecutionException, InterruptedException {
        // Retrieve pending_transaction table results
        List<PendingTransaction> pendingTransactionSchedulerList = pendingTransactionRepository.findAll();
        log.debug("Found " + pendingTransactionSchedulerList.size() + " pending transactions");

        // cycle through the results and check the transaction receipt
        Web3j web3 = Web3j.build(new HttpService());
        for (PendingTransaction pendingTransaction : pendingTransactionSchedulerList) {
            log.debug("Processing pending transaction: " + pendingTransaction.toString());
            Optional<TransactionReceipt> transactionReceipt = web3.ethGetTransactionReceipt(pendingTransaction.getTransactionHash()).sendAsync().get().getTransactionReceipt();

            // TODO: ensure that the transaction is confirmed before proceeding
            if (transactionReceipt.isPresent()) {
                // Update vote count
//                Image imageToBeUpvoted = imageRepository.findOne(pendingTransaction.getImage().getId());
//                log.debug("Image to be upvoted: " + imageToBeUpvoted.toString());
//                imageToBeUpvoted.setUpvoteCount(imageToBeUpvoted.getUpvoteCount() + 1);
//                imageRepository.save(imageToBeUpvoted);
                log.debug("image {}", pendingTransaction.getImage());
                imageRepository.findById(pendingTransaction.getImage().getId()).ifPresent(imageToBeUpvoted -> {
                    log.debug("Image to be upvoted: " + imageToBeUpvoted.toString());
                    imageToBeUpvoted.setUpvoteCount(imageToBeUpvoted.getUpvoteCount() + 1);
                    imageRepository.save(imageToBeUpvoted);
                });

                // delete pending transaction record
                pendingTransactionRepository.deleteById(pendingTransaction.getId());
            }
        }

    }
}
