G# Restaurant Voting System

The voting system helps to decide where to have lunch. 
Users can vote for preferable restaurant by increasing its rating.

### Technologies applied in the project:
- Spring 5
- Spring Data 2
- Spring Security 5
- Maven
- Hibernate 5 
- HSQLDB
- Logging: Logback and SLF4G
- Testing: Junit 5 and Hamcrest
- Ehcache

G# CURL commands:
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
