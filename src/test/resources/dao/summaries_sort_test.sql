DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'name6', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (11, 'name5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (12, 'name4', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (13, 'name1', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (14, 'name3', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (15, 'name5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (16, 'name1', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (17, 'name8', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (18, 'name7', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (19, 'name2', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
