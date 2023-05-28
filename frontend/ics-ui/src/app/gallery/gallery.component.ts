import {Component, OnInit} from "@angular/core";
import {AllImages, Image} from "../models/image";
import {ImageService} from "../image.service";
import {HttpErrorResponse} from "@angular/common/http";
import {Router} from "@angular/router";

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

  constructor(private imageService: ImageService, private router: Router) {};

  ngOnInit() {
    this.getImages();
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

  goToPage(page: number): void {
    if (page >= 0 && page <= this.totalPages) {
      this.currentPage = page;
      this.getImages();
    }
  }

  viewImage(id: number): void {
    this.router.navigate(['/image', id]);
  }

}
