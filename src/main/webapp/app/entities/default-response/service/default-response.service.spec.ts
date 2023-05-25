import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IDefaultResponse } from '../default-response.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../default-response.test-samples';

import { DefaultResponseService } from './default-response.service';

const requireRestSample: IDefaultResponse = {
  ...sampleWithRequiredData,
};

describe('DefaultResponse Service', () => {
  let service: DefaultResponseService;
  let httpMock: HttpTestingController;
  let expectedResult: IDefaultResponse | IDefaultResponse[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(DefaultResponseService);
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

    it('should create a DefaultResponse', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const defaultResponse = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(defaultResponse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a DefaultResponse', () => {
      const defaultResponse = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(defaultResponse).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a DefaultResponse', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of DefaultResponse', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a DefaultResponse', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addDefaultResponseToCollectionIfMissing', () => {
      it('should add a DefaultResponse to an empty array', () => {
        const defaultResponse: IDefaultResponse = sampleWithRequiredData;
        expectedResult = service.addDefaultResponseToCollectionIfMissing([], defaultResponse);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(defaultResponse);
      });

      it('should not add a DefaultResponse to an array that contains it', () => {
        const defaultResponse: IDefaultResponse = sampleWithRequiredData;
        const defaultResponseCollection: IDefaultResponse[] = [
          {
            ...defaultResponse,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addDefaultResponseToCollectionIfMissing(defaultResponseCollection, defaultResponse);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a DefaultResponse to an array that doesn't contain it", () => {
        const defaultResponse: IDefaultResponse = sampleWithRequiredData;
        const defaultResponseCollection: IDefaultResponse[] = [sampleWithPartialData];
        expectedResult = service.addDefaultResponseToCollectionIfMissing(defaultResponseCollection, defaultResponse);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(defaultResponse);
      });

      it('should add only unique DefaultResponse to an array', () => {
        const defaultResponseArray: IDefaultResponse[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const defaultResponseCollection: IDefaultResponse[] = [sampleWithRequiredData];
        expectedResult = service.addDefaultResponseToCollectionIfMissing(defaultResponseCollection, ...defaultResponseArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const defaultResponse: IDefaultResponse = sampleWithRequiredData;
        const defaultResponse2: IDefaultResponse = sampleWithPartialData;
        expectedResult = service.addDefaultResponseToCollectionIfMissing([], defaultResponse, defaultResponse2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(defaultResponse);
        expect(expectedResult).toContain(defaultResponse2);
      });

      it('should accept null and undefined values', () => {
        const defaultResponse: IDefaultResponse = sampleWithRequiredData;
        expectedResult = service.addDefaultResponseToCollectionIfMissing([], null, defaultResponse, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(defaultResponse);
      });

      it('should return initial array if no DefaultResponse is added', () => {
        const defaultResponseCollection: IDefaultResponse[] = [sampleWithRequiredData];
        expectedResult = service.addDefaultResponseToCollectionIfMissing(defaultResponseCollection, undefined, null);
        expect(expectedResult).toEqual(defaultResponseCollection);
      });
    });

    describe('compareDefaultResponse', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareDefaultResponse(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareDefaultResponse(entity1, entity2);
        const compareResult2 = service.compareDefaultResponse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareDefaultResponse(entity1, entity2);
        const compareResult2 = service.compareDefaultResponse(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareDefaultResponse(entity1, entity2);
        const compareResult2 = service.compareDefaultResponse(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
