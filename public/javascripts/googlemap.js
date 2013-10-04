function GoogleMap (mapElement) {
    this.idealZoom = 16;

    this.mapParams = {
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

    this.internalMap = new google.maps.Map(document.getElementById(mapElement), this.mapParams);
}

GoogleMap.prototype.focus = function(latitude, longitude) {
   var userPosition = new google.maps.LatLng(latitude, longitude);
   
   this.internalMap.setCenter(userPosition);
   this.internalMap.setZoom(this.idealZoom);
   
   new google.maps.Marker({
      position: userPosition,
      map: this.internalMap,
      title: "My Location"
   });
};
