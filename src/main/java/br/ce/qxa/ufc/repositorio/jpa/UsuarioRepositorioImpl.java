package br.ce.qxa.ufc.repositorio.jpa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import br.ce.qxa.ufc.enumeration.QueryType;
import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.repositorio.UsuarioRepositorio;

@Named
public class UsuarioRepositorioImpl extends GenericRepositoryImpl<Usuario> implements UsuarioRepositorio{

	public Usuario getUsuarioByLogin(String login) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("login", login);
		List<Usuario> result = find(QueryType.JPQL, "from Usuario where login = :login", params);
		if(result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

	@Override
	public void CadastraIdAmigos(List<TwitterUsuarioId> amigosId, Integer idUsuario) {
		Usuario usuario = find(Usuario.class, idUsuario);
		List<TwitterUsuarioId> result = new ArrayList<TwitterUsuarioId>();
		result = usuario.getAmigosId();
		for (TwitterUsuarioId twitterUsuarioId : amigosId) {
			
				if (!result.contains(twitterUsuarioId)) {
					
					result.add(twitterUsuarioId);
					System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
				}
		}
		usuario.setAmigosId(result);
		
		save(usuario);
		
	}

	@Override
	public void CadastraIdsParaRecomadacao(List<TwitterUsuarioId> listaParaRecomendacao, Integer idUsuario) {
		Usuario usuario = find(Usuario.class, idUsuario);
		List<TwitterUsuarioId> result = usuario.getListaParaRecomendacao();
		for (TwitterUsuarioId twitterUsuarioId : listaParaRecomendacao) {
			if (!result.contains(twitterUsuarioId)){
				usuario.getListaParaRecomendacao().add(twitterUsuarioId);
			}
		}
		update(usuario);
		
	}

}
