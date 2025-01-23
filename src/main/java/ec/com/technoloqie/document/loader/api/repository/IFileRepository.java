package ec.com.technoloqie.document.loader.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.model.FileEntity;

public interface IFileRepository extends JpaRepository<FileEntity, Integer>{

}
