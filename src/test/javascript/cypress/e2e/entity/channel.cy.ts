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

describe('Channel e2e test', () => {
  const channelPageUrl = '/channel';
  const channelPageUrlPattern = new RegExp('/channel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const channelSample = { description: 'Fish' };

  let channel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/channels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/channels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/channels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (channel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/channels/${channel.id}`,
      }).then(() => {
        channel = undefined;
      });
    }
  });

  it('Channels menu should load Channels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('channel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('Channel').should('exist');
    cy.url().should('match', channelPageUrlPattern);
  });

  describe('Channel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(channelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create Channel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/channel/new$'));
        cy.getEntityCreateUpdateHeading('Channel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', channelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/channels',
          body: channelSample,
        }).then(({ body }) => {
          channel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/channels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/channels?page=0&size=20>; rel="last",<http://localhost/api/channels?page=0&size=20>; rel="first"',
              },
              body: [channel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(channelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details Channel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('channel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', channelPageUrlPattern);
      });

      it('edit button click should load edit Channel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Channel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', channelPageUrlPattern);
      });

      it('edit button click should load edit Channel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('Channel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', channelPageUrlPattern);
      });

      it('last delete button click should delete instance of Channel', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('channel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', channelPageUrlPattern);

        channel = undefined;
      });
    });
  });

  describe('new Channel page', () => {
    beforeEach(() => {
      cy.visit(`${channelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('Channel');
    });

    it('should create an instance of Channel', () => {
      cy.get(`[data-cy="description"]`).type('Facilitator').should('have.value', 'Facilitator');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        channel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', channelPageUrlPattern);
    });
  });
});
