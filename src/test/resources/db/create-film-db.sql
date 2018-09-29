--DROP TABLE country

CREATE TABLE country (
	country_id int2 PRIMARY KEY,
	country varchar(50) NOT NULL,
	last_update timestamp NOT NULL
);

CREATE TABLE city (
	city_id serial NOT NULL,
	city varchar(50) NOT NULL,
	country_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT city_pkey PRIMARY KEY (city_id),
	CONSTRAINT fk_city FOREIGN KEY (country_id) REFERENCES country(country_id)
);
CREATE INDEX idx_fk_country_id ON city (country_id) ;

CREATE TABLE address (
	address_id serial NOT NULL,
	address varchar(50) NOT NULL,
	address2 varchar(50) NULL,
	district varchar(20) NOT NULL,
	city_id int2 NOT NULL,
	postal_code varchar(10) NULL,
	phone varchar(20) NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT address_pkey PRIMARY KEY (address_id),
	CONSTRAINT fk_address_city FOREIGN KEY (city_id) REFERENCES city(city_id)
);
CREATE INDEX idx_fk_city_id ON address (city_id) ;


CREATE TABLE staff (
	staff_id serial NOT NULL,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	address_id int2 NOT NULL,
	email varchar(50) NULL,
	store_id int2 NOT NULL,
	active bool NOT NULL DEFAULT true,
	username varchar(16) NOT NULL,
	password varchar(40) NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	picture bytea NULL,
	CONSTRAINT staff_pkey PRIMARY KEY (staff_id),
	CONSTRAINT staff_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id)
);


CREATE TABLE store (
	store_id serial NOT NULL,
	manager_staff_id int2 NOT NULL,
	address_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT store_pkey PRIMARY KEY (store_id),
	CONSTRAINT store_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id),
	CONSTRAINT store_manager_staff_id_fkey FOREIGN KEY (manager_staff_id) REFERENCES staff(staff_id)
);
CREATE UNIQUE INDEX idx_unq_manager_staff_id ON store (manager_staff_id) ;

CREATE TABLE customer (
	customer_id serial NOT NULL,
	store_id int2 NOT NULL,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	email varchar(50) NULL,
	address_id int2 NOT NULL,
	activebool bool NOT NULL DEFAULT true,
	create_date date NOT NULL DEFAULT GETDATE(),
	last_update timestamp NULL DEFAULT now(),
	active int4 NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (customer_id),
	CONSTRAINT customer_address_id_fkey FOREIGN KEY (address_id) REFERENCES address(address_id)
);
CREATE INDEX idx_fk_address_id ON customer (address_id) ;
CREATE INDEX idx_fk_store_id ON customer (store_id) ;
CREATE INDEX idx_last_name ON customer (last_name) ;

CREATE TABLE language (
	language_id serial NOT NULL,
	name varchar(20) NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT language_pkey PRIMARY KEY (language_id)
);

CREATE TABLE film (
	film_id serial NOT NULL,
	title varchar(255) NOT NULL,
	description varchar(255) NULL, --bio je tip polja text
	release_year year NULL,
	language_id int2 NOT NULL,
	rental_duration int2 NOT NULL DEFAULT 3,
	rental_rate numeric(4,2) NOT NULL DEFAULT 4.99,
	length int2 NULL,
	replacement_cost numeric(5,2) NOT NULL DEFAULT 19.99,
	rating varchar(5) NULL DEFAULT 'G',
	last_update timestamp NOT NULL DEFAULT now(),
	special_features clob NULL,
	fulltext clob NOT NULL,
	CONSTRAINT film_pkey PRIMARY KEY (film_id),
	CONSTRAINT film_language_id_fkey FOREIGN KEY (language_id) REFERENCES language(language_id)
);

CREATE INDEX idx_fk_language_id ON film (language_id) ;
CREATE INDEX idx_title ON film (title) ;

CREATE TABLE inventory (
	inventory_id serial NOT NULL,
	film_id int2 NOT NULL,
	store_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT inventory_pkey PRIMARY KEY (inventory_id),
	CONSTRAINT inventory_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id)
);
CREATE INDEX idx_store_id_film_id ON inventory (store_id, film_id) ;


CREATE TABLE rental (
	rental_id serial NOT NULL,
	rental_date timestamp NOT NULL,
	inventory_id int4 NOT NULL,
	customer_id int2 NOT NULL,
	return_date timestamp NULL,
	staff_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT rental_pkey PRIMARY KEY (rental_id),
	CONSTRAINT rental_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	CONSTRAINT rental_inventory_id_fkey FOREIGN KEY (inventory_id) REFERENCES inventory(inventory_id),
	CONSTRAINT rental_staff_id_key FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);
CREATE INDEX idx_fk_inventory_id ON rental(inventory_id) ;
CREATE UNIQUE INDEX idx_unq_rental_rental_date_inventory_id_customer_id ON rental (rental_date, inventory_id, customer_id) ;


CREATE TABLE payment (
	payment_id serial NOT NULL,
	customer_id int2 NOT NULL,
	staff_id int2 NOT NULL,
	rental_id int4 NOT NULL,
	amount numeric(5,2) NOT NULL,
	payment_date timestamp NOT NULL,
	CONSTRAINT payment_pkey PRIMARY KEY (payment_id),
	CONSTRAINT payment_customer_id_fkey FOREIGN KEY (customer_id) REFERENCES customer(customer_id),
	CONSTRAINT payment_rental_id_fkey FOREIGN KEY (rental_id) REFERENCES rental(rental_id),
	CONSTRAINT payment_staff_id_fkey FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
);
CREATE INDEX idx_fk_customer_id ON payment (customer_id) ;
CREATE INDEX idx_fk_rental_id ON payment (rental_id) ;
CREATE INDEX idx_fk_staff_id ON payment (staff_id) ;

CREATE TABLE actor (
	actor_id serial NOT NULL,
	first_name varchar(45) NOT NULL,
	last_name varchar(45) NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT actor_pkey PRIMARY KEY (actor_id)
);
CREATE INDEX idx_actor_last_name ON actor (last_name) ;

CREATE TABLE category (
	category_id serial NOT NULL,
	name varchar(25) NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT category_pkey PRIMARY KEY (category_id)
);

CREATE TABLE film_category (
	film_id int2 NOT NULL,
	category_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT film_category_pkey PRIMARY KEY (film_id, category_id),
	CONSTRAINT film_category_category_id_fkey FOREIGN KEY (category_id) REFERENCES category(category_id),
	CONSTRAINT film_category_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id)
);

CREATE TABLE film_actor (
	actor_id int2 NOT NULL,
	film_id int2 NOT NULL,
	last_update timestamp NOT NULL DEFAULT now(),
	CONSTRAINT film_actor_pkey PRIMARY KEY (actor_id, film_id),
	CONSTRAINT film_actor_actor_id_fkey FOREIGN KEY (actor_id) REFERENCES actor(actor_id),
	CONSTRAINT film_actor_film_id_fkey FOREIGN KEY (film_id) REFERENCES film(film_id)
);
CREATE INDEX idx_fk_film_id ON film_actor (film_id);
