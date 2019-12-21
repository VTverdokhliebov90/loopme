DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;
-- first_page
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'first_page1', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (11, 'first_page2', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (12, 'first_page3', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (13, 'first_page4', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (14, 'first_page5', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
-- second_page
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (15, 'second_page1', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (16, 'second_page2', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (17, 'second_page3', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (18, 'second_page4', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (19, 'second_page5', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);