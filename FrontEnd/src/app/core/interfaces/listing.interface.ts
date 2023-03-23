import { User } from './user.interface';
import { Booking } from './booking.interface';
import { Review } from './review.inteface';
export interface Listing {
   id: number;
	name: string;
   description: string;
   neighborhoodOverview?: string;
   pictures: string[];
   owner: User;
   latitude: number;
   longitude: number;
   propertyType: string;
   roomType: string;
   capacity: number;
   bathrooms: string;
   bedrooms: number;
   beds: number;
   price: number;
   minimumStay: number;
   maximumStay: number;
   instantBookable: boolean;
   amenities: string[];
   reviews: Review[];
   bookings: Booking[];
}
