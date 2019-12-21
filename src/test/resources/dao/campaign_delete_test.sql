DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'campaign_name', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'ad_name', 2, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (100, 0, 100);
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (101, 1, 100);
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (102, 2, 100);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 100);