# Restaurant Voting System
> !Dishes without dates

The voting system helps to decide where to have lunch. 
Users can vote for preferable restaurant by increasing its rating.  
  
2 types of users: admin and regular users.
Admin can input a restaurant and its lunch menu of the day (2-5 items usually, just a dish name and price).
Menu changes each day (admins do the updates).
Users can vote on which restaurant they want to have lunch at.
Only one vote counted per user.
If user votes again the same day:
    If it's before 11:00 we assume that they changed their mind;
    If it's after 11:00 then it's too late, vote can't be changed.


## Technologies applied in the project:
- Spring 5
- Spring Data 2
- Spring Security 5
- Maven
- Tomcat 9
- Hibernate 5 
- HSQLDB
- Logging: Logback and SLF4G
- Testing: Junit 5 and Hamcrest
- Ehcache

## CURL commands:
### curl samples (application deployed in application context `restaurantvotingsystem`).
> For windows use `Git Bash`

#### get All Users
`curl -s http://localhost:8080/restaurantvoting/rest/admin/users --user admin@gmail.com:admin`

#### get Users 100001
`curl -s http://localhost:8080/restaurantvoting/rest/admin/users/100001 --user admin@gmail.com:admin`

#### register Users
`curl -s -i -X POST -d '{"name":"New User","email":"test@mail.ru","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restaurantvoting/rest/profile/register`

#### get Activity
`curl -s http://localhost:8080/restaurantvoting/rest/profile --user user@yandex.ru:password`

#### get Profile
`curl -s http://localhost:8080/restaurantvoting/rest/profile/100000 --user user@yandex.ru:password`

#### get All Restaurants
`curl -s http://localhost:8080/restaurantvoting/rest/restaurants --user user@yandex.ru:password`

#### get Restaurants 100003
`curl -s http://localhost:8080/restaurantvoting/rest/restaurants/100003  --user user@yandex.ru:password`

#### vote for Restaurant
`curl -s PUT http://localhost:8080/restaurantvoting/rest/restaurants/100003 --user user@yandex.ru:password`

#### delete Restaurants
`curl -s -X DELETE http://localhost:8080/restaurantvoting/rest/restaurants/100003 --user admin@gmail.com:admin`

#### create Restaurants
`curl -s -X POST -d '{"name":"newRestaurant","menu":"[]"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restaurantvoting/rest/restaurants --user admin@gmail.com:admin`

#### update Restaurants
`curl -s -X POST -d '{"name":"updatedRestaurant", "menu":"[]"}' -H 'Content-Type: application/json' http://localhost:8080/restaurantvoting/rest/restaurants --user admin@gmail.com:admin`

#### update Menu
`curl -s -X POST -d '[{"name":"newDish", "price":"9.99"}]' -H 'Content-Type: application/json' http://localhost:8080/restaurantvoting/rest/restaurants/100003 --user admin@gmail.com:admin`
