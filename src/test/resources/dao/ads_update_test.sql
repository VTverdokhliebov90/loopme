DELETE FROM ads;

INSERT INTO ads (id, name, status, asset_url) VALUES (200, 'ad_name', 0, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (200, 0, 200);
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (201, 1, 200);
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (202, 2, 200);

INSERT INTO ads (id, name, status, asset_url) VALUES (201, 'ad_name', 0, 'asset_url/ad_name');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (211, 1, 201);
