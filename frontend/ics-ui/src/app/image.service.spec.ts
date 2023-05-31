import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ImageService } from './image.service';
import { AllImages, Image } from './models/image';

describe('ImageService', () => {
  let service: ImageService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ImageService]
    });
    service = TestBed.inject(ImageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getGalleryImages', () => {
    it('should send a GET request to the server with the correct parameters and return the response', () => {
      const page = 1;
      const size = 10;
      const mockResponse: AllImages = {
        content: [
          {
            id: 1,
            url: 'http://example.com/image1.jpg',
            uploadedAt: new Date(),
            analyzedWith: 'Imagga',
            width: 800,
            height: 600,
            checksum: 'abcd1234',
            tags: []
          },
          {
            id: 2,
            url: 'http://example.com/image2.jpg',
            uploadedAt: new Date(),
            analyzedWith: 'Imagga',
            width: 1024,
            height: 768,
            checksum: 'efgh5678',
            tags: []
          }
        ],
        totalPages: 2
      };

      service.getGalleryImages(page, size).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${service.getApiServerUrl()}?page=${page}&size=${size}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockResponse);
    });
  });

  describe('getImageById', () => {
    it('should send a GET request to the server with the correct URL and return the response', () => {
      const id = 1;
      const mockResponse: Image = {
        id: 1,
        url: 'http://example.com/image.jpg',
        uploadedAt: new Date(),
        analyzedWith: 'AI',
        width: 800,
        height: 600,
        checksum: 'abcd1234',
        tags: []
      };

      service.getImageById(id).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(`${service.getApiServerUrl()}/${id}`);
      expect(req.request.method).toBe('GET');
      req.flush(mockResponse);
    });
  });

  describe('analyzeImage', () => {
    it('should send a POST request to the server with the correct URL and data, and return the response', () => {
      const imageUrl = 'http://example.com/image.jpg';
      const mockResponse: Image = {
        id: 1,
        url: 'http://example.com/image.jpg',
        uploadedAt: new Date(),
        analyzedWith: 'Imagga',
        width: 800,
        height: 600,
        checksum: 'abcd1234',
        tags: []
      };

      service.analyzeImage(imageUrl).subscribe((response) => {
        expect(response).toEqual(mockResponse);
      });

      const req = httpMock.expectOne(service.getApiServerUrl());
      expect(req.request.method).toBe('POST');
      expect(req.request.body).toEqual({ imageUrl });
      req.flush(mockResponse);
    });
  });
});
