package acme.entities.quantities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import acme.entities.items.Item;
import acme.entities.toolkits.Toolkit;
import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Quantity extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@Min(1)
	protected int number;

	// Derived attributes ----------------------------------------------------

	// Relationships ----------------------------------------------------

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Item item;

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	protected Toolkit toolkit;
}
