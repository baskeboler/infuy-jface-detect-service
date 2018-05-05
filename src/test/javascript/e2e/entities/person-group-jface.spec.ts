import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('PersonGroup e2e test', () => {

    let navBarPage: NavBarPage;
    let personGroupDialogPage: PersonGroupDialogPage;
    let personGroupComponentsPage: PersonGroupComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load PersonGroups', () => {
        navBarPage.goToEntity('person-group-jface');
        personGroupComponentsPage = new PersonGroupComponentsPage();
        expect(personGroupComponentsPage.getTitle())
            .toMatch(/Person Groups/);

    });

    it('should load create PersonGroup dialog', () => {
        personGroupComponentsPage.clickOnCreateButton();
        personGroupDialogPage = new PersonGroupDialogPage();
        expect(personGroupDialogPage.getModalTitle())
            .toMatch(/Create or edit a Person Group/);
        personGroupDialogPage.close();
    });

    it('should create and save PersonGroups', () => {
        personGroupComponentsPage.clickOnCreateButton();
        personGroupDialogPage.setPersonGroupIdInput('personGroupId');
        expect(personGroupDialogPage.getPersonGroupIdInput()).toMatch('personGroupId');
        personGroupDialogPage.setNameInput('name');
        expect(personGroupDialogPage.getNameInput()).toMatch('name');
        personGroupDialogPage.setDescriptionInput('description');
        expect(personGroupDialogPage.getDescriptionInput()).toMatch('description');
        personGroupDialogPage.save();
        expect(personGroupDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class PersonGroupComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-person-group-jface div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class PersonGroupDialogPage {
    modalTitle = element(by.css('h4#myPersonGroupLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    personGroupIdInput = element(by.css('input#field_personGroupId'));
    nameInput = element(by.css('input#field_name'));
    descriptionInput = element(by.css('input#field_description'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setPersonGroupIdInput = function(personGroupId) {
        this.personGroupIdInput.sendKeys(personGroupId);
    };

    getPersonGroupIdInput = function() {
        return this.personGroupIdInput.getAttribute('value');
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
