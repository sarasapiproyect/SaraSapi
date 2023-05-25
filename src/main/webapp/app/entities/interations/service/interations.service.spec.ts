import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IInterations } from '../interations.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../interations.test-samples';

import { InterationsService } from './interations.service';

const requireRestSample: IInterations = {
  ...sampleWithRequiredData,
};

describe('Interations Service', () => {
  let service: InterationsService;
  let httpMock: HttpTestingController;
  let expectedResult: IInterations | IInterations[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(InterationsService);
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

    it('should create a Interations', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const interations = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(interations).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Interations', () => {
      const interations = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(interations).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Interations', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Interations', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Interations', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addInterationsToCollectionIfMissing', () => {
      it('should add a Interations to an empty array', () => {
        const interations: IInterations = sampleWithRequiredData;
        expectedResult = service.addInterationsToCollectionIfMissing([], interations);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interations);
      });

      it('should not add a Interations to an array that contains it', () => {
        const interations: IInterations = sampleWithRequiredData;
        const interationsCollection: IInterations[] = [
          {
            ...interations,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addInterationsToCollectionIfMissing(interationsCollection, interations);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Interations to an array that doesn't contain it", () => {
        const interations: IInterations = sampleWithRequiredData;
        const interationsCollection: IInterations[] = [sampleWithPartialData];
        expectedResult = service.addInterationsToCollectionIfMissing(interationsCollection, interations);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interations);
      });

      it('should add only unique Interations to an array', () => {
        const interationsArray: IInterations[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const interationsCollection: IInterations[] = [sampleWithRequiredData];
        expectedResult = service.addInterationsToCollectionIfMissing(interationsCollection, ...interationsArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const interations: IInterations = sampleWithRequiredData;
        const interations2: IInterations = sampleWithPartialData;
        expectedResult = service.addInterationsToCollectionIfMissing([], interations, interations2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(interations);
        expect(expectedResult).toContain(interations2);
      });

      it('should accept null and undefined values', () => {
        const interations: IInterations = sampleWithRequiredData;
        expectedResult = service.addInterationsToCollectionIfMissing([], null, interations, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(interations);
      });

      it('should return initial array if no Interations is added', () => {
        const interationsCollection: IInterations[] = [sampleWithRequiredData];
        expectedResult = service.addInterationsToCollectionIfMissing(interationsCollection, undefined, null);
        expect(expectedResult).toEqual(interationsCollection);
      });
    });

    describe('compareInterations', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareInterations(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareInterations(entity1, entity2);
        const compareResult2 = service.compareInterations(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareInterations(entity1, entity2);
        const compareResult2 = service.compareInterations(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareInterations(entity1, entity2);
        const compareResult2 = service.compareInterations(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
