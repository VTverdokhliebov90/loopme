# Getting Started

mvn spring-boot:run

Resource will be available by path
http://localhost:9090/campaign_booking

Application uses H2 database in the memory mode. 
The H2 Server should be started automatically when app is run

##Test ad url:
http://localhost:9090/campaign_booking/ad/100

Should be displayed json with available ad:

{"name":"ad_name1","assetUrl":"asset_url/ad_name1","platforms":[0,1,2],"id":100,"status":2}

##Test campaign url:
http://localhost:9090/campaign_booking/campaign/10

Should be displayed json with available campaign:

{"name":"campaign_name1","startDate":1576802213757,"endDate":1576802213757,"advertisements":[100,101],"id":10,"status":1}

##Test summaries url:
http://localhost:9090/campaign_booking/summaries?limit=100        

Should be displayed json with available summaries list:

[{"id":10,"name":"campaign_name1","status":1,"adsCount":2},{"id":11,"name":"campaign_name2","status":3,"adsCount":1}]

url params:
    
    limit   - defines number of entries will be returned default one is 0
    
    offset  - defines number of entries should be skipped to return next page
    
    name    - specify a name which should contain returned entries
    
    status  - specify a status which should contain returned entries
    
    sort_by - specify specify property according to which result will be sorted (available are: NAME, STATUS, ADS_COUNT)
        and default one is ID

