package dev.tindersamurai.atencjobot.mvc.data.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
@NoArgsConstructor
@AllArgsConstructor
public class Configuration {

	private @Id @Column(
			name = "name",
			nullable = false,
			updatable = false,
			unique = true
	) String name;


	private @Column(
			name = "value"
	) String value;


	private @Column(
			name = "type",
			nullable = false,
			updatable = false
	) Class<?> type;

}
