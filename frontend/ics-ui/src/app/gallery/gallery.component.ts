import { Component, OnInit } from '@angular/core';
import { ImageService } from '../image.service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';
import {AllImages, Image} from "../models/image";

@Component({
  selector: 'app-gallery',
  templateUrl: 'gallery.component.html',
  styleUrls: ['gallery.component.css']
})
export class GalleryComponent implements OnInit {
  public images: Image[] = [];
  public currentPage: number = 0;
  public pageSize: number = 5;
  public totalPages: number = 0;
  public isFiltered: boolean = false;

  constructor(private imageService: ImageService, private router: Router) {}

  ngOnInit() {
    this.getImages();
  }

  handleSelectedTagsChanged(selectedTags: string[]): void {
    if (selectedTags.length > 0) {
      this.getImagesWithTags(selectedTags);
      this.isFiltered = true;
    } else {
      this.getImages();
      this.isFiltered = false;

    }
  }


  public getImages(): void {
    this.imageService.getGalleryImages(this.currentPage, this.pageSize)
      .subscribe({
        next: (response: AllImages) => {
          this.totalPages = response.totalPages;
          this.images = response.content;
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
  }

  getImagesWithTags(tags: string[]): void {
    const queryString = `tags=${tags.join(',')}`;
    this.imageService.getImagesWithTags(queryString).subscribe((images) => {
      this.images = images;
    });
  }


  goToPage(page: number): void {
    if (page >= 0 && page <= this.totalPages) {
      this.currentPage = page;
      this.getImages();
    }
  }

  viewImage(id: number): void {
    this.router.navigate(['/image', id]).then(() => {}).catch((error) => {
      alert(error);
    });
  }
}

