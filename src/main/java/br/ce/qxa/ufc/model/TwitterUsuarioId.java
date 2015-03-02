package br.ce.qxa.ufc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "id" }))
public class TwitterUsuarioId {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(unique=true )
	private Long idTwitter;
	
	public TwitterUsuarioId() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Long getIdTwitter() {
		return idTwitter;
	}

	public void setIdTwitter(Long idTwitter) {
		this.idTwitter = idTwitter;
	}

		
	
}
