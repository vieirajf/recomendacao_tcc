package br.ce.qxa.ufc.repositorio;

import br.ce.qxa.ufc.model.TwitterUsuarioId;

public interface TwitterUsuarioIdRepositorio  extends GenericRepository<TwitterUsuarioId>{
	public abstract TwitterUsuarioId getTwitterUsuarioIdByIdTwitter(Long idTwitter);
	
}
