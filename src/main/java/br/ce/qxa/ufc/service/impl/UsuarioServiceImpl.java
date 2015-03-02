package br.ce.qxa.ufc.service.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.model.Usuario;
import br.ce.qxa.ufc.repositorio.UsuarioRepositorio;
import br.ce.qxa.ufc.service.UsuarioService;

@Named
public class UsuarioServiceImpl extends GenericServiceImpl<Usuario> implements UsuarioService {
	
	@Inject
	private UsuarioRepositorio usuarioRespositorio;

	

	public Usuario getUsuarioByLogin(String login) {
		return usuarioRespositorio.getUsuarioByLogin(login);
	}



	@Override
	public void CadastraIdAmigos(List<TwitterUsuarioId> amigosId,	Integer idUsuario) {
		usuarioRespositorio.CadastraIdAmigos(amigosId, idUsuario);		
	}



	@Override
	public void CadastraIdsParaRecomadacao(List<TwitterUsuarioId> listaParaRecomendacao, Integer idUsuario) {
		usuarioRespositorio.CadastraIdsParaRecomadacao(listaParaRecomendacao, idUsuario);
		
	}

}
