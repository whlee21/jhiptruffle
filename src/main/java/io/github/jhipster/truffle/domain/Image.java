package io.github.jhipster.truffle.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Image.
 */
@Entity
@Table(name = "image")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Image implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crypto_user")
    private String cryptoUser;

    @Column(name = "image_location")
    private String imageLocation;

    @Min(value = 1)
    @Column(name = "upvote_count")
    private Integer upvoteCount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCryptoUser() {
        return cryptoUser;
    }

    public Image cryptoUser(String cryptoUser) {
        this.cryptoUser = cryptoUser;
        return this;
    }

    public void setCryptoUser(String cryptoUser) {
        this.cryptoUser = cryptoUser;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public Image imageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
        return this;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Integer getUpvoteCount() {
        return upvoteCount;
    }

    public Image upvoteCount(Integer upvoteCount) {
        this.upvoteCount = upvoteCount;
        return this;
    }

    public void setUpvoteCount(Integer upvoteCount) {
        this.upvoteCount = upvoteCount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image = (Image) o;
        if (image.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), image.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Image{" +
            "id=" + getId() +
            ", cryptoUser='" + getCryptoUser() + "'" +
            ", imageLocation='" + getImageLocation() + "'" +
            ", upvoteCount=" + getUpvoteCount() +
            "}";
    }
}
