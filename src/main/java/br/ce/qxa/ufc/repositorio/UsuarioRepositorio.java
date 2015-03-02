package br.ce.qxa.ufc.repositorio;

import java.util.List;

import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;

public interface UsuarioRepositorio  extends GenericRepository<Usuario>{
	public abstract Usuario getUsuarioByLogin(String login);
	public abstract void CadastraIdAmigos(List<TwitterUsuarioId> amigosId, Integer idUsuario);
	public abstract void CadastraIdsParaRecomadacao(List<TwitterUsuarioId> listaParaRecomendacao,Integer idUsuario);
}
