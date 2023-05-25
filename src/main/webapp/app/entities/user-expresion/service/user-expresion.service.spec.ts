import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IUserExpresion } from '../user-expresion.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../user-expresion.test-samples';

import { UserExpresionService } from './user-expresion.service';

const requireRestSample: IUserExpresion = {
  ...sampleWithRequiredData,
};

describe('UserExpresion Service', () => {
  let service: UserExpresionService;
  let httpMock: HttpTestingController;
  let expectedResult: IUserExpresion | IUserExpresion[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(UserExpresionService);
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

    it('should create a UserExpresion', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const userExpresion = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(userExpresion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a UserExpresion', () => {
      const userExpresion = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(userExpresion).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a UserExpresion', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of UserExpresion', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a UserExpresion', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addUserExpresionToCollectionIfMissing', () => {
      it('should add a UserExpresion to an empty array', () => {
        const userExpresion: IUserExpresion = sampleWithRequiredData;
        expectedResult = service.addUserExpresionToCollectionIfMissing([], userExpresion);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userExpresion);
      });

      it('should not add a UserExpresion to an array that contains it', () => {
        const userExpresion: IUserExpresion = sampleWithRequiredData;
        const userExpresionCollection: IUserExpresion[] = [
          {
            ...userExpresion,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addUserExpresionToCollectionIfMissing(userExpresionCollection, userExpresion);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a UserExpresion to an array that doesn't contain it", () => {
        const userExpresion: IUserExpresion = sampleWithRequiredData;
        const userExpresionCollection: IUserExpresion[] = [sampleWithPartialData];
        expectedResult = service.addUserExpresionToCollectionIfMissing(userExpresionCollection, userExpresion);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userExpresion);
      });

      it('should add only unique UserExpresion to an array', () => {
        const userExpresionArray: IUserExpresion[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const userExpresionCollection: IUserExpresion[] = [sampleWithRequiredData];
        expectedResult = service.addUserExpresionToCollectionIfMissing(userExpresionCollection, ...userExpresionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const userExpresion: IUserExpresion = sampleWithRequiredData;
        const userExpresion2: IUserExpresion = sampleWithPartialData;
        expectedResult = service.addUserExpresionToCollectionIfMissing([], userExpresion, userExpresion2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(userExpresion);
        expect(expectedResult).toContain(userExpresion2);
      });

      it('should accept null and undefined values', () => {
        const userExpresion: IUserExpresion = sampleWithRequiredData;
        expectedResult = service.addUserExpresionToCollectionIfMissing([], null, userExpresion, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(userExpresion);
      });

      it('should return initial array if no UserExpresion is added', () => {
        const userExpresionCollection: IUserExpresion[] = [sampleWithRequiredData];
        expectedResult = service.addUserExpresionToCollectionIfMissing(userExpresionCollection, undefined, null);
        expect(expectedResult).toEqual(userExpresionCollection);
      });
    });

    describe('compareUserExpresion', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareUserExpresion(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareUserExpresion(entity1, entity2);
        const compareResult2 = service.compareUserExpresion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareUserExpresion(entity1, entity2);
        const compareResult2 = service.compareUserExpresion(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareUserExpresion(entity1, entity2);
        const compareResult2 = service.compareUserExpresion(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
