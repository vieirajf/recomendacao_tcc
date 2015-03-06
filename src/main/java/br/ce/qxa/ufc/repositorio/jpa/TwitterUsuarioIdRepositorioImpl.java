package br.ce.qxa.ufc.repositorio.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Named;

import br.ce.qxa.ufc.enumeration.QueryType;
import br.ce.qxa.ufc.model.TwitterUsuarioId;
import br.ce.qxa.ufc.repositorio.TwitterUsuarioIdRepositorio;

@Named
public class TwitterUsuarioIdRepositorioImpl extends GenericRepositoryImpl<TwitterUsuarioId> implements TwitterUsuarioIdRepositorio{

	@Override
	public TwitterUsuarioId getTwitterUsuarioIdByIdTwitter(Long idTwitter) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("idTwitter", idTwitter);
		List<TwitterUsuarioId> result = find(QueryType.JPQL, "from TwitterUsuarioId where idTwitter = :idTwitter", params);
		if(result != null && !result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

}
