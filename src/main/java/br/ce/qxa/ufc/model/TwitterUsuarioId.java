package br.ce.qxa.ufc.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TwitterUsuarioId {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column()
	private Long IdTwitter;
	
	public TwitterUsuarioId() {
		super();
	}
		
	public TwitterUsuarioId(Long id, Long idTwitter) {
		super();
		Id = id;
		IdTwitter = idTwitter;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public Long getIdTwitter() {
		return IdTwitter;
	}

	public void setIdTwitter(Long idTwitter) {
		IdTwitter = idTwitter;
	}

	
	
	
	

}
