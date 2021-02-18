------------
# Spring Boot Point Tracker
------------

**Spring Boot application for tracking the managment of reward points.**
- Creates one H2 database table named "tbl_points"
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
- https://drive.google.com/drive/folders/1U3C1uIAMcJ74qQ9n1IyAp2bymbWwNxMd?usp=sharing
