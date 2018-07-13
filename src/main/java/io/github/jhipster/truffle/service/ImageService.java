package io.github.jhipster.truffle.service;

import io.github.jhipster.truffle.domain.Image;
import io.github.jhipster.truffle.repository.ImageRepository;
import io.github.jhipster.truffle.service.dto.ImageDTO;
import io.github.jhipster.truffle.service.mapper.ImageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ImageService {

    private final Logger log = LoggerFactory.getLogger(ImageService.class);

    private final ImageRepository imageRepository;

    private final ImageMapper imageMapper;

    public ImageService(ImageRepository imageRepository, ImageMapper imageMapper) {
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    /**
     * Save a image.
     *
     * @param imageDTO the entity to save
     * @return the persisted entity
     */
    public ImageDTO save(ImageDTO imageDTO) {
        log.debug("Request to save Image : {}", imageDTO);
        Image image = imageMapper.toEntity(imageDTO);
        image = imageRepository.save(image);
        return imageMapper.toDto(image);
    }

    /**
     * Get all the images.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<ImageDTO> findAll() {
        log.debug("Request to get all Images");
        return imageRepository.findAll().stream()
            .map(imageMapper::toDto)
            .sorted(Comparator.comparing(ImageDTO::getUpvoteCount).reversed())
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one image by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<ImageDTO> findById(Long id) {
        log.debug("Request to get Image : {}", id);
        // FIXME: to convert more concise codes
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            return Optional.of(imageMapper.toDto(image.get()));
        } else {
            return Optional.empty();
        }
    }

    /**
     * Delete the image by id.
     *
     * @param id the id of the entity
     */
    public void deleteById(Long id) {
        log.debug("Request to delete Image : {}", id);
        imageRepository.deleteById(id);
    }

}
