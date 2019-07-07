package dev.tindersamurai.prokurator.mvc.data.dao.jpa;

import dev.tindersamurai.prokurator.mvc.data.entity.TextChannel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TextChannelRepo extends JpaRepository<TextChannel, String> {
}
