package acme.entities.chimpums;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Chimpum extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Column(unique = true)
	// TODO the pattern may change to something like ^\\w{3}-yymm:dd$
	@Pattern(regexp = "^\\w{3}-\\d{4}:\\d{2}$")
	protected String code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date creationDate; // TODO the attribute name may change

	@NotBlank
	@Length(max = 100)
	protected String title; // TODO the attribute name may change

	@NotBlank
	@Length(max = 255)
	protected String description; // TODO the attribute name may change

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date startDate;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date endDate;

	@NotNull
	@Valid
	protected Money budget; // TODO the attribute name may change

	@URL
	protected String info; // TODO the attribute name may change
}
