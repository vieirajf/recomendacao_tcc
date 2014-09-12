package br.ce.qxa.ufc.repositorio;

import br.ce.qxa.ufc.model.Usuario;

public interface UsuarioRepositorio  extends GenericRepository<Usuario>{
	public abstract Usuario getUsuarioByLogin(String login);

}
