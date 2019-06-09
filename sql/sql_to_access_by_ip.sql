/* 
(2) Write MySQL query to find requests made by a given IP.
*/

select * 
  from http_access 
 where ip_address = :ip;

/* 
*USAGE Example*
select * 
  from http_access 
 where ip_address = '192.168.11.231';
*/
