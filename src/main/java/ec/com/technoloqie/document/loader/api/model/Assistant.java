package ec.com.technoloqie.document.loader.api.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
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
@Table(name="SCBTASSISTANT")
public class Assistant implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="ASSISTANTID",nullable=false, unique=true)
	private Integer id;
	
	@Column(name="NAME",nullable=false, unique=true)
    private String name; 
	
	@Column(name="LABEL")
    private String label; 
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="LANGUAGEID")
	private Integer languageId;
	
	@Column(name="ASSISTANTTYPEID")
	private Integer assistantTypeId;
	
	@NotEmpty(message ="no puede estar vacio")
	@NotNull(message = "no puede ser nulo")
	@Column(name="CREATEDBY",nullable=false )
	private String createdBy;
	
	@Column(name="CREATEDDATE",nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@Column(name="MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name="MODIFIEDDATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedDate;
	
	@Column(name="STATUS")
	private Boolean status;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "ASSISTANTID")
	private Collection<FileEntity> files;
	
	@OneToMany(mappedBy = "assistant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "INTENTID")
	private Collection<Intent> intents;
	
	
	@PrePersist 
	public void prePersist() {
		createdDate = new Date();
		status = Boolean.TRUE;
	}
}
