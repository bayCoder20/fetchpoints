------------
# Spring Boot Point Tracker
------------

**Spring Boot application for tracking the managment of reward points.**
- Creates two H2 database tables named "tbl_points" and "tbl_log"
- H2 username is "root" and password is "password" see application.properties for reference.
- Utilizes port 8080

**Functions**
- API calls and returns in JSON format
- Add payer and points
- Deduct points from specific payer
- Deduct general points fom all payers
- Get balance of points 

**API Calls**
- Add Points Per Payer, Deduct Points via Negative Value, Returns Confirmation via POST Method <br>
-- localhost:8080/api/fetchpoints/add <br>
-- request body example... {"payerName":"dannon","points":300}

- Deduct General Points, Returns Deduction Summary via POST Method <br>
-- localhost:8080/api/fetchpoints/deduct <br>
-- request body example... {"points":5000}

- Get Point balance, Returns Balance Summary via GET Method <br>
-- localhost:8080/api/fetchpoints/balance


**Demo Videos via PostMan**
- Videos may need to be downloaded for viewing with higher resolution
- https://drive.google.com/drive/folders/1_ixkPTIHwG7udb2pqrZyKbtyvMDTV7n4?usp=sharing
