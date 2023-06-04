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

describe('UserExpresion e2e test', () => {
  const userExpresionPageUrl = '/user-expresion';
  const userExpresionPageUrlPattern = new RegExp('/user-expresion(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userExpresionSample = { value: 'EXE user-centric' };

  let userExpresion;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-expresions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-expresions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-expresions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userExpresion) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-expresions/${userExpresion.id}`,
      }).then(() => {
        userExpresion = undefined;
      });
    }
  });

  it('UserExpresions menu should load UserExpresions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-expresion');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserExpresion').should('exist');
    cy.url().should('match', userExpresionPageUrlPattern);
  });

  describe('UserExpresion page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userExpresionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserExpresion page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-expresion/new$'));
        cy.getEntityCreateUpdateHeading('UserExpresion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userExpresionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-expresions',
          body: userExpresionSample,
        }).then(({ body }) => {
          userExpresion = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-expresions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/user-expresions?page=0&size=20>; rel="last",<http://localhost/api/user-expresions?page=0&size=20>; rel="first"',
              },
              body: [userExpresion],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userExpresionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserExpresion page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userExpresion');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userExpresionPageUrlPattern);
      });

      it('edit button click should load edit UserExpresion page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserExpresion');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userExpresionPageUrlPattern);
      });

      it.skip('edit button click should load edit UserExpresion page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserExpresion');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userExpresionPageUrlPattern);
      });

      it('last delete button click should delete instance of UserExpresion', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('userExpresion').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userExpresionPageUrlPattern);

        userExpresion = undefined;
      });
    });
  });

  describe('new UserExpresion page', () => {
    beforeEach(() => {
      cy.visit(`${userExpresionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserExpresion');
    });

    it('should create an instance of UserExpresion', () => {
      cy.get(`[data-cy="value"]`).type('invoice Mali').should('have.value', 'invoice Mali');

      cy.get(`[data-cy="priority"]`).select('MEDIA');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userExpresion = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userExpresionPageUrlPattern);
    });
  });
});
