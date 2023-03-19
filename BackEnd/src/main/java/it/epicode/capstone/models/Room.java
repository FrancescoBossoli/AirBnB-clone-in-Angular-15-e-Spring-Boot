package it.epicode.capstone.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Room {
	ENTIRE_HOME("Entire home/apt"),
	PRIVATE_ROOM("Private room"),
	HOTEL_ROOM("Hotel room"),
	SHARED_ROOM("Shared room");
	
	private String name;
	
	public static Room getEnumByString(String s){
        for(Room a : Room.values()) if(a.name.equals(s)) return a;        
        return null;
    }
}
