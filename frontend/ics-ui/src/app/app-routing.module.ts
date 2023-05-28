import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GalleryComponent} from "./gallery/gallery.component";
import {SubmitComponent} from "./submit/submit.component";
import {ViewImageComponent} from "./view-image/view-image.component";
import {ImageViewByIdComponent} from "./image-view-by-id/image-view-by-id.component";

const routes: Routes = [
  { path: '', redirectTo: '/submit', pathMatch: 'full' },
  { path: 'submit', component: SubmitComponent },
  { path: 'view', component: ViewImageComponent },
  {path: 'gallery', component: GalleryComponent},
  { path: 'image/:id', component: ImageViewByIdComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
