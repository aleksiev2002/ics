import { Injectable } from '@angular/core';
import {Image} from "./models/image";

@Injectable({
  providedIn: 'root'
})
export class ImageDataService {
  private imageData!: Image;

  setImageData(data: Image): void {
    this.imageData = data;
  }

  getImageData(): Image {
    return this.imageData;
  }
}
