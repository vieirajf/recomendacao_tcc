package br.ce.qxa.ufc.service;

import br.ce.qxa.ufc.model.TwitterUsuarioId;

public interface TwitterUsuarioIdService extends GenericService<TwitterUsuarioId> {
	public abstract TwitterUsuarioId getTwitterUsuarioIdByIdTwitter(Long idTwitter);
	}
