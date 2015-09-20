CREATE TABLE Bookinfo (id INT NOT NULL , 
tittle VARCHAR(30) ,
auther VARCHAR(30), 
publisher VARCHAR(100), 
release_date DATE, 
list_price double(10,2),
is_proceed INT DEFAULT 0,
PRIMARY KEY (ID)
);
 
 
delimiter $$
CREATE  PROCEDURE insertBookinfo(
IN ID INT,
IN TITTLE VARCHAR(30),
IN AUTHER varchar(30),
IN PUBLISHER varchar(100),
IN RELEASE_DATE DATE,
IN LIST_PRICE double(10,2)
)
BEGIN
INSERT INTO Bookinfo
( id , tittle, auther, publisher, 
release_date,  list_price, is_proceed)
VALUES ( ID , TITTLE , AUTHER , PUBLISHER ,
RELEASE_DATE , LIST_PRICE, 0 ) 
ON DUPLICATE KEY UPDATE id = ID, tittle = TITTLE,
auther=AUTHER, publisher=PUBLISHER, release_date= RELEASE_DATE,
list_price=LIST_PRICE, is_proceed=0;
END
$$

delimiter ;

    