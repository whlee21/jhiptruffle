package io.github.jhipster.truffle.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.github.jhipster.truffle.domain.Image;
import io.github.jhipster.truffle.service.ImageService;
import io.github.jhipster.truffle.service.dto.ImageDTO;
import io.github.jhipster.truffle.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.truffle.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * REST controller for managing Image.
 */
@RestController
@RequestMapping("/api")
public class ImageResource {

    private final Logger log = LoggerFactory.getLogger(ImageResource.class);

    private static final String ENTITY_NAME = "image";

    private final ImageService imageService;

    public ImageResource(ImageService imageService) {
        this.imageService = imageService;
    }

    @Autowired
    ServletContext context;

    /**
     * POST  /images : Create a new image.
     *
     * @param imageDTO the image to create
     * @return the ResponseEntity with status 201 (Created) and with body the new image, or with status 400 (Bad Request) if the image has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> createImage(@Valid @RequestBody ImageDTO imageDTO) throws URISyntaxException, IOException {
        log.debug("REST request to save Image : {}", imageDTO);
        if (imageDTO.getId() != null) {
            throw new BadRequestAlertException("A new image cannot already have an ID", ENTITY_NAME, "idexists");
        }

        String formattedBase64Image = imageDTO.getImageBase64().split(",")[1];
        byte[] decodedImage = Base64.getDecoder().decode(formattedBase64Image);

        String dirString = context.getRealPath("/")+"/images/";
        File directory = new File(dirString);
        if (!directory.exists()) {
            directory.mkdir();
        }

        UUID uuid = UUID.randomUUID();
        String fileName = uuid.toString() + ".jpg";
        File file = new File(dirString, fileName);
        while(file.exists()) {
            uuid = UUID.randomUUID();
            fileName = uuid.toString() + ".jpg";
            file = new File(dirString, fileName);
        }

        new FileOutputStream(file.getPath()).write(decodedImage);

        imageDTO.setImageLocation("images/" + fileName);
        ImageDTO result = imageService.save(imageDTO);
        return ResponseEntity.created(new URI("/api/images/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /images : Updates an existing image.
     *
     * @param image the image to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated image,
     * or with status 400 (Bad Request) if the image is not valid,
     * or with status 500 (Internal Server Error) if the image couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/images")
    @Timed
    public ResponseEntity<ImageDTO> updateImage(@Valid @RequestBody ImageDTO image) throws URISyntaxException {
        log.debug("REST request to update Image : {}", image);
        if (image.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ImageDTO result = imageService.save(image);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, image.getId().toString()))
            .body(result);
    }

    /**
     * GET  /images : get all the images.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of images in body
     */
    @GetMapping("/images")
    @Timed
    public List<ImageDTO> getAllImages() {
        log.debug("REST request to get all Images");
        return imageService.findAll();
    }

    /**
     * GET  /images/:id : get the "id" image.
     *
     * @param id the id of the image to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the image, or with status 404 (Not Found)
     */
    @GetMapping("/images/{id}")
    @Timed
    public ResponseEntity<ImageDTO> getImage(@PathVariable Long id) {
        log.debug("REST request to get Image : {}", id);
        Optional<ImageDTO> image = imageService.findById(id);
        return ResponseUtil.wrapOrNotFound(image);
    }

    /**
     * DELETE  /images/:id : delete the "id" image.
     *
     * @param id the id of the image to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/images/{id}")
    @Timed
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        log.debug("REST request to delete Image : {}", id);

        imageService.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
