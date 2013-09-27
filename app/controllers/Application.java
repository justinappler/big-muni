package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;

/**
 * Main Application controller
 * 
 * Serves the index page
 * 
 * @author justinappler
 */
public class Application extends Controller {

    /**
     * Loads the main page
     * @return
     */
    public static Result index() {
        return ok(index.render("SF Muni Big Screen"));
    }
}
