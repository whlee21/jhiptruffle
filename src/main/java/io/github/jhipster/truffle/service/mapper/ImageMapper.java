package io.github.jhipster.truffle.service.mapper;

import io.github.jhipster.truffle.domain.Image;
import io.github.jhipster.truffle.service.dto.ImageDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface ImageMapper extends EntityMapper<ImageDTO, Image> {

    default Image fromId(Long id) {
        if (id == null) {
            return null;
        }
        Image image = new Image();
        image.setId(id);
        return image;
    }

}
