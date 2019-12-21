DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

-- case updateAll with extra ad
INSERT INTO campaigns (id, name, status, start_date, end_date) VALUES (10, 'campaign_name1', 0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'ad_name11', 0, 'asset_url/ad_name11');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (100, 0, 100);
INSERT INTO ads (id, name, status, asset_url) VALUES (101, 'ad_name12', 1, 'asset_url/ad_name12');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (110, 1, 101);
INSERT INTO ads (id, name, status, asset_url) VALUES (102, 'ad_name13', 2, 'asset_url/ad_name13');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (120, 2, 102);

INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 100);
INSERT INTO campaigns_ads (campaign_id, ad_id) VALUES (10, 101);