package br.ce.qxa.ufc.service;

import java.util.List;

import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;

public interface UsuarioService extends GenericService<Usuario> {
	
	public abstract Usuario getUsuarioByLogin(String login);

	public abstract Usuario CadastraIdAmigos(List<TwitterUsuarioId> amigosId,Integer idUsuario);

	public abstract Usuario CadastraIdsParaRecomadacao(List<TwitterUsuarioId> idAmigos2, Usuario usuario);

}
