DELETE FROM ads;

INSERT INTO ads (id, name, status, asset_url) VALUES (900, 'ad_900', 1, 'asset_url/900');
INSERT INTO ads_platforms (id, platform, ad_id) VALUES (900, 1, 900);

