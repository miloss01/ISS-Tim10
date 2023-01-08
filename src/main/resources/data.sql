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
(1, 'neka tojota nesto', 5, 1, '007-sg', 1, 2);
--insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
--registration_plate, current_coordinates_id, vehicle_type_id) values
--(1, 'neka tojota nesto', 5, 1, '007-sg', 1, 2);
--insert into vehicle (baby_flag, model, num_of_seats, pets_flag,
--registration_plate, current_coordinates_id, vehicle_type_id) values
--(1, 'citroen', 5, 1, '007-ssadg', 2, 1);



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


insert into passenger (id) values (1);
insert into passenger (id) values (3);
insert into driver (id, vehicle_id) values (2, 1);
insert into driver (id, vehicle_id) values (4, 2);
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
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'active', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-08-11 07:15:24', 15, 1, 0, 100.0, 'finished', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2024-02-05 07:03:24', 15, 1, 0, 100.0, 'finished', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-07-11 07:03:24', 15, 1, 0, 100.0, 'accepted', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 2);
insert into ride (baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'pending', 2);

insert into passengers_rides (passenger_id, ride_id) values (1, 1);
insert into passengers_rides (passenger_id, ride_id) values (1, 2);
insert into passengers_rides (passenger_id, ride_id) values (1, 3);
insert into passengers_rides (passenger_id, ride_id) values (3, 2);

insert into routes_rides (ride_id, route_id) values (1, 1);

insert into passengers_favourite_rides (passenger_id, ride_id) values (1, 1);



insert into review (comment, rating, passenger_id, ride_id, for_driver)
values ('Dobar', 5, 1, 1, 2);


insert into rejection (reason, rejection_time, app_user_id, ride_id)
values ('Ne mogu vise ovo da kucam', '2016-04-12 07:03:24', 2, 1);


insert into panic (reason, panic_time, app_user_id, ride_id)
values ('Upomoc', '2016-04-12 07:03:24', 1, 1);


insert into note (message, note_date, app_user_id)
values ('liquid_smooth.mp3', '2016-04-12 07:03:24', 1);


insert into message (text, message_type, time_sent, receiver_id, sender_id, ride_id)
values ('lolita hey', 'support', '2016-04-12 07:03:24', 1, 2, 0);


insert into document (image, title, driver_id)
values ('scr', 'licna', 2);


insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
values ('work', 'boki@DEsi.com', 'every night', 'and', 'day', 'https://material.angular.io/assets/img/examples/shiba2.jpg', 2, 0, 'promena', 'registracija', 3, 1, 1, '2020-04-12 07:03:24', 'standard');
insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
values ('work', 'so hard', 'every night', 'and', 'day', 'csr', 4, 0, 'promena', 'registracija', 3, 1, 1, '2022-04-12 07:03:24', 'standard');
--
--insert into change_request (address, email, last_name, name, phone, profile_image, driver_id, approved, model, registration_plate, num_of_seats, baby_flag, pets_flag, date_created, vehicle_type)
--values ('work', 'so hard', 'every night', 'and', 'day', 'csr', 4, 1, 'promena', 'registracija', 3, 1, 1, '2016-04-12 07:03:24', 'standard');
