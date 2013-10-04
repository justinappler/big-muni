Big Muni
=====================================

A simple web app that uses the NextBus Public API to display predictions for nearby bus stops.  Ideally, this webapp would be displayed full screen on a spare LCD/TV somewhere.

Uses the following tools:
* [Play Framework][play2]
* [JAXB][jaxb], for XML -> POJO deserialization
* [Jackson][jackson], for POJO -> JSON serialization
* [NextBus][nextbus] Public Data API, for route information and stop predictions
* [jQuery][jquery], for client-side AJAX requests and DOM Manipulation

## To-do
* Explicit location (as opposed to just using the HTML Geolocation API)
* Client error case handling (API is down, etc)
* Tests (Unit for parsing/Geo calc)
* Server-side Async Prediction (don't block/wait on prediction API call)
* More robust Geolocation with error checking 
* Chromecast Support

## Usage
With the [Play 2.2 Framework][play2] installed,
```
play run
```

## Demo
[Demo][demo]

## Author

Justin Appler: [@justinappler][twitter] [LinkedIn][linkedin]

## License

Licensed under [MIT][mit].

[linkedin]: http://www.linkedin.com/in/justinappler
[demo]: http://app-15074f20-0033-419e-9a46-0dca922442e0.cleverapps.io/ 
[twitter]: http://twitter.com/justinappler
[mit]: http://www.opensource.org/licenses/mit-license.php
[play2]: http://www.playframework.com
[jaxb]: https://jaxb.java.net/
[jackson]: https://github.com/FasterXML/jackson
[nextbus]: http://www.nextbus.com
[jquery]: http://www.jquery.com
