package it.epicode.capstone.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Bathroom {

	BATH_1("1 bath"),
	BATHS_2("2 baths"),
	BATHS_3("3 baths"),
	BATHS_4("4 baths"),
	PRIVATE_BATH_1("1 private bath"),
	PRIVATE_BATHS_2("2 private baths"),
	SHARED_BATH_1("1 shared bath"),
	SHARED_BATHS_2("2 shared baths"),
	SHARED_BATHS_3("3 shared baths"),
	SHARED_BATHS_4("4 shared baths");
	
	
	private String name;
	
	public static Bathroom getEnumByString(String s){
        for(Bathroom a : Bathroom.values()) if(a.name.equals(s)) return a;        
        return null;
    }
}
