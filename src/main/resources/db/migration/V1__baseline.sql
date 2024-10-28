CREATE TABLE invocations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  country_code CHAR(2) NOT NULL,
  distance DECIMAL(8, 2) NOT NULL,
  INDEX idx_country_code (country_code)
);
