package br.ufscar.dc.dsw1.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@SuppressWarnings("serial")
@Entity
@Setter
@Getter
@Table(name = "FileDB")
public class FileEntity extends AbstractEntity<Long> {

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 30)
	private String type;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "carro_id")
	private Carro carro;

	@Lob
	private byte[] data;

	public FileEntity() {
	}

	public FileEntity(String name, String type, byte[] data, Carro carro) {
		this.carro = carro;
		this.name = name;
		this.type = type;
		this.data = data;
	}

	public boolean isImage() {
		return this.type.contains("image");
	}
}