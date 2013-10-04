var UPDATE_TIME = 30 * 1000; // 30 Seconds

// Define some variables we'll use globally
var googleMap, latitude, longitude;

/*
 * Adds stop information elements to the DOM using Stop Data returned from
 * the API
 */
function addStops(stopData) {
   $('#loading').remove();
   $('.route').remove();
   
   var routes = stopData["routes"];
   
   var routeId = 0;
   for (var i = 0; i < routes.length; i++) {
      var contentBox = document.createElement("div");
      $(contentBox).addClass("content-box");
      $(contentBox).addClass("route");
      
      var heading = document.createElement("h2");
      heading.innerHTML = routes[i]["routeName"];
      contentBox.appendChild(heading);
      
      var stops = routes[i]["stops"];
      
      for (var j = 0; j < stops.length; j++) {
         var stopTitle = document.createElement("h3");
         stopTitle.innerHTML = stops[j]["stopName"];
         contentBox.appendChild(stopTitle);
         
         var predictions = stops[j]["predictions"];
         for (var k = 0; k < predictions.length; k++) {
            var prediction = document.createElement("span");
            prediction.innerHTML = predictions[k] + ((predictions[k] > 1) ? " mins" : " min");
            contentBox.appendChild(prediction);
         }
         
         if (predictions.length < 1) {
            var prediction = document.createElement("span");
            prediction.innerHTML = "No predictions";
            contentBox.appendChild(prediction);
         }
      }
      
      $("#container").append(contentBox);
   }
   
   $('.last-updated').text(Date.parse('now').toString("h:mm:ss tt"));
   setTimeout(updateStops, UPDATE_TIME);
}

/*
 * Updates the stops using the JSON stops API
 */
function updateStops() {
   $('.last-updated').text("Updating...");
   
   $.ajax({
      url: "/stops",
      data: {lat: latitude, lon: longitude}
    }).done(addStops);
}


/*
 * Callback for the HTML Location API, focuses the map on the returned location
 * and triggers an update of the stops
 */
function getLocation(position) {
   latitude = position.coords.latitude;
   longitude = position.coords.longitude;
   
   googleMap.focus(latitude, longitude);
   
   updateStops();
}

/*
 * When the document is ready, start here 
 */
$(function() {  
   // Create a new Google map tied to the map element
   googleMap = new GoogleMap("map");

   // Use the HTML Geolocation API to get the current position
   navigator.geolocation.getCurrentPosition(getLocation);
})