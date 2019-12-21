DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'some_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'some_name', 0, 'asset_url/some_name');
INSERT INTO ads (id, name, status, asset_url) VALUES (101, 'some_name', 0, 'asset_url/some_name');
INSERT INTO ads (id, name, status, asset_url) VALUES (102, 'some_name', 0, 'asset_url/some_name');
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 100);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 101);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 102);

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (11, 'some_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (110, 'some_name', 0, 'asset_url/some_name');
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (11, 110);

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (12, 'some_name', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (120, 'some_name', 0, 'asset_url/some_name');
INSERT INTO ads (id, name, status, asset_url) VALUES (121, 'some_name', 0, 'asset_url/some_name');
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (12, 120);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (12, 121);
