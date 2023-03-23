import { User } from './user.interface';
import { Listing } from './listing.interface';

export interface Review {
   id: number;
   listing: Listing;
   date: Date;
   reviewer: User;
   comment: string;
   score: number;
}
