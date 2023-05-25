import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IChatbootStyle } from '../chatboot-style.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../chatboot-style.test-samples';

import { ChatbootStyleService } from './chatboot-style.service';

const requireRestSample: IChatbootStyle = {
  ...sampleWithRequiredData,
};

describe('ChatbootStyle Service', () => {
  let service: ChatbootStyleService;
  let httpMock: HttpTestingController;
  let expectedResult: IChatbootStyle | IChatbootStyle[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ChatbootStyleService);
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

    it('should create a ChatbootStyle', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const chatbootStyle = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(chatbootStyle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ChatbootStyle', () => {
      const chatbootStyle = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(chatbootStyle).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ChatbootStyle', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ChatbootStyle', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ChatbootStyle', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addChatbootStyleToCollectionIfMissing', () => {
      it('should add a ChatbootStyle to an empty array', () => {
        const chatbootStyle: IChatbootStyle = sampleWithRequiredData;
        expectedResult = service.addChatbootStyleToCollectionIfMissing([], chatbootStyle);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chatbootStyle);
      });

      it('should not add a ChatbootStyle to an array that contains it', () => {
        const chatbootStyle: IChatbootStyle = sampleWithRequiredData;
        const chatbootStyleCollection: IChatbootStyle[] = [
          {
            ...chatbootStyle,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addChatbootStyleToCollectionIfMissing(chatbootStyleCollection, chatbootStyle);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ChatbootStyle to an array that doesn't contain it", () => {
        const chatbootStyle: IChatbootStyle = sampleWithRequiredData;
        const chatbootStyleCollection: IChatbootStyle[] = [sampleWithPartialData];
        expectedResult = service.addChatbootStyleToCollectionIfMissing(chatbootStyleCollection, chatbootStyle);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chatbootStyle);
      });

      it('should add only unique ChatbootStyle to an array', () => {
        const chatbootStyleArray: IChatbootStyle[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const chatbootStyleCollection: IChatbootStyle[] = [sampleWithRequiredData];
        expectedResult = service.addChatbootStyleToCollectionIfMissing(chatbootStyleCollection, ...chatbootStyleArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const chatbootStyle: IChatbootStyle = sampleWithRequiredData;
        const chatbootStyle2: IChatbootStyle = sampleWithPartialData;
        expectedResult = service.addChatbootStyleToCollectionIfMissing([], chatbootStyle, chatbootStyle2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(chatbootStyle);
        expect(expectedResult).toContain(chatbootStyle2);
      });

      it('should accept null and undefined values', () => {
        const chatbootStyle: IChatbootStyle = sampleWithRequiredData;
        expectedResult = service.addChatbootStyleToCollectionIfMissing([], null, chatbootStyle, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(chatbootStyle);
      });

      it('should return initial array if no ChatbootStyle is added', () => {
        const chatbootStyleCollection: IChatbootStyle[] = [sampleWithRequiredData];
        expectedResult = service.addChatbootStyleToCollectionIfMissing(chatbootStyleCollection, undefined, null);
        expect(expectedResult).toEqual(chatbootStyleCollection);
      });
    });

    describe('compareChatbootStyle', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareChatbootStyle(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.compareChatbootStyle(entity1, entity2);
        const compareResult2 = service.compareChatbootStyle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.compareChatbootStyle(entity1, entity2);
        const compareResult2 = service.compareChatbootStyle(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.compareChatbootStyle(entity1, entity2);
        const compareResult2 = service.compareChatbootStyle(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
