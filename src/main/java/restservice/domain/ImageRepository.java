package restservice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import restservice.domain.imagerecognition.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
