insert into vehicle_type (id, name, price) values (1, 'standard', 123.1);
insert into vehicle_type (id, name, price) values (2, 'luxury', 123.1);
insert into vehicle_type (id, name, price) values (3, 'van', 123.1);


insert into coordinates (latitude, longitude, address)
values (45.2501342, 19.8480507, 'Strazilovska 19, Novi Sad');
insert into coordinates (latitude, longitude, address)
values (45.2523302, 19.7586626, 'Fruskogorska 5, Novi Sad');


insert into vehicle (id, baby_flag, model, num_of_seats, pets_flag,
registration_plate, current_coordinates_id, vehicle_type_id) values
(1, 1, 'neka tojota nesto', 5, 1, '007-sg', 1, 1);


insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (0, 'Bulevar Oslobodjenja', 1, 'nana@DEsi.com', 'Petrovic', 'Petar', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 0);
insert into app_user (active_flag, address, blocked_flag, email, last_name, name, password, phone, profile_image, role)
values (0, 'Bulevar Oslobodjenja', 1, 'boki@DEsi.com', 'Petrovic', 'Bojan', '$2a$10$IXA3XB8wgTEXkJKIB5OCyOauVHACKU01elKgnVPcRMcXjZ56iZLEC', '0654324', 'https://www.rd.com/wp-content/uploads/2017/09/01-shutterstock_476340928-Irina-Bg.jpg?resize=768,512', 1);

insert into passenger (id) values (1);

insert into driver (id, vehicle_id) values (2, 1);


insert into admin (id, last_name, name, password, profile_image, username)
values (1, 'Mirkovic', 'Mirka', '123', 'src', 'je l ovo mejl');


insert into user_activation (id, date_created, date_expiration, app_user_id)
values (1, '2016-04-12 07:03:24', '2026-04-12 07:03:24', 1);


insert into working_time (id, start_time, end_time, driver_id)
values (1, '2016-04-12 07:03:24', '2016-04-12 07:03:24', 2);


insert into route (id, mileage, orderr, departure_coordinates_id, destination_coordinates_id)
values (1, 2.3, 1, 1, 2);


insert into ride (id, baby_flag, start_time, end_time, estimated_time_minutes,
panic_flag, pets_flag, price, ride_status, driver_id) values
(1, 0, '2026-04-12 07:03:24', '2026-04-12 07:03:24', 15, 1, 0, 100.0, 'finished', 2);


insert into passengers_rides (passenger_id, ride_id) values (1, 1);

insert into routes_rides (ride_id, route_id) values (1, 1);

insert into passengers_favourite_rides (passenger_id, ride_id) values (1, 1);


insert into review (id, comment, rating, passenger_id, ride_id, for_driver)
values (1, 'Dobar', 5, 1, 1, 2);


insert into rejection (id, reason, rejection_time, app_user_id, ride_id)
values (1, 'Ne mogu vise ovo da kucam', '2016-04-12 07:03:24', 2, 1);


insert into panic (id, reason, panic_time, app_user_id, ride_id)
values (1, 'Upomoc', '2016-04-12 07:03:24', 1, 1);


insert into note (id, message, note_date, app_user_id)
values (1, 'liquid_smooth.mp3', '2016-04-12 07:03:24', 1);


insert into message (id, text, message_type, time_sent, receiver_id, sender_id, ride_id)
values (1, 'lolita hey', 'support', '2016-04-12 07:03:24', 1, 2, 0);


insert into document (id, image, title, driver_id)
values (1, 'scr', 'licna', 2);


insert into change_request (id, address, email, last_name, name, phone, profile_image, driver_id, approved)
values (1, 'work', 'so hard', 'every night', 'and', 'day', 'csr', 2, 0);





