package dev.tindersamurai.atencjobot.mvc.data.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlainPost extends Post {

	private @Column(
			name = "message",
			updatable = false,
			length = 1024
	) String message;
}
