import { Contact } from './contact.interface';
import { User } from './user.interface';
import { Booking } from './booking.interface';
import { Listing } from './listing.interface';
import { Review } from './review.inteface';

export interface JwtResponse extends User{
   token: string;
	type: string;
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
}
