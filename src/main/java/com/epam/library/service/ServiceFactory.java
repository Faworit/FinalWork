package com.epam.library.service;

import java.util.HashMap;
import java.util.Map;

public class ServiceFactory {

    private static final Map<String, Service> MAP_OF_SERVICE = new HashMap<>();
    private static final ServiceFactory SERVICE_FACTORY = new ServiceFactory();

    static{
        MAP_OF_SERVICE.put("/LogIn", new LogInService());
        MAP_OF_SERVICE.put("/jsp/addAuthor", new AddAuthorService());
        MAP_OF_SERVICE.put("/jsp/addGenre", new AddGenreService());
        MAP_OF_SERVICE.put("/jsp/changePassword", new ChengePasswordService());
        MAP_OF_SERVICE.put("/jsp/createUser", new AddUserService());
        MAP_OF_SERVICE.put("/jsp/createBook", new AddBookService());
        MAP_OF_SERVICE.put("/jsp/confirm", new ConfirmReceiptService());
        MAP_OF_SERVICE.put("/jsp/editBook", new EditBookService());
        MAP_OF_SERVICE.put("/jsp/editBooking", new EditBookingService());
        MAP_OF_SERVICE.put("/jsp/editBookMenu", new ShowEditBookMenuService());
        MAP_OF_SERVICE.put("/jsp/forward", new ForwardService());
        MAP_OF_SERVICE.put("/jsp/logOut", new LogOutService());
        MAP_OF_SERVICE.put("/jsp/makeOrder", new AddOrderService());
        MAP_OF_SERVICE.put("/jsp/perform", new PerformOrderService());
        MAP_OF_SERVICE.put("/jsp/onlyNew", new ShowBookingByStatusService());
        MAP_OF_SERVICE.put("/jsp/editByID", new RemoveBookService());
        MAP_OF_SERVICE.put("/jsp/removeBook", new RemoveBookService());
        MAP_OF_SERVICE.put("/search", new SearchService());
        MAP_OF_SERVICE.put("/jsp/showAddBookMenu", new ShowAddBookMenuService());
        MAP_OF_SERVICE.put("/showBook", new ShowBookService());
        MAP_OF_SERVICE.put("/jsp/showOrder", new ShowOrderService());
    }

    public static ServiceFactory getInstance(){
        return SERVICE_FACTORY;
    }

    public Service getService(String request){
        return MAP_OF_SERVICE.get(request);
    }
}
