import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { SubmitComponent } from './submit.component';
import { Router } from '@angular/router';
import { ImageService } from '../image.service';
import { of, throwError } from 'rxjs';
import { Image } from '../models/image';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('SubmitComponent', () => {
  let component: SubmitComponent;
  let fixture: ComponentFixture<SubmitComponent>;
  let imageService: ImageService;
  let router: Router;
  let httpMock: HttpTestingController;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [SubmitComponent],
      imports: [HttpClientTestingModule],
      providers: [ImageService, Router]
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SubmitComponent);
    component = fixture.componentInstance;
    imageService = TestBed.inject(ImageService);
    router = TestBed.inject(Router);
    httpMock = TestBed.inject(HttpTestingController);

    spyOn(router, 'navigate').and.stub();
    spyOn(window, 'alert').and.stub();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should call analyzeImage and handle success response', () => {
    const imageUrl = 'http://example.com/image.jpg';
    const mockResponse: Image = {
      id: 1,
      url: imageUrl,
      uploadedAt: new Date(),
      analyzedWith: 'Imagga',
      width: 800,
      height: 600,
      checksum: 'abcd1234',
      tags: []
    };

    component.imageUrl = imageUrl;

    component.onSubmit();

    const req = httpMock.expectOne(`${imageService.getApiServerUrl()}`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({ imageUrl });

    req.flush(mockResponse);

    expect(component.loading).toBe(false);
    expect(component.submitted).toBe(true);
    expect(component.image).toEqual(mockResponse);
  });

});
