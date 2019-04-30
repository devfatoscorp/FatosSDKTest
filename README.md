1	Overview

1.1	Purpose
This document defines each functions of the FATOS SDK sample and provides reference to the development of S/W using the FATOS SDK.

1.2	Scope
This document does not deal with the structural design or algorithm of FATOS SDK, but describes the function of Sample app and related API.

2	Introduction of Screen and Function Configuration  screen 1) 
screen 2) 

The description of each function is described in terms of number 1,2,3,4,5 of screen 1).

2.1	API Menu ( screen 1 - 1 ) 
The functions of the FATOS SDK sample app are largely divided into map, route, search, etc.
-	Map 
λ	Map move      : Example of moving a map to a specific coordinate
λ	Sub Map View : Example of creating sub map view except on  Main Map View 
-	Route (screen 2) 
λ	Route via  : Example of navigating a route with a waypoint 
( Sample App example is based on Korean standard. For smooth testing, set it with local coordinates and test it.) 
λ	Route Cancel : Example of Cancel route
λ	Get DriveInfo : Examples of information that may be received during current position or route driving (refer to SDK documentation)
λ	Route : Example of navigating a path to a specific destination  ( Sample App example is based on Korean standard. For smooth testing, set it with local coordinates and test it.)
λ	ReRoute : Example of a manual rescan while driving (can only be called with a path) 
λ	Get Route Summary : Example of getting information about each path option after navigating a path
λ	Start RouteGuidance : Example of calling to start route guidance after path discovery 
λ	Select Route : Example of selecting the desired option when there are multiple paths depending on the path option after path discovery (Refer to SDK documentation for path options) 
λ	Route Summary Detail : “Get Route Summary” function only gets summary information about a particular path. For example, total distance, total distance, estimated time, etc. But ‘Route Summary Detail’ includes all rotation information of the path and details such as distance/time to it. Example of getting this.
-	Search
λ	Recommended words : When you read a specific character, such as 'a', you get a recommendation that includes that character. (Currently only Korean is supported) 
-	Etc
λ	TBT Show : Calling “Start RouteGuidance” after route search. Example of showing a window a window in which driving information such as TBT, total time/distance is presented since then calling “Start RouteGuidance” after route search. 
λ	TBT Hide : Example of calling when hiding a TBT screen after path discovery 
λ	Next via : Example of changing guidance to the next destination or waypoint on a route with a waypoint.  
It is then guided to a waypoint or destination. No function to skip two or more steps.
No two or more steps to be taken to the next stop or destination.
.

2.2	POI Search ( Screen 1 - 2 )
Example for integrated search of POI by selecting edit control, typing a place to search in the keypad, and searching for results for that keyword. (Refer to FM_SearchPOI_Hybrid of SDK document) 

2.3  Map Functions ( Screen 1 - 3 )
From above, Map Mode, air/satellite mode, map zoom-in/out, and current position movement functions.
SDK internal functions cannot be controlled from the UI end. 

2.4  Drive text Info ( Screen 1 - 4 )
An Information screen that to outputs data from Route-Get Drive info(screen 2.1) 

2.5  Driving information screen showing TBT & Goal Distance & Remaining time ( Screen 1 - 5 )
It can be controlled through the TBT show/hide as an area that is automatically released after the route guidance starts. 


3	Function Scenarios 
Scenarios for Searching, Route search Function Based on the Functionality of the Sample App

-	Searching
λ	Enter the desired search term in ‘edit control’ and select the ‘search’ button on the keypad to search for poi of that word. 
If you select the retrieved list at this time, the path will be navigated. 





-	Route search
λ	Doing Route search through “Route  ‘Route Via’ or ‘Route’”  Sample App is based on Korean coordinates, so please enter specific destination coordinates for the region. 
When path discovery is complete, ‘the popup’ disappears and the path is exposed on the map.


λ	Information about the full path and each path option is available through “Route  Get Route Summary” button.  (It is not absolutely must be called for the route guidance.) 


λ	Starts the route guidance on that route through “Route  Start Route Guidance” button


So, guys. Are you interested in testing FATOS sdk? Then, you need to get the test key. 

Kindly send the requesting email to dev@fatoscorp.com, titled with [request a test key]. 

We will send the key to you as early as possible.


All the best
FATOS team
