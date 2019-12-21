DELETE FROM ads;

INSERT INTO ads (id, name, status, asset_url) VALUES (100, 'ad_name', 1, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (100, 0, 100);
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (102, 2, 100);

INSERT INTO ads (id, name, status, asset_url) VALUES (101, 'ad_name', 2, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (110, 1, 101);

INSERT INTO ads (id, name, status, asset_url) VALUES (102, 'ad_name', 3, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (120, 2, 102);