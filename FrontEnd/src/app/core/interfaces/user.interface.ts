import { Booking } from './booking.interface';
import { Listing } from './listing.interface';
import { Review } from './review.inteface';
export interface User {
   id: number;
	username: string;
	email: string;
	roles: string[];
   name?: string;
   surname?: string;
   hostSince?: Date;
   location?: string;
   neighbourhood?: string;
   about?: string;
   pictureUrl?: string;
   spokenLanguages?: string[];
   verifications?: string[];
   listings?: Listing[];
   reviews?: Review[];
   bookings?: Booking[];
   token?: string;
}
