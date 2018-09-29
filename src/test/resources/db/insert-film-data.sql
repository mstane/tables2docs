INSERT INTO country (country_id, country, last_update) VALUES(8, 'Australia', '2006-02-15 09:44:00.000');
INSERT INTO country (country_id, country, last_update) VALUES(20, 'Canada', '2006-02-15 09:44:00.000');
INSERT INTO country (country_id, country, last_update) VALUES(45, 'Indonesia', '2006-02-15 09:44:00.000');
INSERT INTO country (country_id, country, last_update) VALUES(50, 'Japan', '2006-02-15 09:44:00.000');
INSERT INTO country (country_id, country, last_update) VALUES(103, 'United States', '2006-02-15 09:44:00.000');
INSERT INTO country (country_id, country, last_update) VALUES(72, 'Pakistan', '2006-02-15 09:44:00.000');

INSERT INTO city (city_id, city, country_id, last_update) VALUES(576, 'Woodridge', 8, '2006-02-15 09:45:25.000');
INSERT INTO city (city_id, city, country_id, last_update) VALUES(300, 'Lethbridge', 20, '2006-02-15 09:45:25.000');
INSERT INTO city (city_id, city, country_id, last_update) VALUES(449, 'San Bernardino', 103, '2006-02-15 09:45:25.000');
INSERT INTO city (city_id, city, country_id, last_update) VALUES(463, 'Sasebo', 50, '2006-02-15 09:45:25.000');
INSERT INTO city (city_id, city, country_id, last_update) VALUES(117, 'Cianjur', 45, '2006-02-15 09:45:25.000');
INSERT INTO city (city_id, city, country_id, last_update) VALUES(134, 'Dadu', 72, '2006-02-15 09:45:25.000');


INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(1, '47 MySakila Drive', NULL, 'Alberta', 300, '', '', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(2, '28 MySQL Boulevard', NULL, 'QLD', 576, '', '', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(3, '23 Workhaven Lane', NULL, 'Alberta', 300, '', '14033335568', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(4, '1411 Lillydale Drive', NULL, 'QLD', 576, '', '6172235589', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(5, '1913 Hanoi Way', '', 'Nagasaki', 463, '35200', '28303384290', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(6, '1121 Loja Avenue', '', 'California', 449, '17886', '838635286649', '2006-02-15 09:45:30.000');
INSERT INTO address (address_id, address, address2, district, city_id, postal_code, phone, last_update) VALUES(421, '966 Arecibo Loop', '', 'Sind', 134, '94018', '15273765306', '2006-02-15 09:45:30.000');

INSERT INTO staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) VALUES(1, 'Mike', 'Hillyer', 3, 'Mike.Hillyer@sakilastaff.com', 1, true, 'Mike', '8cb2237d0679ca88db6464eac60da96345513964', '2006-05-16 16:13:11.793', null);
INSERT INTO staff (staff_id, first_name, last_name, address_id, email, store_id, active, username, password, last_update, picture) VALUES(2, 'Jon', 'Stephens', 4, 'Jon.Stephens@sakilastaff.com', 2, true, 'Jon', '8cb2237d0679ca88db6464eac60da96345513964', '2006-05-16 16:13:11.793', NULL);

INSERT INTO store (store_id, manager_staff_id, address_id, last_update) VALUES(1, 1, 1, '2006-02-15 09:57:12.000');
INSERT INTO store (store_id, manager_staff_id, address_id, last_update) VALUES(2, 2, 2, '2006-02-15 09:57:12.000');

INSERT INTO customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) VALUES(1, 1, 'Mary', 'Smith', 'mary.smith@sakilacustomer.org', 5, true, '2006-02-14', '2013-05-26 14:49:45.738', 1);
INSERT INTO customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) VALUES(2, 1, 'Patricia', 'Johnson', 'patricia.johnson@sakilacustomer.org', 6, true, '2006-02-14', '2013-05-26 14:49:45.738', 1);
INSERT INTO customer (customer_id, store_id, first_name, last_name, email, address_id, activebool, create_date, last_update, active) VALUES(416, 2, 'Jeffery', 'Pinson', 'jeffery.pinson@sakilacustomer.org', 421, true, '2006-02-14', '2013-05-26 14:49:45.738', 1);

INSERT INTO language (language_id, name, last_update) VALUES(1, 'English             ', '2006-02-15 10:02:19.000');

INSERT INTO film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) VALUES(1, 'Academy Dinosaur', 'A Epic Drama of a Feminist And a Mad Scientist who must Battle a Teacher in The Canadian Rockies', 2006, 1, 6, 0.99, 86, 20.99, 'PG', '2013-05-26 14:50:58.951', '{Deleted Scenes,Behind the Scenes}', '');
INSERT INTO film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) VALUES(2, 'Ace Goldfinger', 'A Astounding Epistle of a Database Administrator And a Explorer who must Find a Car in Ancient China', 2006, 1, 3, 4.99, 48, 12.99, 'G', '2013-05-26 14:50:58.951', '{Trailers,Deleted Scenes}', '');
INSERT INTO film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) VALUES(3, 'Adaptation Holes', 'A Astounding Reflection of a Lumberjack And a Car who must Sink a Lumberjack in A Baloon Factory', 2006, 1, 7, 2.99, 50, 18.99, 'NC-17', '2013-05-26 14:50:58.951', '{Trailers,Deleted Scenes}', '');
INSERT INTO film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) VALUES(22, 'Amistad Midsummer', 'A Emotional Character Study of a Dentist And a Crocodile who must Meet a Sumo Wrestler in California', 2006, 1, 6, 2.99, 85, 10.99, 'G', '2013-05-26 14:50:58.951', '{Commentaries,Behind the Scenes}', '');
INSERT INTO film (film_id, title, description, release_year, language_id, rental_duration, rental_rate, length, replacement_cost, rating, last_update, special_features, fulltext) VALUES(356, 'Giant Troopers', 'A Fateful Display of a Feminist And a Monkey who must Vanquish a Monkey in The Canadian Rockies', 2006, 1, 5, 2.99, 102, 10.99, 'R', '2013-05-26 14:50:58.951', '{Trailers,Commentaries}', '');

INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES(1, 1, 1, '2006-02-15 10:09:17.000');
INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES(9, 2, 2, '2006-02-15 10:09:17.000');
INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES(14, 3, 2, '2006-02-15 10:09:17.000');
INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES(108, 22, 1, '2006-02-15 10:09:17.000');
INSERT INTO inventory (inventory_id, film_id, store_id, last_update) VALUES(1632, 356, 2, '2006-02-15 10:09:17.000');

INSERT INTO rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) VALUES(10437, '2005-08-01 08:51:04.000', 14, 1, '2005-08-10 12:12:04.000', 1, '2006-02-16 02:30:53.000');
INSERT INTO rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) VALUES(8326, '2005-07-29 03:58:49.000', 108, 1, '2005-08-01 05:16:49.000', 2, '2006-02-16 02:30:53.000');
INSERT INTO rental (rental_id, rental_date, inventory_id, customer_id, return_date, staff_id, last_update) VALUES(1158, '2005-06-14 22:53:33.000', 1632, 416, '2005-06-18 21:37:33.000', 2, '2006-02-16 02:30:53.000');

INSERT INTO payment (payment_id, customer_id, staff_id, rental_id, amount, payment_date) VALUES(17793, 416, 2, 1158, 2.99, '2007-02-14 21:21:59.996');

INSERT INTO actor (actor_id, first_name, last_name, last_update) VALUES(1, 'Penelope', 'Guiness', '2013-05-26 14:47:57.620');
INSERT INTO actor (actor_id, first_name, last_name, last_update) VALUES(10, 'Christian', 'Gable', '2013-05-26 14:47:57.620');
INSERT INTO actor (actor_id, first_name, last_name, last_update) VALUES(19, 'Bob', 'Fawcett', '2013-05-26 14:47:57.620');

INSERT INTO category (category_id, name, last_update) VALUES(6, 'Documentary', '2006-02-15 09:46:27.000');
INSERT INTO category (category_id, name, last_update) VALUES(11, 'Horror', '2006-02-15 09:46:27.000');

INSERT INTO film_category (film_id, category_id, last_update) VALUES(1, 6, '2006-02-15 10:07:09.000');
INSERT INTO film_category (film_id, category_id, last_update) VALUES(2, 11, '2006-02-15 10:07:09.000');

INSERT INTO film_actor (actor_id, film_id, last_update) VALUES(1, 1, '2006-02-15 10:05:03.000');
INSERT INTO film_actor (actor_id, film_id, last_update) VALUES(10, 1, '2006-02-15 10:05:03.000');
INSERT INTO film_actor (actor_id, film_id, last_update) VALUES(19, 2, '2006-02-15 10:05:03.000');

