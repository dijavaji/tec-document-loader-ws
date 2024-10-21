package ec.com.technoloqie.document.loader.api.model;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name="SCBTPHRASE")
public class Phrase implements Serializable{
	
	private static final long serialVersionUID = -5443070484930045796L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="PHRASEID",nullable=false, unique=true)
	private Integer id;
	
	@Column(name="PHRASE",nullable=false )
	private String phrase;
	
	//@Column(name="INTENTID", nullable=false)
	//private Integer intentId;
	
	@NotEmpty(message ="no puede estar vacio")
	@NotNull(message = "no puede ser nulo")
	@Column(name="CREATEDBY",nullable=false )
	private String createdBy;
	
	@Column(name="CREATEDDATE",nullable=false)
	@Temporal(TemporalType.DATE)
	private Date createdDate;
	
	@Column(name="MODIFIEDBY")
	private String modifiedBy;
	
	@Column(name="MODIFIEDDATE")
	@Temporal(TemporalType.DATE)
	private Date modifiedDate;
	
	@Column(name="STATUS")
	private Boolean status;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "INTENTID", nullable = false)
	private Intent intent;
	
	@PrePersist 
	public void prePersist() {
		createdDate = new Date();
		status = Boolean.TRUE;
	}
}
