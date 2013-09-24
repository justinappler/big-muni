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
   }
   
   navigator.geolocation.getCurrentPosition(getLocation);
   
   
})