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

describe('DefaultResponse e2e test', () => {
  const defaultResponsePageUrl = '/default-response';
  const defaultResponsePageUrlPattern = new RegExp('/default-response(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const defaultResponseSample = { defaultValueResponse: 'Home Bacon' };

  let defaultResponse;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/default-responses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/default-responses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/default-responses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (defaultResponse) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/default-responses/${defaultResponse.id}`,
      }).then(() => {
        defaultResponse = undefined;
      });
    }
  });

  it('DefaultResponses menu should load DefaultResponses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('default-response');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DefaultResponse').should('exist');
    cy.url().should('match', defaultResponsePageUrlPattern);
  });

  describe('DefaultResponse page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(defaultResponsePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DefaultResponse page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/default-response/new$'));
        cy.getEntityCreateUpdateHeading('DefaultResponse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', defaultResponsePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/default-responses',
          body: defaultResponseSample,
        }).then(({ body }) => {
          defaultResponse = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/default-responses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [defaultResponse],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(defaultResponsePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DefaultResponse page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('defaultResponse');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', defaultResponsePageUrlPattern);
      });

      it('edit button click should load edit DefaultResponse page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DefaultResponse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', defaultResponsePageUrlPattern);
      });

      it('edit button click should load edit DefaultResponse page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DefaultResponse');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', defaultResponsePageUrlPattern);
      });

      it('last delete button click should delete instance of DefaultResponse', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('defaultResponse').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', defaultResponsePageUrlPattern);

        defaultResponse = undefined;
      });
    });
  });

  describe('new DefaultResponse page', () => {
    beforeEach(() => {
      cy.visit(`${defaultResponsePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DefaultResponse');
    });

    it('should create an instance of DefaultResponse', () => {
      cy.get(`[data-cy="defaultValueResponse"]`).type('Handcrafted Account').should('have.value', 'Handcrafted Account');

      cy.get(`[data-cy="priority"]`).select('MEDIA');

      cy.setFieldImageAsBytesOfEntity('multimedia', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('multimediaVoice', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('saraAnimation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="isEndConversation"]`).should('not.be.checked');
      cy.get(`[data-cy="isEndConversation"]`).click().should('be.checked');

      cy.get(`[data-cy="multimediaUrl"]`).type('Kids synthesize').should('have.value', 'Kids synthesize');

      cy.get(`[data-cy="multimediaVoiceUrl"]`)
        .type('functionalities Producer Cotton')
        .should('have.value', 'functionalities Producer Cotton');

      cy.get(`[data-cy="saraAnimationUrl"]`).type('Planner tan').should('have.value', 'Planner tan');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        defaultResponse = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', defaultResponsePageUrlPattern);
    });
  });
});
