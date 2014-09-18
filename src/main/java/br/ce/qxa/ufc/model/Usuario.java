package br.ce.qxa.ufc.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.security.crypto.codec.Hex;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "id", "login" }))
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String login;

	@Column(nullable = false)
	private String senha;

	@Column(nullable = false)
	private boolean habilitado;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "papel_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "papel_id")
	)
	private List<Papel> papeis;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "twitterusuarioid_usuario", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "twitterUsuarioId_id")
	)
	private List<TwitterUsuarioId> amigosId;

	
	@OneToOne
	@JoinColumn(name = "autorizacaoTwitter_id")
	private AutorizacaoTwitter autorizacaoTwitter;

	public Usuario() {
		super();
	}

	public Usuario(String login, String senha) {
		super();
		this.senha = senha;
	}

	
	public List<TwitterUsuarioId> getAmigosId() {
		return amigosId;
	}

	public void setAmigosId(List<TwitterUsuarioId> amigosId) {
		this.amigosId = amigosId;
	}

	public AutorizacaoTwitter getAutorizacaoTwitter() {
		return autorizacaoTwitter;
	}

	public void setAutorizacaoTwitter(AutorizacaoTwitter autorizacaoTwitter) {
		this.autorizacaoTwitter = autorizacaoTwitter;
	}

	public String sha256(String original) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(original.getBytes());
		byte[] digest = md.digest();

		return String.copyValueOf(Hex.encode(digest));
		// return new String(Hex.encodeHexString(digest));
	}

	public String getPassword() {
		return senha;
	}

	public void setPassword(String password) {
		try {
			this.senha = sha256(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public boolean isHabilitado() {
		return habilitado;
	}

	public void setHabilitado(boolean habilitado) {
		this.habilitado = habilitado;
	}

	public List<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		try {
			this.senha = sha256(senha);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Usuario) {
			Usuario other = (Usuario) obj;
			if (other != null && other.getId() != null && this.id != null
					&& other.getId().equals(this.id)) {
				return true;
			}
		}
		return false;
	}

}
