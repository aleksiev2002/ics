import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { GalleryComponent } from './gallery.component';
import { ImageService } from '../image.service';
import { of } from 'rxjs';
import { AllImages, Image } from '../models/image';
import {Router} from "@angular/router";

describe('GalleryComponent', () => {
  let component: GalleryComponent;
  let fixture: ComponentFixture<GalleryComponent>;
  let imageService: ImageService;
  let router: Router;


  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, HttpClientTestingModule],
      declarations: [GalleryComponent],
      providers: [ImageService]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleryComponent);
    component = fixture.componentInstance;
    imageService = TestBed.inject(ImageService);
    router = TestBed.inject(Router);
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should retrieve images on initialization', () => {
    const mockImages: AllImages = {
      totalPages: 2,
      content: [
        {
          id: 1,
          url: 'https://example.com/image1.jpg',
          uploadedAt: new Date(),
          analyzedWith: 'Imagga',
          width: 800,
          height: 600,
          checksum: 'abc123',
          tags: [
            { id: 1, name: 'tag1', confidence: 60 },
            { id: 2, name: 'tag2', confidence: 70 }
          ]
        },
        {
          id: 2,
          url: 'https://example.com/image2.jpg',
          uploadedAt: new Date(),
          analyzedWith: 'Imagga',
          width: 1024,
          height: 768,
          checksum: 'def456',
          tags: [
            { id: 3, name: 'tag3', confidence: 50 },
            { id: 4, name: 'tag4', confidence: 60 }
          ]
        }
      ]
    };
    spyOn(imageService, 'getGalleryImages').and.returnValue(of(mockImages));

    fixture.detectChanges();

    expect(imageService.getGalleryImages).toHaveBeenCalled();
    expect(component.images).toEqual(mockImages.content);
    expect(component.totalPages).toBe(mockImages.totalPages);
  });

  it('should retrieve filtered images when selectedTags change', () => {
    const selectedTags = ['tag1', 'tag2'];
    const mockImages: Image[] = [
      {
        id: 1,
        url: 'https://example.com/image1.jpg',
        uploadedAt: new Date(),
        analyzedWith: 'Imagga',
        width: 800,
        height: 600,
        checksum: 'abc123',
        tags: [
          { id: 1, name: 'tag1', confidence: 60 },
          { id: 2, name: 'tag2', confidence: 70 }
        ]
      },
      {
        id: 3,
        url: 'https://example.com/image3.jpg',
        uploadedAt: new Date(),
        analyzedWith: 'Imagga',
        width: 640,
        height: 480,
        checksum: 'ghi789',
        tags: [
          { id: 5, name: 'tag1', confidence: 40 },
          { id: 6, name: 'tag5', confidence: 100 }
        ]
      }
    ];
    spyOn(imageService, 'getImagesWithTags').and.returnValue(of(mockImages));

    component.handleSelectedTagsChanged(selectedTags);

    expect(imageService.getImagesWithTags).toHaveBeenCalledWith('tags=tag1,tag2');
    expect(component.images).toEqual(mockImages);
    expect(component.isFiltered).toBeTrue();
  });

  it('should retrieve all images when selectedTags are empty', () => {
    const selectedTags: string[] = [];
    const mockImages: AllImages = {
      totalPages: 3,
      content: [
        {
          id: 1,
          url: 'https://example.com/image1.jpg',
          uploadedAt: new Date(),
          analyzedWith: 'Imagga',
          width: 800,
          height: 600,
          checksum: 'abc123',
          tags: [
            { id: 1, name: 'tag1', confidence: 80 },
            { id: 2, name: 'tag2', confidence: 90 }
          ]
        },
        {
          id: 2,
          url: 'https://example.com/image2.jpg',
          uploadedAt: new Date(),
          analyzedWith: 'Imagga',
          width: 1024,
          height: 768,
          checksum: 'def456',
          tags: [
            { id: 3, name: 'tag3', confidence: 70 },
            { id: 4, name: 'tag4', confidence: 70 }
          ]
        }
      ]
    };
    spyOn(imageService, 'getGalleryImages').and.returnValue(of(mockImages));

    component.handleSelectedTagsChanged(selectedTags);

    expect(imageService.getGalleryImages).toHaveBeenCalled();
    expect(component.images).toEqual(mockImages.content);
    expect(component.isFiltered).toBeFalse();
  });

});
