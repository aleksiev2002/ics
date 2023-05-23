import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Image} from "./models/image";

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private apiServerUrl = 'http://localhost:8080/images';

  constructor(private http: HttpClient) { }

  public getGalleryImages(page: number, size:number): Observable<Image[]> {
    return this.http.get<Image[]>(`${this.apiServerUrl}?page=${page}&size=${size}`);
  }

  public getImageById(id: number): Observable<Image> {
    const url = `${this.apiServerUrl}/${id}`;
    return this.http.get<Image>(url);
  }

  public analyzeImage(imageUrl: string): Observable<Image> {
    return this.http.post<Image>(this.apiServerUrl, {imageUrl});
  }
}
