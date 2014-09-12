package br.ce.qxa.ufc.service;

import br.ce.qxa.ufc.model.Usuario;


public interface UsuarioService  extends GenericService<Usuario>{
	public abstract Usuario getUsuarioByLogin(String login);

}
