package dev.tindersamurai.atencjobot.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class TextChannel {

	private @Id @Column(
			name = "id"
	) String id;

	private @Column(
			name = "name",
			nullable = false
	) String name;

	private @Column(
			name = "guild",
			nullable = false
	) @ManyToOne(
			cascade = ALL
	) Guild guild;

	private @Column(
			name = "category"
	) String category;
}
