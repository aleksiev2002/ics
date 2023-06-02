import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AllImages, Image} from "./models/image";
import {Tag} from "./models/tag";

@Injectable({
  providedIn: 'root'
})
export class ImageService {
  private apiServerUrl = 'http://localhost:8080/images';

  constructor(private http: HttpClient) { }

  public getGalleryImages(page: number, size:number): Observable<AllImages> {
    return this.http.get<AllImages>(`${this.apiServerUrl}?page=${page}&size=${size}`);
  }

  public getImageById(id: number): Observable<Image> {
    const url = `${this.apiServerUrl}/${id}`;
    return this.http.get<Image>(url);
  }

  public analyzeImage(imageUrl: string): Observable<Image> {
    return this.http.post<Image>(this.apiServerUrl, {imageUrl});
  }

  public getTags(): Observable<Tag[]> {
    return this.http.get<Tag[]>('http://localhost:8080/tags');
  }

  public getImagesWithTags(tags: string): Observable<Image[]> {
    return this.http.get<Image[]>(`http://localhost:8080/images/tags?${tags}`);
  }

  getApiServerUrl(): string {
    return this.apiServerUrl;
  }
}
