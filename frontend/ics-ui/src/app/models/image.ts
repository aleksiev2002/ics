import {Tag} from "./tag";

export interface Image {
  id: number;
  url: string;
  uploadedAt: Date;
  analyzedWith: string;
  width: number;
  height: number;
  checksum: string;
  tags: Tag[];
}

export interface AllImages {
  content: Image[];
  totalPages: number;
}
