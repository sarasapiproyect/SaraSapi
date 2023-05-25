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

describe('Intent e2e test', () => {
  const intentPageUrl = '/intent';
  const intentPageUrlPattern = new RegExp('/intent(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const intentSample = { name: 'Facilitator', description: 'SCSI lavender', urlRequest: 'Optimization Port' };

  let intent;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/intents+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/intents').as('postEntityRequest');
    cy.intercept('DELETE', '/api/intents/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (intent) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/intents/${intent.id}`,
      }).then(() => {
        intent = undefined;
      });
    }
  });

  it('Intents menu should load Intents page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('intent');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Intent').should('exist');
    cy.url().should('match', intentPageUrlPattern);
  });

  describe('Intent page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(intentPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Intent page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/intent/new$'));
        cy.getEntityCreateUpdateHeading('Intent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intentPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/intents',
          body: intentSample,
        }).then(({ body }) => {
          intent = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/intents+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [intent],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(intentPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Intent page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('intent');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intentPageUrlPattern);
      });

      it('edit button click should load edit Intent page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Intent');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intentPageUrlPattern);
      });

      it('edit button click should load edit Intent page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Intent');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intentPageUrlPattern);
      });

      it('last delete button click should delete instance of Intent', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('intent').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', intentPageUrlPattern);

        intent = undefined;
      });
    });
  });

  describe('new Intent page', () => {
    beforeEach(() => {
      cy.visit(`${intentPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Intent');
    });

    it('should create an instance of Intent', () => {
      cy.get(`[data-cy="intenType"]`).select('BACK_END_INFO');

      cy.get(`[data-cy="name"]`).type('Plastic').should('have.value', 'Plastic');

      cy.get(`[data-cy="description"]`).type('intuitive teal digital').should('have.value', 'intuitive teal digital');

      cy.get(`[data-cy="urlRequest"]`).type('interactive evolve cross-platform').should('have.value', 'interactive evolve cross-platform');

      cy.get(`[data-cy="enabled"]`).should('not.be.checked');
      cy.get(`[data-cy="enabled"]`).click().should('be.checked');

      cy.get(`[data-cy="creationDate"]`).type('2023-05-20T16:28').blur().should('have.value', '2023-05-20T16:28');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        intent = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', intentPageUrlPattern);
    });
  });
});
