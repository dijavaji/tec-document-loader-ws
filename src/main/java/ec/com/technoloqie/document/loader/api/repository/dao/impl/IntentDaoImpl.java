package ec.com.technoloqie.document.loader.api.repository.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.Assistant;
import ec.com.technoloqie.document.loader.api.model.Intent;
import ec.com.technoloqie.document.loader.api.model.Phrase;
import ec.com.technoloqie.document.loader.api.model.Response;
import ec.com.technoloqie.document.loader.api.repository.dao.IIntentDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class IntentDaoImpl implements IIntentDao{
	
	private EntityManager em;
	
	public IntentDaoImpl(EntityManager em) {
		this.em = em;
	}
	
	@Override
	public Intent getIntentById(Integer id) throws DocumentLoaderException{
		Intent result = null;
		log.info("busco getIntentById ");
		try {
			CriteriaBuilder cb = this.em.getCriteriaBuilder();
			CriteriaQuery<Intent> query = cb.createQuery(Intent.class);
			Root<Intent> root = query.from(Intent.class);
			Join<Intent, Phrase> joinPhrase = root.join("phrases", JoinType.INNER);
			Join<Phrase, Response> joinResponse = joinPhrase.join("responses", JoinType.INNER);
			
			List <Predicate>filterPredicates = new ArrayList<>();
			filterPredicates.add(cb.and(cb.equal(root.get("id"), id)));
			filterPredicates.add(cb.and(cb.equal(root.get("status"), Boolean.TRUE)));
			filterPredicates.add(cb.and(cb.equal(joinPhrase.get("status") , Boolean.TRUE)));
			filterPredicates.add(cb.and(cb.equal(joinResponse.get("status") , Boolean.TRUE)));
			//filterPredicates.add(cb.equal(root.get("stock.country.id"), countryId));
			//filterPredicates.add(cb.and(cb.equal(join.get("id") , root.get("stock"))));
			//filterPredicates.add(cb.equal(join.get("email"), mail));
			
			query.where(filterPredicates.toArray(new Predicate[0]));
			result = em.createQuery(query).getSingleResult();
			log.info("consulta getIntentById " + result.getPhrases().size());
			
		
		}catch(Exception e) {
			log.error("Error al momento de consultar Intenciones " ,e);
			throw new DocumentLoaderException("Error al momento de consultar Intenciones " ,e);
		}
		return result;
	}

	@Override
	@Transactional(readOnly = true)
	public List <Intent> findIntentsKnowlegeByAssistant(Integer assistantId) throws DocumentLoaderException {
		List <Intent>  result = null;
		log.info("busco findIntentsKnowlegeByAssistant ");
		try {
			CriteriaBuilder cb = this.em.getCriteriaBuilder();
			CriteriaQuery<Intent> query = cb.createQuery(Intent.class);
			Root<Intent> root = query.from(Intent.class);
			Join<Intent, Phrase> joinPhrase = root.join("phrases", JoinType.INNER);
			Join<Phrase, Response> joinResponse = joinPhrase.join("responses", JoinType.INNER);
			Assistant assistant = new Assistant();
			assistant.setId(assistantId);
			assistant.setStatus(Boolean.TRUE);
			List <Predicate>filterPredicates = new ArrayList<>();
			filterPredicates.add(cb.and(cb.equal(root.get("assistant"), assistant)));
			filterPredicates.add(cb.and(cb.equal(root.get("status"), Boolean.TRUE)));
			filterPredicates.add(cb.and(cb.equal(joinPhrase.get("status") , Boolean.TRUE)));
			filterPredicates.add(cb.and(cb.equal(joinResponse.get("status") , Boolean.TRUE)));
			
			query.where(filterPredicates.toArray(new Predicate[0]));
			result = em.createQuery(query).getResultList();
			log.info("consulta IntentsKnowlegeByAssistant " + result.size());
			
		}catch(Exception e) {
			log.error("Error al momento de consultar conocimiento de la intencion " ,e);
			throw new DocumentLoaderException("Error al momento de consultar conocimiento de la intencion " ,e);
		}
		return result;
	}

}
