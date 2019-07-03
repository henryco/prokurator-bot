package dev.tindersamurai.atencjobot.mvc.data.dao.jpa;

import dev.tindersamurai.atencjobot.mvc.data.entity.TextChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextChannelRepo extends JpaRepository<TextChannel, String> {
}
