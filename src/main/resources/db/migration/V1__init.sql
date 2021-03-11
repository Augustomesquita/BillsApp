CREATE TABLE bill (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  name varchar(255) NOT NULL,
  due_date bigint(20) NOT NULL,
  payment_date bigint(20) NOT NULL,
  original_value DECIMAL NOT NULL,
  new_value DECIMAL NOT NULL,
  rule varchar(100) NOT NULL,
  delayed_days INT NOT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;