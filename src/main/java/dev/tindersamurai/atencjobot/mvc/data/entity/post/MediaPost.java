package dev.tindersamurai.atencjobot.mvc.data.entity.post;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data @Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MediaPost extends Post {

	private @Column(
			name = "name",
			nullable = false
	) String name;

	private @Column(
			name = "size",
			nullable = false,
			updatable = false
	) Integer size;

	private @Column(
			name = "url",
			unique = true
	) String url;

	private @Column(
			name = "image",
			nullable = false,
			updatable = false
	) Boolean image;
}
