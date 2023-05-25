import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserResponse } from '../user-response.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-response.test-samples';

import { UserResponseService } from './user-response.service';

const requireRestSample: IUserResponse = {
  ...sampleWithRequiredData,
};

describe('UserResponse Service', () => {
  let service: UserResponseService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserResponse | IUserResponse[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserResponseService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a UserResponse', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userResponse = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userResponse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserResponse', () => {
      const userResponse = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userResponse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserResponse', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserResponse', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserResponse', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserResponseToCollectionIfMissing', () => {
      it('should add a UserResponse to an empty array', () => {
        const userResponse: IUserResponse = sampleWithRequiredData;
        expectedResult = service.addUserResponseToCollectionIfMissing([], userResponse);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userResponse);
      });

      it('should not add a UserResponse to an array that contains it', () => {
        const userResponse: IUserResponse = sampleWithRequiredData;
        const userResponseCollection: IUserResponse[] = [
          {
            ...userResponse,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserResponseToCollectionIfMissing(userResponseCollection, userResponse);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserResponse to an array that doesn't contain it", () => {
        const userResponse: IUserResponse = sampleWithRequiredData;
        const userResponseCollection: IUserResponse[] = [sampleWithPartialData];
        expectedResult = service.addUserResponseToCollectionIfMissing(userResponseCollection, userResponse);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userResponse);
      });

      it('should add only unique UserResponse to an array', () => {
        const userResponseArray: IUserResponse[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userResponseCollection: IUserResponse[] = [sampleWithRequiredData];
        expectedResult = service.addUserResponseToCollectionIfMissing(userResponseCollection, ...userResponseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userResponse: IUserResponse = sampleWithRequiredData;
        const userResponse2: IUserResponse = sampleWithPartialData;
        expectedResult = service.addUserResponseToCollectionIfMissing([], userResponse, userResponse2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userResponse);
        expect(expectedResult).toContain(userResponse2);
      });

      it('should accept null and undefined values', () => {
        const userResponse: IUserResponse = sampleWithRequiredData;
        expectedResult = service.addUserResponseToCollectionIfMissing([], null, userResponse, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userResponse);
      });

      it('should return initial array if no UserResponse is added', () => {
        const userResponseCollection: IUserResponse[] = [sampleWithRequiredData];
        expectedResult = service.addUserResponseToCollectionIfMissing(userResponseCollection, undefined, null);
        expect(expectedResult).toEqual(userResponseCollection);
      });
    });

    describe('compareUserResponse', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserResponse(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserResponse(entity1, entity2);
        const compareResult2 = service.compareUserResponse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserResponse(entity1, entity2);
        const compareResult2 = service.compareUserResponse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserResponse(entity1, entity2);
        const compareResult2 = service.compareUserResponse(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
