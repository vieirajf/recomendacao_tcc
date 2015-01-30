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
	private Integer Id;
	
	@Column()
	private Integer IdTwitter;
	
	public TwitterUsuarioId() {
		super();
	}
		
	public TwitterUsuarioId(Integer id, Integer idTwitter) {
		super();
		Id = id;
		IdTwitter = idTwitter;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getIdTwitter() {
		return IdTwitter;
	}

	public void setIdTwitter(Integer idTwitter) {
		IdTwitter = idTwitter;
	}

}
