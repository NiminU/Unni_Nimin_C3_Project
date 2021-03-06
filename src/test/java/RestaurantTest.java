import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantTest {
    Restaurant restaurant;
    RestaurantService service =new RestaurantService();


    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant mockedRestaurant = getRestaurant();
        Mockito.when(mockedRestaurant.getNow()).thenReturn(LocalTime.of(10,00));
        assertEquals(mockedRestaurant.isRestaurantOpen(),true);
    }

    private Restaurant getRestaurant() {
        restaurant = new Restaurant("Poland Cafe", "Bangalore", LocalTime.of(8, 00), LocalTime.of(20, 00));
        return Mockito.spy(restaurant);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant mockedRestaurant = getRestaurant();
        Mockito.when(mockedRestaurant.getNow()).thenReturn(LocalTime.of(21,00));
        assertEquals(mockedRestaurant.isRestaurantOpen(),false);

    }



    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        setMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    private void setMenu() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Amelie's cafe", "Chennai", openingTime, closingTime);
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        setMenu();

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        setMenu();

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    @Test
    public void check_total_price_of_order(){
        setMenu();
        List<String> items = new ArrayList<String>();
        items.add("Sweet corn soup");
        items.add("Vegetable lasagne");
        assertEquals(restaurant.getOrderPrice(items),388);

    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}