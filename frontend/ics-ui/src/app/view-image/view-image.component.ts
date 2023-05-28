import {Component, OnInit} from "@angular/core";
import {Image} from "../models/image";
import {ImageDataService} from "../image-data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-view-image',
  templateUrl: 'view-image.component.html',
  styleUrls: ['view-image.component.css']
})
export class ViewImageComponent implements OnInit {
  imageData!: Image; // Replace `any` with the appropriate type for your image data

  constructor(
    private imageDataService: ImageDataService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.imageData = this.imageDataService.getImageData();
    console.log(this.imageDataService.getImageData());
  }

  goBack(): void {
    this.router.navigate(['/submit']);
  }
}
