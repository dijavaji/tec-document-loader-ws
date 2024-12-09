package ec.com.technoloqie.document.loader.api.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.util.CollectionUtils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "knowledge_data_mart")
public class KnowledgeData {
	
	@Id
	@BsonProperty("_id")
	private ObjectId id;
	@Field("metadata")
	private Map<String, Object> metadata;
	
	/**
	 * Document content.
	 */
	@Field("content")
	private String content;
	//@Field("media")
	//private List<Media> media;

	/**
	 * Embedding of the document. Note: ephemeral field.
	 */
	//@JsonProperty(index = 100)
	@Field("embedding")
	private List<Float> embedding = new ArrayList<>();
	
	public static KnowledgeData fromDomainModel(org.springframework.ai.document.Document doc) {
		KnowledgeData documentBase= new KnowledgeData();
		documentBase.setContent(doc.getText());
		//TODO comentado error doc.getEmbedding() en snapshot
		//List <Double> embeddings= (List<Double>) CollectionUtils.arrayToList(doc.getEmbedding());
		List <Double> embeddings= (List<Double>) CollectionUtils.arrayToList(null);
		//documentBase.setEmbedding(embeddings);
		//documentBase.setMedia(doc.getMedia(null));
		documentBase.setMetadata(doc.getMetadata());
		return  documentBase;
	}
	
	/*public PickUpTransportEvent toDomainModel() {
		return new PickUpTransportEvent(
				this.id,
				this.pickupTransportQsId,
				this.eventPickupTransportTypeCode,
				this.comment, 
				this.createdByUser,
				this.createdDate,
				this.modifiedByUser,
				this.modifiedDate,
				null
				);
	}*/

}
