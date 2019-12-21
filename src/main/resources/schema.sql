DROP TABLE IF EXISTS campaigns;
CREATE TABLE campaigns (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  status INT NOT NULL,
  start_date TIMESTAMP,
  end_date TIMESTAMP
);

DROP TABLE IF EXISTS ads;
CREATE TABLE ads (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  status INT NOT NULL,
  asset_url VARCHAR(250)
);

DROP TABLE IF EXISTS ads_platforms;
CREATE TABLE ads_platforms (
  id INT AUTO_INCREMENT PRIMARY KEY,
  platform INT NOT NULL,
  ad_id INT NOT NULL,
  FOREIGN KEY(ad_id) REFERENCES ads(id) ON UPDATE CASCADE ON DELETE CASCADE
);

DROP TABLE IF EXISTS campaigns_ads;
CREATE TABLE campaigns_ads (
  campaign_id INT NOT NULL,
  ad_id INT NOT NULL,
  PRIMARY KEY(campaign_id, ad_id),
  FOREIGN KEY(campaign_id) REFERENCES campaigns(id) ON UPDATE CASCADE ON DELETE CASCADE,
  FOREIGN KEY(ad_id) REFERENCES ads(id) ON UPDATE CASCADE ON DELETE CASCADE
);