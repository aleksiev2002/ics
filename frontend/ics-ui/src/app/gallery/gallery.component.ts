import {Component, OnInit} from "@angular/core";
import {Image} from "../models/image";
import {ImageService} from "../image.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-gallery',
  templateUrl: 'gallery.component.html'
})
export class GalleryComponent implements OnInit {
  public images: Image[] = [];
  public currentPage: number = 0;
  public pageSize: number = 5;

  constructor(private imageService: ImageService) {};

  ngOnInit() {
    this.getImages();
  }

  public getImages(): void {
    this.imageService.getGalleryImages(this.currentPage, this.pageSize)
      .subscribe({
        next: (response: any) => {
          this.images = response.content;
          console.log(this.images)
        },
        error: (error: HttpErrorResponse) => {
          alert(error.message);
        }
      });
  }
}
