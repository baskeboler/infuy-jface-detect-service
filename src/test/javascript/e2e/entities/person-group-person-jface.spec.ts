import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PersonGroupPerson e2e test', () => {

    let navBarPage: NavBarPage;
    let personGroupPersonDialogPage: PersonGroupPersonDialogPage;
    let personGroupPersonComponentsPage: PersonGroupPersonComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PersonGroupPeople', () => {
        navBarPage.goToEntity('person-group-person-jface');
        personGroupPersonComponentsPage = new PersonGroupPersonComponentsPage();
        expect(personGroupPersonComponentsPage.getTitle())
            .toMatch(/Person Group People/);

    });

    it('should load create PersonGroupPerson dialog', () => {
        personGroupPersonComponentsPage.clickOnCreateButton();
        personGroupPersonDialogPage = new PersonGroupPersonDialogPage();
        expect(personGroupPersonDialogPage.getModalTitle())
            .toMatch(/Create or edit a Person Group Person/);
        personGroupPersonDialogPage.close();
    });

    it('should create and save PersonGroupPeople', () => {
        personGroupPersonComponentsPage.clickOnCreateButton();
        personGroupPersonDialogPage.setPersonIdInput('personId');
        expect(personGroupPersonDialogPage.getPersonIdInput()).toMatch('personId');
        personGroupPersonDialogPage.setNameInput('name');
        expect(personGroupPersonDialogPage.getNameInput()).toMatch('name');
        personGroupPersonDialogPage.setDescriptionInput('description');
        expect(personGroupPersonDialogPage.getDescriptionInput()).toMatch('description');
        personGroupPersonDialogPage.personGroupSelectLastOption();
        personGroupPersonDialogPage.save();
        expect(personGroupPersonDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PersonGroupPersonComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-person-group-person-jface div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PersonGroupPersonDialogPage {
    modalTitle = element(by.css('h4#myPersonGroupPersonLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    personIdInput = element(by.css('input#field_personId'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));
    personGroupSelect = element(by.css('select#field_personGroup'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setPersonIdInput = function(personId) {
        this.personIdInput.sendKeys(personId);
    };

    getPersonIdInput = function() {
        return this.personIdInput.getAttribute('value');
    };

    setNameInput = function(name) {
        this.nameInput.sendKeys(name);
    };

    getNameInput = function() {
        return this.nameInput.getAttribute('value');
    };

    setDescriptionInput = function(description) {
        this.descriptionInput.sendKeys(description);
    };

    getDescriptionInput = function() {
        return this.descriptionInput.getAttribute('value');
    };

    personGroupSelectLastOption = function() {
        this.personGroupSelect.all(by.tagName('option')).last().click();
    };

    personGroupSelectOption = function(option) {
        this.personGroupSelect.sendKeys(option);
    };

    getPersonGroupSelect = function() {
        return this.personGroupSelect;
    };

    getPersonGroupSelectedOption = function() {
        return this.personGroupSelect.element(by.css('option:checked')).getText();
    };

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
