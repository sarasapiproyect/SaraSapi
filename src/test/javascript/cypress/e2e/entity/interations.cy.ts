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

describe('Interations e2e test', () => {
  const interationsPageUrl = '/interations';
  const interationsPageUrlPattern = new RegExp('/interations(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const interationsSample = { valueRequest: 'Customer-focused', sourceInfo: 'infrastructures', valueResponse: 'Cotton Market intranet' };

  let interations;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/interations+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/interations').as('postEntityRequest');
    cy.intercept('DELETE', '/api/interations/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (interations) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/interations/${interations.id}`,
      }).then(() => {
        interations = undefined;
      });
    }
  });

  it('Interations menu should load Interations page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('interations');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Interations').should('exist');
    cy.url().should('match', interationsPageUrlPattern);
  });

  describe('Interations page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(interationsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Interations page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/interations/new$'));
        cy.getEntityCreateUpdateHeading('Interations');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', interationsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/interations',
          body: interationsSample,
        }).then(({ body }) => {
          interations = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/interations+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/interations?page=0&size=20>; rel="last",<http://localhost/api/interations?page=0&size=20>; rel="first"',
              },
              body: [interations],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(interationsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Interations page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('interations');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', interationsPageUrlPattern);
      });

      it('edit button click should load edit Interations page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Interations');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', interationsPageUrlPattern);
      });

      it.skip('edit button click should load edit Interations page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Interations');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', interationsPageUrlPattern);
      });

      it('last delete button click should delete instance of Interations', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('interations').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', interationsPageUrlPattern);

        interations = undefined;
      });
    });
  });

  describe('new Interations page', () => {
    beforeEach(() => {
      cy.visit(`${interationsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Interations');
    });

    it('should create an instance of Interations', () => {
      cy.get(`[data-cy="valueRequest"]`).type('Car utilisation').should('have.value', 'Car utilisation');

      cy.get(`[data-cy="sourceInfo"]`).type('Account white').should('have.value', 'Account white');

      cy.get(`[data-cy="valueResponse"]`).type('Analyst programming Yemen').should('have.value', 'Analyst programming Yemen');

      cy.get(`[data-cy="sourceChannel"]`).select('WHATSAPP');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        interations = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', interationsPageUrlPattern);
    });
  });
});
