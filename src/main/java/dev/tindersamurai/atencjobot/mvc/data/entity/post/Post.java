package dev.tindersamurai.atencjobot.mvc.data.entity.post;

import dev.tindersamurai.atencjobot.mvc.data.entity.TextChannel;
import dev.tindersamurai.atencjobot.mvc.data.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.InheritanceType.*;
import static javax.persistence.TemporalType.TIMESTAMP;


@AllArgsConstructor
@NoArgsConstructor
@Data @Entity @Inheritance(
		strategy = TABLE_PER_CLASS
) /* package */ abstract class Post {

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

	private @ManyToOne(
			cascade = ALL
	) User author;

	private @ManyToOne(
			cascade = ALL
	) TextChannel channel;
}
