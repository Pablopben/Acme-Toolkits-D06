package acme.features.inventor.chimpum;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.components.ExchangeService;
import acme.entities.chimpums.Chimpum;
import acme.entities.items.Item;
import acme.entities.items.ItemType;
import acme.framework.components.models.Model;
import acme.framework.controllers.Errors;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractCreateService;
import acme.roles.Inventor;

@Service
public class InventorChimpumCreateService implements AbstractCreateService<Inventor, Chimpum> {

	@Autowired
	protected InventorChimpumRepository repository;

	@Autowired
	protected ExchangeService exchangeService;

	@Override
	public boolean authorise(final Request<Chimpum> request) {
		int principalId;
		int itemId;
		Item item;
		boolean result;

		result = true;

		principalId = request.getPrincipal().getActiveRoleId();
		itemId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(itemId);

		// Check that the item is not a component/tool (depending on the exam)
		if (item.getType().equals(ItemType.COMPONENT))
			result = false;

		// Check that there is no chimpum associated to the item
		if (this.repository.findOneChimpumByItemId(itemId) != null)
			result = false;

		// Check that the item has been published. We make this assumption (README.md)
		if (item.isDraftMode())
			result = false;

		// Check that the inventor creating the chimpum is the one who owns the item
		if (item.getInventor().getId() != principalId)
			result = false;

		return result;
	}

	@Override
	public void bind(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		request.bind(entity, errors, "code", "title", "description", "startDate", "endDate", "budget", "info");
	}

	@Override
	public void unbind(final Request<Chimpum> request, final Chimpum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "code", "creationDate", "title", "description", "startDate", "endDate", "budget",
				"info");

		int itemId;
		Item item;

		itemId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(itemId);

		model.setAttribute("itemName", item.getName());
		model.setAttribute("itemId", item.getId());
	}

	@Override
	public Chimpum instantiate(final Request<Chimpum> request) {
		Chimpum result;
		Date creationDate;

		creationDate = new Date(System.currentTimeMillis() - 1);

		result = new Chimpum();
		result.setCreationDate(creationDate);

		return result;
	}

	@Override
	public void validate(final Request<Chimpum> request, final Chimpum entity, final Errors errors) {
		if (!errors.hasErrors("startDate")) {
			// The chimpum must start, at least, one month after its creation
			Calendar creationDate;
			Calendar startDate;

			creationDate = Calendar.getInstance();
			creationDate.setTime(entity.getCreationDate());

			startDate = Calendar.getInstance();
			startDate.setTime(entity.getStartDate());

			startDate.add(Calendar.MONTH, -1);

			errors.state(request, startDate.after(creationDate), "startDate",
					"inventor.chimpum.form.error.start-date-too-early");
		}

		if (!errors.hasErrors("startDate") && !errors.hasErrors("endDate")) {
			// The chimpum must be at least one week long
			Calendar startDate;
			Calendar endDate;

			startDate = Calendar.getInstance();
			startDate.setTime(entity.getStartDate());

			endDate = Calendar.getInstance();
			endDate.setTime(entity.getEndDate());

			endDate.add(Calendar.WEEK_OF_MONTH, -1);

			errors.state(request, endDate.after(startDate), "endDate",
					"inventor.chimpum.form.error.end-date-too-early");
		}

		if (!errors.hasErrors("budget")) {
			Double budget;
			String currency;

			budget = entity.getBudget().getAmount();
			currency = entity.getBudget().getCurrency();

			errors.state(request, this.repository.isAcceptedCurrency(currency), "budget",
					"inventor.chimpum.form.error.not-accepted-currency");
			errors.state(request, budget > 0.0, "budget", "inventor.chimpum.form.error.negative-price");
		}

		if (!errors.hasErrors("code")) {
			String code;
			Date creationDate;
			String onlyNumbers;

			String creationDateOnlyNumbers;

			// ^\\w{3}-yymm:dd$ ==> ABC-2206:02 ==> 2022/06/02

			code = entity.getCode();
			creationDate = entity.getCreationDate();

			// ABC-2206:02 => 220602
			onlyNumbers = code.replaceAll("\\D", "");

			// Creation date in the same format and order (220602)
			creationDateOnlyNumbers = new SimpleDateFormat("yyMMdd").format(creationDate);

			errors.state(request, onlyNumbers.equals(creationDateOnlyNumbers), "code",
					"inventor.chimpum.form.error.invalid-code-date");

			// Check that there is not a chimpum with the same code
			Chimpum existing;
			Integer id;

			existing = this.repository.findOneChimpumByCode(entity.getCode());
			id = request.getModel().getInteger("id");

			errors.state(request, existing == null || existing.getId() == id, "code",
					"inventor.chimpum.form.error.duplicated");
		}
	}

	@Override
	public void create(final Request<Chimpum> request, final Chimpum entity) {
		int itemId;
		Item item;

		this.repository.save(entity);

		itemId = request.getModel().getInteger("masterId");
		item = this.repository.findOneItemById(itemId);
		item.setChimpum(entity);

		this.repository.save(item);
	}
}
