package dev.tindersamurai.prokurator.mvc.data.dao.jpa;

import dev.tindersamurai.prokurator.mvc.data.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepo extends JpaRepository<Guild, String> {
}
