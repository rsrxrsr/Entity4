package com.rsr.file;

import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="Archivos")
public class File {

	public File() {
		super();
	}

	public File(String name, String type, byte[] picByte, Long fk, Date creation) {
		this.name = name;
		this.type = type;
		this.fk = fk;
		this.creation = creation;
		this.picByte = picByte;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String type;
	private Long fk;
	private Date creation;
	@Column(name = "picByte", length = 1048576) //image bytes can have large lengths
	private byte[] picByte;
		
}