# spring-boot-currencyfair-test
This is the github repo per the request of the Currency Fair Engineering for the employment test.
The test code uses a POJO for the MarketTrade process with a controller to consume the pushed data via a POST to
data.sparkfun.com which is the initial data fetched from the JSONStreamParser running @localhost:8080 via a 
GET with no params just the root URL.

I coded and ran everthing from within Intellij 14 starting with running the MarketTrade jar server (embedded Tomcat).

With the consuming server running I then ran the JPhant_Currency_Fair_Test that fetches the original Currency Fair
JSON format data string. 

The original data is then pushed to the MarketTrade server that randomly replaces the Amount Buy, the Amount Sell 
and the rate values to give the data some spread. 

50 data points are generated in a loop that gives back a rate limiting status among various other statuses. 

Once the data has been successfully pushed to data.sparkfun using the provided Apache HttpClient the data is 
then rendered using the phant.io recommended  Google Charts examples. 

The JPhant library is a fork of the JPhant library found on github and recommended by sparkfun.com. 

I included the JPhant libary as a jar in case the hardwired pom proves to be too unwieldly to build 
the JPhant library locally from source.
