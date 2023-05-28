import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {GalleryComponent} from "./gallery/gallery.component";
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {NavbarComponent} from "./navbar/navbar.component";
import {FooterComponent} from "./footer/footer.component";
import {FormsModule} from "@angular/forms";
import {SubmitComponent} from "./submit/submit.component";
import {ViewImageComponent} from "./view-image/view-image.component";
import {ClarityModule} from "@clr/angular";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import { ImageViewByIdComponent } from './image-view-by-id/image-view-by-id.component';

@NgModule({
  declarations: [
    AppComponent,
    GalleryComponent,
    NavbarComponent,
    FooterComponent,
    SubmitComponent,
    ViewImageComponent,
    ImageViewByIdComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    CommonModule,
    FormsModule,
    ClarityModule,
    BrowserAnimationsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
