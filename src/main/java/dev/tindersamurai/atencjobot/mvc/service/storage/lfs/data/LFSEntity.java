package dev.tindersamurai.atencjobot.mvc.service.storage.lfs.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
public class LFSEntity {

	private @Id @Column(
			name = "uid"
	) String id;

	private @Column(
			name = "path",
			nullable = false,
			updatable = false
	) String path;

}
