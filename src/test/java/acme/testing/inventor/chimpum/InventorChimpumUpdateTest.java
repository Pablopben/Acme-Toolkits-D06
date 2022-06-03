package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumUpdateTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String code, final String title, final String description, final String startDate,
			final String endDate, final String budget, final String info) {

		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(1, "asc");

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Update");

		super.checkListingExists();
		super.sortListing(1, "asc");
		super.checkColumnHasValue(0, 2, title);
		super.checkColumnHasValue(0, 3, startDate);

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("info", info);

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/update-negative.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final String code, final String title, final String description, final String startDate,
			final String endDate, final String budget, final String info) {

		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(0, "asc");

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.fillInputBoxIn("code", code);
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("description", description);
		super.fillInputBoxIn("startDate", startDate);
		super.fillInputBoxIn("endDate", endDate);
		super.fillInputBoxIn("budget", budget);
		super.fillInputBoxIn("info", info);

		super.clickOnSubmit("Update");

		super.checkErrorsExist();

		super.signOut();

	}

	@Test
	@Order(30)
	public void hackingTest() {
		// As the framework doesn't support this hacking feature we will have to perform
		// this manually

		// 1) Start by initiating the Acme Toolkits project
		// 2) Log in as inventor2 (username=inventor2 password=inventor2)
		// 3) Go to the inventor menu and click "List my items"
		// 4) Click on the item which has the associated chimpum
		// 5) Click on "See chimpum"
		// 6) Copy the link "inventor/chimpum/show?id=XXX" where XXX is the associated
		// id of the chimpum
		// 7) Log out
		// 8) Log in a different inventor, for example inventor1 (username=inventor1
		// password=inventor1)
		// 9) Navigate to the URL inventor/chimpum/update?id=XXX
		// 10) Check that a panic happens

		// 11) Log out
		// 12) Navigate to the URL inventor/chimpum/update?id=XXX
		// 13) Check that a panic happens
	}

}
