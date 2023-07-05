import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('Contacts e2e test', () => {
  const contactsPageUrl = '/contacts';
  const contactsPageUrlPattern = new RegExp('/contacts(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const contactsSample = { phoneNumber: 'Pass' };

  let contacts;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/contacts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/contacts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/contacts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (contacts) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/contacts/${contacts.id}`,
      }).then(() => {
        contacts = undefined;
      });
    }
  });

  it('Contacts menu should load Contacts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('contacts');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Contacts').should('exist');
    cy.url().should('match', contactsPageUrlPattern);
  });

  describe('Contacts page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(contactsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Contacts page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/contacts/new$'));
        cy.getEntityCreateUpdateHeading('Contacts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/contacts',
          body: contactsSample,
        }).then(({ body }) => {
          contacts = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/contacts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/contacts?page=0&size=20>; rel="last",<http://localhost/api/contacts?page=0&size=20>; rel="first"',
              },
              body: [contacts],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(contactsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Contacts page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('contacts');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactsPageUrlPattern);
      });

      it('edit button click should load edit Contacts page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Contacts');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactsPageUrlPattern);
      });

      it.skip('edit button click should load edit Contacts page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Contacts');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactsPageUrlPattern);
      });

      it('last delete button click should delete instance of Contacts', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('contacts').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', contactsPageUrlPattern);

        contacts = undefined;
      });
    });
  });

  describe('new Contacts page', () => {
    beforeEach(() => {
      cy.visit(`${contactsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Contacts');
    });

    it('should create an instance of Contacts', () => {
      cy.get(`[data-cy="phoneNumber"]`).type('hack').should('have.value', 'hack');

      cy.get(`[data-cy="lastDayConnection"]`).type('2023-07-04T15:29').blur().should('have.value', '2023-07-04T15:29');

      cy.get(`[data-cy="sourceChannel"]`).select('TELEGRAM');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        contacts = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', contactsPageUrlPattern);
    });
  });
});
