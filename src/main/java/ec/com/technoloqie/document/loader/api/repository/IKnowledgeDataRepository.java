package ec.com.technoloqie.document.loader.api.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import ec.com.technoloqie.document.loader.api.model.KnowledgeData;

public interface IKnowledgeDataRepository extends MongoRepository<KnowledgeData, ObjectId>{

}
