DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'campaign_name5', 3, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'ad_name5', 2, 'asset_url/ad_name5');
INSERT INTO ads_platforms (platform, ad_id) VALUES (0, 100);
INSERT INTO ads_platforms (platform, ad_id) VALUES (1, 100);
INSERT INTO ads_platforms (platform, ad_id) VALUES (2, 100);
INSERT INTO ads (id, name, status, asset_url) VALUES (101, 'ad_name6', 1, 'asset_url/ad_name6');
INSERT INTO ads_platforms (platform, ad_id) VALUES (0, 101);
INSERT INTO ads_platforms (platform, ad_id) VALUES (1, 101);
INSERT INTO ads_platforms (platform, ad_id) VALUES (2, 101);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 100);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 101);

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (11, 'campaign_name1', 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (110, 'ad_name3', 3, 'asset_url/ad_name3');
INSERT INTO ads_platforms (platform, ad_id) VALUES (0, 110);
INSERT INTO ads_platforms (platform, ad_id) VALUES (2, 110);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (11, 110);

INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (12, 'campaign_name1', 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
INSERT INTO ads (id, name, status, asset_url) VALUES (120, 'ad_name9', 3, 'asset_url/ad_name9');
INSERT INTO ads_platforms (platform, ad_id) VALUES (0, 120);
INSERT INTO ads_platforms (platform, ad_id) VALUES (2, 120);
INSERT INTO ads (id, name, status, asset_url) VALUES (121, 'ad_name1', 3, 'asset_url/ad_name1');
INSERT INTO ads_platforms (platform, ad_id) VALUES (1, 121);
INSERT INTO ads (id, name, status, asset_url) VALUES (122, 'ad_name2', 3, 'asset_url/ad_name2');
INSERT INTO ads_platforms (platform, ad_id) VALUES (0, 122);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (12, 120);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (12, 121);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (12, 122);