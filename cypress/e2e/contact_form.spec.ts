import { ContactForm } from '../../src/components/ContactForm';


// cypress/e2e/contact_form_spec.js
describe('ContactForm', () => {
    beforeEach(() => {
        // Oppdater URLen til hvor din app kjører
        cy.visit('/');
    });

    it('form validering scenario: submit-knappen skal være deaktivert dersom skjemaet er ugyldig', () => {
        // Sjekk at knappen er deaktivert ved tomt skjema (ugyldig)
        cy.get('#submit-button').should('be.disabled');

        // Fyll inn med delvis gyldige data: ugyldig e-post
        cy.get('#name-input').type('Test User');
        cy.get('#email-input').type('ugyldig-email');
        cy.get('#message-input').type('Hei verden');

        // Forvent fortsatt at knappen er deaktivert siden e-posten er ugyldig
        cy.get('#submit-button').should('be.disabled');
    });

    it('successful submission scenario: kontaktskjema viser riktig response ved gyldige data', () => {
        // Stub API-kallet for å simulere en suksessrespons
        cy.intercept('POST', 'http://localhost:3000/contact', {
            statusCode: 200,
            body: { status: 'success', message: 'Submission successful!' },
        }).as('submitContact');

        // Fyll ut skjemaet med gyldige data
        cy.get('#name-input').clear().type('Test User');
        cy.get('#email-input').clear().type('test@example.com');
        cy.get('#message-input').clear().type('Hei, dette er en testmelding!');

        // Nå skal knappen være aktiv (ikke deaktivert)
        cy.get('#submit-button').should('not.be.disabled').click();

        // Vent på at API-kallet fullføres
        cy.wait('@submitContact');

        // Sjekk at riktig suksessmelding vises
        cy.contains('Submission successful!').should('be.visible');
    });
});
