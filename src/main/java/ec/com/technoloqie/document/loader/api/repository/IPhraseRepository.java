package ec.com.technoloqie.document.loader.api.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.model.Phrase;

public interface IPhraseRepository extends JpaRepository<Phrase, Integer>{
	
	Collection <Phrase> findByIntentId(int intentId);
}
