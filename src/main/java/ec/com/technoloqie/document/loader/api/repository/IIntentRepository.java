package ec.com.technoloqie.document.loader.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.model.Intent;

public interface IIntentRepository extends JpaRepository<Intent, Integer>{

}
