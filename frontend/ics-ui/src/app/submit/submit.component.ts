import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ImageService} from "../image.service";
import {ImageDataService} from "../image-data.service";
import {Image} from "../models/image";

@Component({
  selector: 'app-submit',
  templateUrl: 'submit.component.html',
  styleUrls: ['submit.component.css']
})
export class SubmitComponent {
  loading: boolean = false;
  submitted: boolean = false;
  imageUrl: string = '';

  constructor(private imageService: ImageService,private imageDataService: ImageDataService,private router: Router) {}

  onSubmit(): void {
    this.loading = true;


    this.imageService.analyzeImage(this.imageUrl)
      .subscribe(
        {
          next: (response: Image) => {
            this.loading = false;
            this.submitted = true;
            this.imageDataService.setImageData(response);

          },
          error: (error) => {
            this.loading = false;
            console.error('An error occurred during image analysis:', error);
            alert('An error occurred during image analysis. Please try again.');
          }
        });
  }
  viewImage(): void {
    this.router.navigate(['/view']);
  }
}
