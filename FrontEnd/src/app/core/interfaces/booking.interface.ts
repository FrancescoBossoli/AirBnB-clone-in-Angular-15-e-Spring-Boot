import { User } from './user.interface';
import { Listing } from './listing.interface';

export interface Booking {
   id: number;
   arrival: Date;
   departure: Date;
   cost: number;
   location: Listing;
   booker: User;
}
