package acme.testing.inventor.chimpum;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorChimpumDeleteTest extends TestHarness {

	// Lifecycle management ---------------------------------------------------

	// Test cases -------------------------------------------------------------

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/chimpum/delete-positive.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTest(final String code, final String creationDate, final String title, final String description,
			final String startDate, final String endDate, final String budget, final String info,
			final String codeNext) {

		// We will delete the first Chimpum, ordering by ascendent code. To do this we
		// will delete the first entry on the list that appears, and check that the new
		// first entry was the entry which was under the deleted one.
		super.signIn("inventor1", "inventor1");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(3, "asc");

		super.clickOnListingRecord(0);

		super.checkFormExists();

		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("creationDate", creationDate);
		super.checkInputBoxHasValue("title", title);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("startDate", startDate);
		super.checkInputBoxHasValue("endDate", endDate);
		super.checkInputBoxHasValue("budget", budget);
		super.checkInputBoxHasValue("info", info);

		super.clickOnSubmit("Delete");

		super.clickOnMenu("Inventor", "List the chimpums of my items");
		super.checkListingExists();
		super.sortListing(3, "asc");

		super.clickOnListingRecord(0);
		super.checkFormExists();

		super.checkInputBoxHasValue("code", codeNext);
		super.signOut();
	}

	@Test
	@Order(20)
	public void negativeTest() {
		// As this is a test that does not involve modifying any form, we do not have
		// negative cases to test, so this function will be blank.
	}

	@Test
	@Order(30)
	public void hackingTest() {
		// As the framework doesn't support this hacking feature we will have to perform
		// this manually

		// 1) Start by initiating the Acme Toolkits project
		// 2) Log in as inventor2 (username=inventor2 password=inventor2)
		// 3) Go to the inventor menu and click on "List the chimpums of my items"
		// 4) Click on the Chimpum
		// 5) Copy the link "inventor/chimpum/show?id=XXX" where XXX is the associated
		// id of the chimpum
		// 6) Log out
		// 7) Log in as inventor1 (username=inventor1 password=inventor1)
		// 8) Navigate to the URL "inventor/chimpum/delete?id=XXX"
		// 9) Check that a panic happens
	}

}
