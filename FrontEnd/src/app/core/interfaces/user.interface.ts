import { Booking } from './booking.interface';
import { Contact } from './contact.interface';
import { Listing } from './listing.interface';
import { Review } from './review.inteface';
export interface User {
   id: number;
	username: string;
	email: string;
	roles: string[];
   name: string;
   surname: string;
   hostSince: Date;
   location: string;
   neighbourhood: string;
   about: string;
   pictureUrl: string;
   spokenLanguages: string[];
   verifications: Contact[];
   listings: Listing[];
   reviews: Review[];
   bookings: Booking[];
   favourites: Listing[];
   token: string;
}
