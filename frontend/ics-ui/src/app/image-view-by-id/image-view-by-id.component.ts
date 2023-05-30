import {Component, OnInit} from '@angular/core';
import {Image} from "../models/image";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../image.service";
import {HttpErrorResponse} from "@angular/common/http";


@Component({
  selector: 'app-image-view-by-id',
  templateUrl: './image-view-by-id.component.html',
  styleUrls: ['./image-view-by-id.component.css']
})
export class ImageViewByIdComponent implements OnInit{
  image!: Image;

  constructor(
    private route: ActivatedRoute,
    private imageService: ImageService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? +idParam : 0;
    this.getImage(id);
  }

  getImage(id: number): void {
    this.imageService.getImageById(id).subscribe({
      next: (response: Image) => {
        this.image = response
      },
      error: (error: HttpErrorResponse) => {
        alert(error.error.message);
      }
    });
  }

  goBack(): void {
    this.router.navigate(['/gallery']).then(() => {

    }).catch((error) => {
      alert(error)
    });
  }
}
