DELIMITER $$
CREATE PROCEDURE getNewNotification()
BEGIN
SELECT id,tittle,auther,publisher, release_date, list_price from Bookinfo where is_proceed = 0;
END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE getSubscriptionList
(
IN iAuther varchar(150),
IN ipublisher varchar(250),
IN iPrice double,
IN iDate DATE,
IN iTitle varchar(250)
)
BEGIN
SELECT id, userID, emailID FROM SUBSCRIPTION 
WHERE (auther = iAuther OR auther = '' OR auther  IS NULL)
AND (title = '' OR title = iTitle OR title IS NULL)
AND ( (price_cond = 'EQ' AND price1 = iPrice) ||
(price_cond = 'LS' AND price1 > iPrice ) || 
(price_cond = 'GT' AND price1 < iPrice)  || price1 = 0.0  || price1 IS NULL)
AND ( (date_cond ='EQ' AND date1 = iDate) ||
(date_cond = 'LS' AND date1 > iDate)  || 
(date_cond = 'GT' AND date1 < iDate)  || 
date1 IS NULL OR date1 = '1970-01-01' );
END $$

DELIMITER $$
CREATE PROCEDURE invalidateRecord(
IN iNID INT
)
BEGIN
update Bookinfo SET is_proceed = 1 where id = iNID;
END $$
DELIMITER ;