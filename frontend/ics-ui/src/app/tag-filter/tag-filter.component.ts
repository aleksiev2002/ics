import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ImageService} from "../image.service";
import {Tag} from "../models/tag";

@Component({
  selector: 'app-tag-filter',
  templateUrl: './tag-filter.component.html',
  styleUrls: ['./tag-filter.component.css']
})
export class TagFilterComponent implements OnInit{
  prefix: string = '';
  tags: any[] = [];
  filteredTags: any[] = [];
  selectedTagNames: string[] = [];
  @Output() selectedTagsChanged: EventEmitter<string[]> = new EventEmitter<string[]>();



  constructor(private http: HttpClient, private imageService: ImageService) { }

  ngOnInit(): void {
    this.loadTags();
  }

  loadTags(): void {
    this.imageService.getTags().subscribe(tags => {
      this.tags = tags;
      this.filterTags();
    });
  }

  filterTags(): void {
    if (this.prefix.trim() === '') {
      this.filteredTags = [];
    } else {
      this.filteredTags = this.tags.filter(tag =>
        tag.name.toLowerCase().startsWith(this.prefix.toLowerCase())
      );
    }
  }
  selectTag(tag: Tag): void {
    const tagName = tag.name;
    const index = this.selectedTagNames.indexOf(tagName);
    if (index > -1) {
      this.deselectTag(index);
    } else {
      this.selectTagByName(tagName);
    }
    this.selectedTagsChanged.emit(this.selectedTagNames);
  }

  deselectTag(index: number): void {
    this.selectedTagNames.splice(index, 1);
    this.selectedTagsChanged.emit(this.selectedTagNames);
  }

  selectTagByName(tagName: string): void {
    this.selectedTagNames.push(tagName);
  }


  isSelected(tag: any): boolean {
    return this.selectedTagNames.includes(tag.name);
  }

}
