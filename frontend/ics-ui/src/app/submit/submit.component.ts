import {Component} from "@angular/core";
import {Router} from "@angular/router";
import {ImageService} from "../image.service";
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
  image!: Image;

  constructor(private imageService: ImageService,private router: Router) {}

  onSubmit(): void {
    this.loading = true;


    this.imageService.analyzeImage(this.imageUrl)
      .subscribe(
        {
          next: (response: Image) => {
            this.loading = false;
            this.submitted = true;
            this.image = response;

          },
          error: (error) => {
            this.loading = false;
            const errorMessage = error.error.message;
            alert('An error occurred during image analysis. Error: ' + errorMessage);
          }
        });
  }
  viewImage(id: number): void {
    this.router.navigate(['/image', id]).then(() => {

    }).catch((error) => {
      alert(error)
    });
  }
}
