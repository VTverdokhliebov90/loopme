DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;
-- first_page
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'find_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (11, 'some_name', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (12, 'find_name', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (13, 'some_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (14, 'find_name', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (15, 'some_name', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (16, 'find_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (17, 'some_name', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (18, 'find_name', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (19, 'some_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (20, 'find_name', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);