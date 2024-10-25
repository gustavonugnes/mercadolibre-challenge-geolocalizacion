CREATE TABLE invocations (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  country_code CHAR(2) NOT NULL,
  distance DECIMAL(8, 2) NOT NULL,
  amount BIGINT NOT NULL
);

--
--CREATE TABLE transactions (
--  id BIGINT AUTO_INCREMENT PRIMARY KEY,
--  source_user_id INT NOT NULL,
--  target_user_id INT NULL,
--  type VARCHAR(255) NOT NULL,
--  amount DECIMAL(15, 2) NOT NULL,
--  description VARCHAR(255) NULL,
--  time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP NOT NULL,
--  INDEX idx_source_user_id (source_user_id),
--  INDEX idx_target_user_id (target_user_id),
--  INDEX idx_type (type),
--  INDEX idx_time (time)
--);

