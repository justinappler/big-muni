$(function() {
   var IDEAL_ZOOM = 16;

   // Initialize the map (show the world)
   var mapParams = {
      zoom: 4,
      mapTypeControl: true,
      mapTypeControlOptions: {
         style: google.maps.MapTypeControlStyle.DROPDOWN_MENU
      },
      navigationControl: true,
      naavigationControlOptions: {
         style: google.maps.NavigationControlStyle.SMALL
      },
      mapTypeId: google.maps.MapTypeId.ROADMAP
   };
   
   var map = new google.maps.Map(document.getElementById("map"), mapParams);

   // Add the stop data to the UI
   function addStops(stopData) {
      $('#loading').remove();
      
      var routes = stopData["routes"];
      
      var routeId = 0;
      for (var i = 0; i < routes.length; i++) {
         var contentBox = document.createElement("div");
         $(contentBox).addClass("content-box");
         
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
               prediction.innerHTML = predictions[k];
               contentBox.appendChild(prediction);
            }
            
            // Temporary test text
            var prediction = document.createElement("span");
            prediction.innerHTML = "No predictions";
            contentBox.appendChild(prediction);
         }
         
         $("#container").append(contentBox);
      }
   }
   
   // Call the JSON stops service
   function updateStops(latitude, longitude) {
      $.ajax({
         type: "POST",
         url: "/stops",
         contentType: 'application/json',
         dataType: 'json',
         data: JSON.stringify({lat: latitude, lon: longitude})
       }).done(addStops);
   }
   
   // Let's start by trying to get the position using the HTML GeoLocation API   
   function getLocation(position) {
      var latitude = position.coords.latitude;
      var longitude = position.coords.longitude;
      
      var userPosition = new google.maps.LatLng(latitude, longitude);
      
      map.setCenter(userPosition);
      map.setZoom(IDEAL_ZOOM);
      
      var marker = new google.maps.Marker({
         position: userPosition,
         map: map,
         title:"My Location"
     });
      
     updateStops(latitude, longitude);
   }
   
   navigator.geolocation.getCurrentPosition(getLocation);
})