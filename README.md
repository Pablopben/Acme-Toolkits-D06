# Acme Toolkits Individual Control Check

This is the Acme-Toolkits' individual control check.

Regarding this deliverable, some inconsistencies have been detected in the requirements. The assumptions that have been made, since the requirements are ambiguous, are these:

* Regarding the **requirement 1)**, I have assumed that we can only add a chimpum to an item if this is already published. I perceieve the chimpum as an "a posteriori" entity. This could be, for example, an additional information for an already published item —which we are unable to edit—, in case the patrons did not understand exactly the purpose of the item. 

* Regarding the **requirement 2)**, I am asked to update the system dashboard. However, this is not a dashboard that has been implemented before. I have assumed that this requirement refers to the administrator dashboard, which I have updated to fulfill the requirement.

* Regarding the **requirement 3)**, there exists the possibility that the role who operates with chimpums are either "Inventor" or "Patron", however, the "Patron" role is definitely not possible to operate with chimpums, as the "Patron" has nothing to do with items, which are crucial to the creation of a chimpum, as a chimpum has to be associated with an item, therefore, the only possible role is "Inventor" for the feature.

* Regarding the **requirement 5)**, more precisely in the positive test for `InventorChimpumCreateTest.java`, the code is related to the creation date. This summons a big problem: if someone tests this file, if it is not the same day the code of the chimpum relates to. Right now, and as we are advised in a "Heads up!", we do not have any tool to modify the virtual clock of the application, so we can not modify the creation date in order to make it in accordance with the code that we provide in the test data. Therefore, this test will not succeed if ran after the control check day (2022/06/02), as the code introduced will not fulfill constraint that it has to be built based on the creation date of the chimpum.

* Regarding the **requirement 6)**, in the performance report, a comparison between the performances of two computers should be carried out. However, as this is an individual task, there can not be such comparison. Instead of this comparison, I have taken account a difference of plus/minus 10% in the performance of my own computer, as we have discussed along the course for these situations where a single machine is available.
