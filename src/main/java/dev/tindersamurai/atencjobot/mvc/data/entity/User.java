package dev.tindersamurai.atencjobot.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.Set;

@Data @Entity
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private @Id @Column(
			name = "id"
	) String id;

	private @Column(
			name = "name",
			nullable = false
	) String name;

	private @Column(
			name = "avatar"
	) String avatar;

}
