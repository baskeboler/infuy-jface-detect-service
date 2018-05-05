import { browser, element, by } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';

describe('Face e2e test', () => {

    let navBarPage: NavBarPage;
    let faceDialogPage: FaceDialogPage;
    let faceComponentsPage: FaceComponentsPage;

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Faces', () => {
        navBarPage.goToEntity('face-jface');
        faceComponentsPage = new FaceComponentsPage();
        expect(faceComponentsPage.getTitle())
            .toMatch(/Faces/);

    });

    it('should load create Face dialog', () => {
        faceComponentsPage.clickOnCreateButton();
        faceDialogPage = new FaceDialogPage();
        expect(faceDialogPage.getModalTitle())
            .toMatch(/Create or edit a Face/);
        faceDialogPage.close();
    });

    it('should create and save Faces', () => {
        faceComponentsPage.clickOnCreateButton();
        faceDialogPage.setPersistedFaceIdInput('persistedFaceId');
        expect(faceDialogPage.getPersistedFaceIdInput()).toMatch('persistedFaceId');
        faceDialogPage.personGroupPersonSelectLastOption();
        faceDialogPage.save();
        expect(faceDialogPage.getSaveButton().isPresent()).toBeFalsy();
    });

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class FaceComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-face-jface div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getText();
    }
}

export class FaceDialogPage {
    modalTitle = element(by.css('h4#myFaceLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    persistedFaceIdInput = element(by.css('input#field_persistedFaceId'));
    personGroupPersonSelect = element(by.css('select#field_personGroupPerson'));

    getModalTitle() {
        return this.modalTitle.getText();
    }

    setPersistedFaceIdInput = function(persistedFaceId) {
        this.persistedFaceIdInput.sendKeys(persistedFaceId);
    };

    getPersistedFaceIdInput = function() {
        return this.persistedFaceIdInput.getAttribute('value');
    };

    personGroupPersonSelectLastOption = function() {
        this.personGroupPersonSelect.all(by.tagName('option')).last().click();
    };

    personGroupPersonSelectOption = function(option) {
        this.personGroupPersonSelect.sendKeys(option);
    };

    getPersonGroupPersonSelect = function() {
        return this.personGroupPersonSelect;
    };

    getPersonGroupPersonSelectedOption = function() {
        return this.personGroupPersonSelect.element(by.css('option:checked')).getText();
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
