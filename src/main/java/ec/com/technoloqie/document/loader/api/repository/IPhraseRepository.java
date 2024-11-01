package ec.com.technoloqie.document.loader.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.model.Phrase;

public interface IPhraseRepository extends JpaRepository<Phrase, Integer>{

}
