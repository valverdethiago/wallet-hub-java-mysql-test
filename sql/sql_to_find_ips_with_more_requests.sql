/* 
(1) Write MySQL query to find IPs that mode more than a certain number 
of requests for a given time period.
*/

  SELECT ip_address 
    FROM http_access 
   WHERE date between :startDate and :finalDate 
GROUP BY ip_address 
  HAVING count(*) > :threshold;

/* 
*USAGE Example*
  SELECT ip_address
    FROM http_access 
   WHERE date between '2017-01-01.15:00:00' and '2017-01-01.16:00:00'
GROUP BY ip_address
  HAVING count(*) > 200;
*/
