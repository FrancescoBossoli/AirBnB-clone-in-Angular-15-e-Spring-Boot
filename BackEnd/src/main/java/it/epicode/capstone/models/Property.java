package it.epicode.capstone.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Property {
	RENTAL_UNIT("Entire rental unit"),
	ROOM_IN_RENTAL_UNIT("Private room in rental unit"),
	CONDO("Entire condo"),
	ROOM_IN_CONDO("Private room in condo"),
	LOFT("Entire loft"),
	ROOM_IN_LOFT("Private room in loft"),
	GUEST_SUITE("Entire guest suite"),	
	HOME("Entire home"),
	ROOM_IN_HOTEL("Room in hotel"),
	ROOM_IN_HOSTEL("Room in hostel"),
	ROOM_IN_BNB("Private room in bed and breakfast");
	
	private String name;
	
	public static Property getEnumByString(String s){
        for(Property a : Property.values()) if(a.name.equals(s)) return a;        
        return null;
    }
}
