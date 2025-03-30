package ec.com.technoloqie.document.loader.api.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="SCBTFILE")
public class FileEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="FILEID",nullable=false, unique=true)
	private Integer id;
	
	@Column(name="FILENAME", nullable = false)
	private String fileName;
	
	@Column(name="FILETYPE", nullable = false)
	private String fileType;
	
	@Column(name="FILEPATH", nullable = false)
	private String filePath;
	
	@Column(name="GCPURL")
	private String gcpUrl; 
	@Column(name="GCPFILENAME")
	private String gcpFilename; 
	
	//@Column(name="ASSISTANTID")
	//private Integer assistantId;
	
	@NotEmpty(message ="no puede estar vacio")
	@NotNull(message = "no puede ser nulo")
	//@CreatedBy
	@Column(name="CREATEDBY",nullable=false)
	private String createdBy;
	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATEDDATE",nullable=false)
	private Date createdDate;
	@LastModifiedBy
	@Column(name="MODIFIEDBY")
	private String modifiedBy;
	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFIEDDATE")
	private Date modifiedDate;
	
	@ManyToOne
	@JoinColumn(name="ASSISTANTID",nullable=false)
	private Assistant assistant;
	
	@PrePersist 
	public void prePersist() {
		this.createdDate = new Date();
	}
}
