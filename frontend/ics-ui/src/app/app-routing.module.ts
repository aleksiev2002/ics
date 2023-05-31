import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {GalleryComponent} from "./gallery/gallery.component";
import {SubmitComponent} from "./submit/submit.component";
import {ImageViewByIdComponent} from "./image-view-by-id/image-view-by-id.component";
import {NotFoundPageComponent} from "./not-found-page/not-found-page.component";

const routes: Routes = [
  { path: '', redirectTo: '/submit', pathMatch: 'full' },
  { path: 'submit', component: SubmitComponent },
  {path: 'gallery', component: GalleryComponent},
  { path: 'image/:id', component: ImageViewByIdComponent },
  { path: '**', component: NotFoundPageComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {scrollPositionRestoration: 'enabled'})],
  exports: [RouterModule]
})
export class AppRoutingModule { }
