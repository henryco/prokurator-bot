package dev.tindersamurai.prokurator.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
