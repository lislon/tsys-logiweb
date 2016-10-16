INSERT IGNORE INTO logiweb_test.cities VALUES
   (1,'Moscow',55.752220,37.615559),
   (2,'St. Petersburg',59.938629,30.314131),
   (3,'Berlin',52.516666,13.400000);

# INSERT IGNORE INTO logiweb_test.drivers (id, personal_code, first_name, last_name, hours_worked, status, city_id, truck_id) VALUES
#    (1,'elephant','Slon','Driver',140,'REST',3,NULL),
#    (2,'lisa','Lisa','Driver',0,'REST',1,NULL);
#
# INSERT IGNORE INTO logiweb_test.trucks (id, name, max_drivers, capacity_kg, `condition`, city_id) VALUES
#   (1, "FH0001", 3, 12000, 'OK', 2),
#   (2, "DF0001", 3, 12000, 'OK', 1);
#
# INSERT IGNORE INTO logiweb_test.orders (id, truck_id, date_created, date_completed, is_completed) VALUES
#   (1, 1, NOW(), NULL, 0);
#
# INSERT IGNORE INTO logiweb_test.cargoes (id, name, title, weight, status, order_id) VALUES
#   (1, "CARGO_TV", "Tv", 1000, 'PREPARED', 1),
#   (2, "CARGO_PL", "Playstation", 500, 'PREPARED', 1)
# ;
#
#
