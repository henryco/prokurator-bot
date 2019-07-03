package dev.tindersamurai.atencjobot.mvc.data.dao.jpa;

import dev.tindersamurai.atencjobot.mvc.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
