package dev.tindersamurai.atencjobot.mvc.data.dao.jpa;

import dev.tindersamurai.atencjobot.mvc.data.entity.MediaPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MediaPostRepo extends JpaRepository<MediaPost, String> {
}
