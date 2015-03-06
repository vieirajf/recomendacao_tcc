package br.ce.qxa.ufc.service.impl;

import javax.inject.Inject;
import javax.inject.Named;

import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.repositorio.TwitterUsuarioIdRepositorio;
import br.ce.qxa.ufc.service.TwitterUsuarioIdService;

@Named
public class TwitterUsuarioIdServiceImpl extends GenericServiceImpl<TwitterUsuarioId> implements TwitterUsuarioIdService {
	
	@Inject
	private TwitterUsuarioIdRepositorio twitterUsuarioIdRespositorio;

	@Override
	public TwitterUsuarioId getTwitterUsuarioIdByIdTwitter(Long idTwitter) {
		return twitterUsuarioIdRespositorio.getTwitterUsuarioIdByIdTwitter(idTwitter);
	}

	

	
}
