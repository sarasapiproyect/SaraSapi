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

describe('ChatbootStyle e2e test', () => {
  const chatbootStylePageUrl = '/chatboot-style';
  const chatbootStylePageUrlPattern = new RegExp('/chatboot-style(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const chatbootStyleSample = { nameProperties: 'Tunnel', value: 'Cliff' };

  let chatbootStyle;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/chatboot-styles+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/chatboot-styles').as('postEntityRequest');
    cy.intercept('DELETE', '/api/chatboot-styles/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (chatbootStyle) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/chatboot-styles/${chatbootStyle.id}`,
      }).then(() => {
        chatbootStyle = undefined;
      });
    }
  });

  it('ChatbootStyles menu should load ChatbootStyles page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('chatboot-style');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('ChatbootStyle').should('exist');
    cy.url().should('match', chatbootStylePageUrlPattern);
  });

  describe('ChatbootStyle page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(chatbootStylePageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create ChatbootStyle page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/chatboot-style/new$'));
        cy.getEntityCreateUpdateHeading('ChatbootStyle');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chatbootStylePageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/chatboot-styles',
          body: chatbootStyleSample,
        }).then(({ body }) => {
          chatbootStyle = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/chatboot-styles+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/chatboot-styles?page=0&size=20>; rel="last",<http://localhost/api/chatboot-styles?page=0&size=20>; rel="first"',
              },
              body: [chatbootStyle],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(chatbootStylePageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details ChatbootStyle page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('chatbootStyle');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chatbootStylePageUrlPattern);
      });

      it('edit button click should load edit ChatbootStyle page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ChatbootStyle');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chatbootStylePageUrlPattern);
      });

      it.skip('edit button click should load edit ChatbootStyle page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('ChatbootStyle');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chatbootStylePageUrlPattern);
      });

      it('last delete button click should delete instance of ChatbootStyle', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('chatbootStyle').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', chatbootStylePageUrlPattern);

        chatbootStyle = undefined;
      });
    });
  });

  describe('new ChatbootStyle page', () => {
    beforeEach(() => {
      cy.visit(`${chatbootStylePageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('ChatbootStyle');
    });

    it('should create an instance of ChatbootStyle', () => {
      cy.get(`[data-cy="nameProperties"]`).type('transition').should('have.value', 'transition');

      cy.get(`[data-cy="value"]`).type('Tactics Interactions contingency').should('have.value', 'Tactics Interactions contingency');

      cy.setFieldImageAsBytesOfEntity('multimedia', 'integration-test.png', 'image/png');

      // since cypress clicks submit too fast before the blob fields are validated
      cy.wait(200); // eslint-disable-line cypress/no-unnecessary-waiting
      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        chatbootStyle = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', chatbootStylePageUrlPattern);
    });
  });
});
