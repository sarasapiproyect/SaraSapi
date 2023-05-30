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

describe('UserResponse e2e test', () => {
  const userResponsePageUrl = '/user-response';
  const userResponsePageUrlPattern = new RegExp('/user-response(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const userResponseSample = { valueResponse: 'models Money' };

  let userResponse;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/user-responses+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/user-responses').as('postEntityRequest');
    cy.intercept('DELETE', '/api/user-responses/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (userResponse) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/user-responses/${userResponse.id}`,
      }).then(() => {
        userResponse = undefined;
      });
    }
  });

  it('UserResponses menu should load UserResponses page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('user-response');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('UserResponse').should('exist');
    cy.url().should('match', userResponsePageUrlPattern);
  });

  describe('UserResponse page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(userResponsePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create UserResponse page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/user-response/new$'));
        cy.getEntityCreateUpdateHeading('UserResponse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userResponsePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/user-responses',
          body: userResponseSample,
        }).then(({ body }) => {
          userResponse = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/user-responses+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              body: [userResponse],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(userResponsePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details UserResponse page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('userResponse');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userResponsePageUrlPattern);
      });

      it('edit button click should load edit UserResponse page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserResponse');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userResponsePageUrlPattern);
      });

      it('edit button click should load edit UserResponse page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('UserResponse');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userResponsePageUrlPattern);
      });

      it('last delete button click should delete instance of UserResponse', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('userResponse').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', userResponsePageUrlPattern);

        userResponse = undefined;
      });
    });
  });

  describe('new UserResponse page', () => {
    beforeEach(() => {
      cy.visit(`${userResponsePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('UserResponse');
    });

    it('should create an instance of UserResponse', () => {
      cy.get(`[data-cy="valueResponse"]`).type('Sharable Music').should('have.value', 'Sharable Music');

      cy.get(`[data-cy="priority"]`).select('HIGHT');

      cy.setFieldImageAsBytesOfEntity('multimedia', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('multimediaVoice', 'integration-test.png', 'image/png');

      cy.setFieldImageAsBytesOfEntity('saraAnimation', 'integration-test.png', 'image/png');

      cy.get(`[data-cy="isEndConversation"]`).should('not.be.checked');
      cy.get(`[data-cy="isEndConversation"]`).click().should('be.checked');

      cy.get(`[data-cy="responseType"]`).select('SERVICIO');

      cy.get(`[data-cy="url"]`).type('http://fernando.org').should('have.value', 'http://fernando.org');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        userResponse = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', userResponsePageUrlPattern);
    });
  });
});
