insert into vehicle_type (name, price) values ('standard', 10);
insert into vehicle_type (name, price) values ('luxury', 20);
insert into vehicle_type (name, price) values ('van', 30);


insert into coordinates (latitude, longitude, address)
values (45.2366791, 19.8160032, 'Mornarska 2, Novi Sad');
insert into coordinates (latitude, longitude, address)
values (45.2471018, 19.8328788, 'Gogoljeva 32, Novi Sad');


insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 1, 1);
insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 2);
insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'nova tojota', 5, 1, '007-sg', 1, 2);
insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'citroen', 5, 1, '007-ssadg', 2, 1);



insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (0, 'Bulevar Oslobodjenja', 0, 'nana@DEsi.com', 'Petrovic', 'Petar', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (0, 'Bulevar Oslobodjenja', 0, 'boki@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Misurinova', 0, 'mail@gmail.com', 'Milic', 'Milica', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'popov@gmail.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 1, 'dmina@gmail.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 1, 'pass@DEsi.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 1, 'driver@DEsi.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);


insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'marija@DEsi.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'zakontrolerPutnik@DEsi.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'zakontrolerVozac@DEsi.com', 'Popov', 'Sandra', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 2);


insert into passenger (id) values (1);
insert into passenger (id) values (3);
insert into passenger (id) values (6);
insert into passenger (id) values (8);
insert into passenger (id) values (9);
insert into driver (id, vehicle_id) values (2, 1);
insert into driver (id, vehicle_id) values (4, 2);
insert into driver (id, vehicle_id) values (7, 3);
insert into driver (id, vehicle_id) values (10, 4);

insert into admin (id) values (5);

--insert into admin (last_name, name, password, profile_image, username)
--values ('Mirkovic', 'Mirka', '123', 'src', 'je l ovo mejl');


--insert into user_activation (name, last_name, phone, email, password, profile_image, address, date_created, date_expiration)
--values ('Petar', '2016-04-12 07:03:24', '2026-04-12 07:03:24');


insert into working_time (start_time, end_time, driver_id)
values ('2016-04-12 07:03:24', '2016-04-12 07:03:24', 2);
insert into working_time (start_time, end_time, driver_id)
values ('2023-01-04 20:03:24', '2023-01-04 20:33:24', 2);


insert into route (mileage, orderr, departure_coordinates_id, destination_coordinates_id)
values (2.3, 1, 1, 2);


insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-06-12 07:03:24', '2026-06-12 07:03:24', 15, 1, 0, 100.0, 'active', 2); --withdraw koristi
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 08:03:24', 15, 1, 0, 100.0, 'accepted', 2); --koristi
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-05-12 07:03:24', '2026-05-13 07:15:24', 15, 1, 0, 100.0, 'pending', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'accepted', 7); --withdraw koristi
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'pending', 7); --withdraw koristi
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 4); --koristi
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2024-02-05 07:03:24', 15, 1, 0, 100.0, 'finished', 4);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-07-11 07:03:24', 15, 1, 0, 100.0, 'finished', 4);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-07-11 07:03:24', 15, 1, 0, 100.0, 'pending', 10);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-07-11 07:03:24', 15, 1, 0, 100.0, 'accepted', 10);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-07-11 07:03:24', 15, 1, 0, 100.0, 'active', 10);



insert into passengers_rides (passenger_id, ride_id) values (1, 1);
insert into passengers_rides (passenger_id, ride_id) values (1, 2);
insert into passengers_rides (passenger_id, ride_id) values (1, 3);
insert into passengers_rides (passenger_id, ride_id) values (6, 4);
insert into passengers_rides (passenger_id, ride_id) values (6, 5);
insert into passengers_rides (passenger_id, ride_id) values (8, 6);
insert into passengers_rides (passenger_id, ride_id) values (8, 7);
insert into passengers_rides (passenger_id, ride_id) values (8, 8);
insert into passengers_rides (passenger_id, ride_id) values (9, 9);
insert into passengers_rides (passenger_id, ride_id) values (9, 10);
insert into passengers_rides (passenger_id, ride_id) values (9, 11);

insert into routes_rides (ride_id, route_id) values (1, 1);
insert into routes_rides (ride_id, route_id) values (2, 1);
insert into routes_rides (ride_id, route_id) values (3, 1);
insert into routes_rides (ride_id, route_id) values (4, 1);
insert into routes_rides (ride_id, route_id) values (5, 1);
insert into routes_rides (ride_id, route_id) values (6, 1);
insert into routes_rides (ride_id, route_id) values (7, 1);
insert into routes_rides (ride_id, route_id) values (8, 1);
insert into routes_rides (ride_id, route_id) values (9, 1);
insert into routes_rides (ride_id, route_id) values (10, 1);
insert into routes_rides (ride_id, route_id) values (11, 1);
insert into review (comment, rating, passenger_id, ride_id, for_driver)
values ('Dobar', 5, 1, 1, 2);


insert into rejection (reason, rejection_time, app_user_id, ride_id)
values ('Ne mogu vise ovo da kucam', '2016-04-12 07:03:24', 2, 1);


insert into panic (reason, panic_time, app_user_id, ride_id)
values ('Upomoc', '2016-04-12 07:03:24', 1, 1);


insert into note (message, note_date, app_user_id)
values ('liquid_smooth.mp3', '2016-04-12 07:03:24', 1);


insert into message (text, message_type, time_sent, receiver_id, sender_id, ride_id)
values ('lolita hey', 'support', '2016-04-12 07:03:24', 1, 5, 0);
insert into message (text, message_type, time_sent, receiver_id, sender_id, ride_id)
values ('lolita heyyy', 'support', '2016-03-12 07:03:24', 5, 1, 0);


insert into document (image, title, driver_id)
values ('scr', 'licna', 2);


insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
values ('work', 'boki@DEsi.com', 'every night', 'and', 'day', 'https://material.angular.io/assets/img/examples/shiba2.jpg', 2, 0, 'promena', 'registracija', 3, 1, 1, '2020-04-12 07:03:24', 'standard');
insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
values ('work', 'so hard', 'every night', 'and', 'day', 'csr', 4, 0, 'promena', 'registracija', 3, 1, 1, '2022-04-12 07:03:24', 'standard');
--
--insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
--values ('work', 'so hard', 'every night', 'and', 'day', 'csr', 4, 1, 'promena', 'registracija', 3, 1, 1, '2016-04-12 07:03:24', 'standard');
--insert into favorite_locations (baby_transport, favourite_name)




insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testExecute@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testCancel@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testHasOnePending@DEsi.com', 'Petrovic', 'Petar', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testHasNoRidesPassenger@DEsi.com', 'Petrovic', 'Petar', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testHasNoRidesDriver@DEsi.com', 'Petrovic', 'Petar', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testExecuteSelenium@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testBookingSelenium@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);

insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (1, 'Bulevar Oslobodjenja', 0, 'testEndSelenium@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);


insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 2);

insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 2);

insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 1);

insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 1);

insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 'neka tojota nesto', 5, 1, '007-sg', 2, 2);

insert into driver (id, vehicle_id) values (11, 5);
insert into driver (id, vehicle_id) values (12, 6);
insert into driver (id, vehicle_id) values (15, 7);
insert into driver (id, vehicle_id) values (16, 8);
insert into driver (id, vehicle_id) values (18, 9);


insert into passenger (id) values (13);
insert into passenger (id) values (14);
insert into passenger (id) values (17);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'pending', 11);


insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'accepted', 12);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 12);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'pending', 11);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'pending', 16);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 06:15:00', '2026-04-12 07:00:00', 15, 1, 0, 100.0, 'active', 18);

insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:00', '2026-04-12 07:15:00', 15, 1, 0, 100.0, 'accepted', 18);

insert into routes_rides (ride_id, route_id) values (12, 1);
insert into routes_rides (ride_id, route_id) values (13, 1);
insert into routes_rides (ride_id, route_id) values (14, 1);
insert into routes_rides (ride_id, route_id) values (15, 1);
insert into routes_rides (ride_id, route_id) values (16, 1);
insert into routes_rides (ride_id, route_id) values (17, 1);
insert into routes_rides (ride_id, route_id) values (18, 1);

insert into passengers_rides (passenger_id, ride_id) values (13, 12);
insert into passengers_rides (passenger_id, ride_id) values (14, 14);


insert into location (latitude, longitude, address)
values (45.2366791, 19.8160032, 'Mornarska 2, Novi Sad');
insert into location (latitude, longitude, address)
values (45.2471018, 19.8328788, 'Gogoljeva 32, Novi Sad');

insert into departure_destination (departure_id, destination_id)
values (1, 2);

insert into favorite_locations (favorite_name, maker_id, vehicle_type_id, baby_transport, pet_transport)
values ('home-work', 14, 1, 0, 0);
insert into favorite_locations (favorite_name, maker_id, vehicle_type_id, baby_transport, pet_transport)
values ('salas', 14, 1, 0, 0);