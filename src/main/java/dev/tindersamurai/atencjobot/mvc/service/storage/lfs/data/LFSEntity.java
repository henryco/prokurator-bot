package dev.tindersamurai.atencjobot.mvc.service.storage.lfs.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "lfs_file")
public class LFSEntity {

	private @Id @Column(
			name = "uid"
	) String id;

	private @Column(
			name = "path",
			updatable = false
	) String path;

}
