CREATE TABLE USER
(name VARCHAR(100),
userID VARCHAR(50),
emailID VARCHAR(150),
mobileNo VARCHAR(13),
id INT AUTO_INCREMENT,
CONSTRAINT usr_pk_id PRIMARY KEY (id)
);

CREATE TABLE SUBSCRIPTION
(
  id INT AUTO_INCREMENT,
  userID VARCHAR(50),
  emailID VARCHAR(150),
  auther varchar(100),
  title varchar(250),
  publisher varchar(250),
  price1 double(10,2),
  price2 double(10,2),
  price_cond varchar(10),
  date1 DATE,
  date2 DATE,
  date_cond varchar(10),
  CONSTRAINT sub_pk_id PRIMARY KEY (id) 
);

DELIMITER $$
CREATE PROCEDURE subscription_insert
(IN iAuther varchar(50),
IN iPrice1 double(10,2),
IN iPrice2 double(10,2),
IN iPrice_cond VARCHAR(10),
IN iDate1 DATE,
IN iDate2 DATE,
IN iDate_cond VARCHAR(10),
IN iUser_id VARCHAR(50),
IN iEmail_id VARCHAR(250),
IN iPublisher varchar(250),
IN iTitle VARCHAR(150)
)
BEGIN
	INSERT INTO SUBSCRIPTION (userID, emailID, auther,title, publisher,price1, 
	price2, price_cond, date1, date2, date_cond )
	VALUES(iUser_id,iEmail_id,iAuther, iTitle, iPublisher, iPrice1,
	iPrice2, iPrice_cond, iDate1, iDate2, iDate_cond);
END $$
DELIMITER ;
