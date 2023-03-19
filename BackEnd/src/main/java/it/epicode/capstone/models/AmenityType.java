package it.epicode.capstone.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AmenityType {
	
	ESSENTIALS("Essentials"), 
	
	HOT_WATER("Hot water"),
	FIRST_AID_KIT("First aid kit"),
	
	BED_LINENS("Bed linens"), 
	EXTRA_PILLOWS_AND_BLANKET("Extra pillows and blankets"), 
	
	ROOM_DARKENING_SHADES("Room-darkening shades"), 
	
	WIFI("Wifi"), 
	ETHERNET_CONNECTION("Ethernet connection"), 
	
	CONDITIONER("Conditioner"),
	AIR_CONDITIONING("Air conditioning"), 
	AIR_CONDITIONING_SPLIT_SYSTEM("AC - split type ductless system"),
	CENTRAL_AIR_CONDITIONING("Central air conditioning"),	
		
	
	KITCHEN("Kitchen"), 
	
	OVEN("Oven"),
	STAINLESS_STEEL_OVEN("Stainless steel oven"),
	STOVE("Stove"),
	STAINLESS_STEEL_GAS_STOVE("Stainless steel gas stove"), 
	MICROWAVE("Microwave"),
	BREAD_MAKER("Bread maker"),
	HOT_WATER_KETTLE("Hot water kettle"),
	TOASTER("Toaster"),
	BAKING_SHEET("Baking sheet"),
	COOKING_BASICS("Cooking basics"),
	REFRIGERATOR("Refrigerator"), 
	FREEZER("Freezer"), 
	DISHWASHER("Dishwasher"),
	 
	BREAKFAST("Breakfast"), 
	
	COFFEE("Coffee"), 
	COFFEE_MAKER("Coffee maker"),
	COFFEE_MAKER_ESPRESSO_MACHINE("Coffee maker: espresso machine"), 
	COFFEE_MAKE_POUR_OVER_MACHINE("Coffee maker: pour-over coffee"),
	
	DINING_TABLE("Dining table"),
	WINE_GLASSES("Wine glasses"),
	DISHES_AND_SILVERWARE("Dishes and silverware"),
	
	 
	CLEANING_PRODUCTS("Cleaning products"), 
	
	
	TV("TV"),
	HDTV("HDTV"),
	HDTV_WITH_NETFLIX_AND_AMAZON("HDTV with Netflix and Amazon Prime Video"),
	HDTV_WITH_FIRETV_AND_NETFLIX("HDTV with Fire TV, Netflix"),
	CABLE_TV("TV with standard cable"),
	
	
	BODY_SOAP("Body soap"), 
	SHOWER_GEL("Shower gel"),
	SHAMPOO("Shampoo"), 
	HAIR_DRYER("Hair dryer"),
	BIDET("Bidet"),	 
	
	
	HEATING("Heating"), 
	CENTRAL_HEATING("Central heating"),
	RADIANT_HEATING("Radiant heating"),
	HEATING_SPLIT_SYSTEM("Heating - split type ductless system"),
	
		
	WASHER("Washer"), 
	FREE_WASHER_IN_UNIT("Free washer in unit"),
	PAID_WASHER_IN_UNIT("Paid washer in unit"),
	LAUNDROMAT_NEARBY("Laundromat nearby"),
	HANGERS("Hangers"),
	DRYER_RACK_FOR_CLOTHING("Drying rack for clothing"),
	IRON("Iron"),
	

	HOST_GREETS_YOU("Host greets you"),
	SELF_CHECK_IN("Self check-in"),	
	
	KEYPAD("Keypad"), 
	SMART_LOCK("Smart lock"), 	
	LOCK_ON_BEDROOM_DOOR("Lock on bedroom door"),
	
	
	OUTDOOR_DINING_AREA("Outdoor dining area"),
	SHARED_PATIO("Shared patio or balcony"), 
	PRIVATE_PATIO("Private patio or balcony"),
	PRIVATE_BACKYARD("Private backyard"),
	PRIVATE_BACKYARD_FULLY_FENCED("Private backyard fully fenced"),
	
	FREE_PARKING_ON_PREMISES("Free parking on premises"),
	FREE_PARKING_OFF_PREMISES("Free parking off premises"),
	PAID_PARKING_ON_PREMISES("Paid parking on premises"),
	PAID_PARKING_OFF_PREMISES("Paid parking off premises"), 
	FREE_PARKING_IN_GARAGE_ON_PREMISES("Free parking garage on premises"),
	PAID_PARKING_IN_GARAGE_ON_PREMISES("Paid parking garage on premises"),
	FREE_STREET_PARKING("Free street parking"),
	
	
	CRIB("Crib"), 
	PACK_N_PLAY_TRAVEL_CRIB("Pack n play/Travel crib"),
	HIGH_CHAIR("High chair"),
	
		
	PRIVATE_ENTRANCE("Private entrance"),
	SINGLE_LEVEL_HOME("Single level home"), 
	ELEVATOR("Elevator"), 
	
	CLOTHING_STORAGE("Clothing storage"),
	CLOTHING_STORAGE_CLOSET("Clothing storage: closet"),
	CLOTHING_STORAGE_CLOSET_AND_DRESSER("Clothing storage: closet and dresser"),
	CLOTHING_STORAGE_DRESSER("Clothing storage: dresser"), 
	CLOTHING_STORAGE_DRESSER_AND_CLOSET("Clothing storage: dresser and closet"), 
	CLOTHING_STORAGE_WALK_IN_CLOSET("Clothing storage: walk-in closet"),
	CLOTHING_STORAGE_WALK_IN_AND_CLOSET("Clothing storage: walk-in closet and closet"),
	
	FIRE_EXTINGUISHER("Fire extinguisher"), 
	SMOKE_ALARM("Smoke alarm"), 	
	CARBON_MONOXIDE_ALARM("Carbon monoxide alarm"), 	
	
	
	DEDICATED_WORKSPACE("Dedicated workspace"), 
	BOOKS("Books and reading material"),
	BOARD_GAMES("Board games"),
	PORTABLE_FANS("Portable fans"),
	MOSQUITO_NET("Mosquito net"), 
	
	SAFE("Safe"), 
	LOCKBOX("Lockbox"), 
	
	BUILDING_STAFF("Building staff"),
	
	PETS_ALLOWED("Pets allowed"), 
	SMOKING_ALLOWED("Smoking allowed"),
	LUGGAGE_DROPOFF_ALLOWED("Luggage dropoff allowed"),
	LONG_TERM_STAYS_ALLOWED("Long term stays allowed"),
	
	GARDEN_VIEW("Garden view"),
	COURTYARD_VIEW("Courtyard view"),
	SEA_VIEW("Sea view"),
	CITY_SLYLINE_VIEW("City skyline view");
	
	private String name;
	
	public static AmenityType getEnumByString(String s){
        for(AmenityType a : AmenityType.values()) if(a.name.equals(s)) return a;        
        return null;
    }
    
}
