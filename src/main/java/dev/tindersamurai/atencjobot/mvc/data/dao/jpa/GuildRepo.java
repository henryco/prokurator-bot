package dev.tindersamurai.atencjobot.mvc.data.dao.jpa;

import dev.tindersamurai.atencjobot.mvc.data.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepo extends JpaRepository<Guild, String> {
}
