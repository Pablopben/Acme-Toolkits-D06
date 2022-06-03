package acme.features.patron.patronage;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ExchangeService;
import acme.entities.patronages.Patronage;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.datatypes.Money;
import acme.framework.services.AbstractListService;
import acme.roles.Patron;

@Service
public class PatronPatronageListMineService implements AbstractListService<Patron, Patronage>{

	// Internal state ---------------------------------------------------------
	
	@Autowired
	protected PatronPatronageRepository repository;
	
	@Autowired
	protected ExchangeService exchangeService;
	
	@Override
	public boolean authorise(final Request<Patronage> request) {
		assert request != null;

		return true;
	}

	@Override
	public Collection<Patronage> findMany(final Request<Patronage> request) {
		assert request != null;
		
		final int patronId = request.getPrincipal().getActiveRoleId();
		
		return this.repository.findPatronagesByPatronId(patronId);
	}

	@Override
	public void unbind(final Request<Patronage> request, final Patronage entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "status", "code", "legalStuff", "budget", "creationDate", "startDate", "endDate", "info");
		
		final Boolean isPublished = !entity.isDraftMode();
		model.setAttribute("published", isPublished);
		
		final Money exchange=this.exchangeService.getExchange(entity.getBudget());
		model.setAttribute("exchange", exchange);
	}
}
