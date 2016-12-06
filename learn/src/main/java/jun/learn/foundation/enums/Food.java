package jun.learn.foundation.enums;


public interface Food {
	enum Appetizer implements Food{
		SALAD, SOUP, SPRING_ROLLS
	}
	enum MainCourse implements Food{
		LASAGNE, BURRID, PAD_THAI,
		LENTILS, HUMMOUS, VINDLOO
	}
	enum Dessert implements Food{
		TIRAMISU, GELATO, BLACK_FOREST_CAKE,
		FRUIT, CREME_CARMEL
	}
	enum Coffee implements Food{
		BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
		LATTE, CAPPUCCINO, TEA, HERB_TEA;
		
		public static void main(String[] args) {
			Food food = null;
			food = Appetizer.SALAD;
			food = MainCourse.BURRID;
		}
	}
	
}
