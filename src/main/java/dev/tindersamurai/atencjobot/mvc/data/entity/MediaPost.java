package dev.tindersamurai.atencjobot.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.TemporalType.TIMESTAMP;

@Data @Entity
@NoArgsConstructor
@AllArgsConstructor
public class MediaPost {

	private @Id @Column(
			name = "id"
	) String id;

	private @Temporal(
			TIMESTAMP
	) @Column(
			name = "date",
			nullable = false,
			updatable = false
	) Date date;

	private @Column(
			name = "author",
			nullable = false,
			updatable = false
	) @ManyToOne(
			cascade = ALL
	) User author;

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
}
