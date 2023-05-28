import {Component} from '@angular/core';
import {Image} from "../models/image";
import {ActivatedRoute, Router} from "@angular/router";
import {ImageService} from "../image.service";

@Component({
  selector: 'app-image-view-by-id',
  templateUrl: './image-view-by-id.component.html',
  styleUrls: ['./image-view-by-id.component.css']
})
export class ImageViewByIdComponent {
  image!: Image;

  constructor(
    private route: ActivatedRoute,
    private imageService: ImageService,
    private router: Router
  ) { }

  ngOnInit(): void {
    const idParam = this.route.snapshot.paramMap.get('id');
    const id = idParam ? +idParam : 0;
    this.getImage(id);
  }

  getImage(id: number): void {
    this.imageService.getImageById(id).subscribe(image => {
      this.image = image;
    });
  }

  goBack(): void {
    this.router.navigate(['/gallery']);
  }
}
