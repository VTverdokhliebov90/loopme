DELETE FROM campaigns;
DELETE FROM ads;
DELETE FROM ads_platforms;
DELETE FROM campaigns_ads;

INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'ad_name', 1, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (100, 1, 100);

INSERT INTO ads (id, name, status, asset_url) VALUES (101, 'ad_name', 2, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (110, 2, 101);