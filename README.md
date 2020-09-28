# elastic-search-directory-java
Java Spring Boot Elastic search directory

### Installation
Clone project into your local directory


search data in mobile directory with any property http://localhost:{port}/mobile/search?announceDate=1999&price=200 Will return 2 devices.

http://localhost:{port}/mobile/search?priceEur=200. Will return 10 devices.
http://localhost:{port}/mobile/search?sim=eSim. Will return 18 devices.


Another api developed for elasticsearch:
>Download and Install Elasticsearch v7.0+ 
>Configure Elastic search connection in application.properties file.
>First import json data into your elastic search db with following api

import data into elasticseach db with REST API: http://localhost:{port}/mobile/load-data

search data in mobile directory with any property http://localhost:{port}/mobile/el-search?sim=eSim


