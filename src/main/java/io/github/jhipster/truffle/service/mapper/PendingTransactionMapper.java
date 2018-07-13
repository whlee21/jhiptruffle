package io.github.jhipster.truffle.service.mapper;

import io.github.jhipster.truffle.domain.PendingTransaction;
import io.github.jhipster.truffle.service.dto.PendingTransactionDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity PendingTransaction and its DTO PendingTransactionDTO.
 */
@Mapper(componentModel = "spring", uses = {ImageMapper.class})
public interface PendingTransactionMapper extends EntityMapper<PendingTransactionDTO, PendingTransaction> {

    @Mapping(source = "image.id", target = "imageId")
    PendingTransactionDTO toDto(PendingTransaction pendingTransaction);

    @Mapping(source = "imageId", target = "image")
    PendingTransaction toEntity(PendingTransactionDTO pendingTransactionDTO);

    default PendingTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        PendingTransaction pendingTransaction = new PendingTransaction();
        pendingTransaction.setId(id);
        return pendingTransaction;
    }
}
