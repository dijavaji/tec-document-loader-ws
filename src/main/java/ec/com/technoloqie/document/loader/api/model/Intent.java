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
@Table(name="SCBTINTENT")
public class Intent implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="INTENTID",nullable=false, unique=true)
	private Integer id;
	
	@Column(name="NAME",nullable=false)
    private String name; 
	
	@Column(name="DESCRIPTION")
	private String description;
	
	@Column(name="ASSISTANTID")
	private Integer assistantId;
	
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
	
	/*@ManyToOne
	@JoinColumn(name="ACCTYPID",nullable=false)
	private AccountType accountType;
	
	@ManyToOne
	@JoinColumn(name="CUSTOMERID",nullable=false)
	private Customer customer;
	*/
	@OneToMany(mappedBy = "intent", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	//@JoinColumn(name = "INTENTID")
	private Collection<Phrase> phrases;
	
    @PrePersist 
	public void prePersist() {
		setCreatedDate(new Date());
		setStatus(Boolean.TRUE);
	}

	private static final long serialVersionUID = -979071489122757786L;

}
