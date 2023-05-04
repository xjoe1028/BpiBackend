# BpiBackend sql
Create sql：

create table bpi (bpi_id bigint not null, code varchar(255), code_chinese_name varchar(255), description varchar(255), rate varchar(255), rate_float double, symbol varchar(255), updated varchar(255), primary key (bpi_id))

Insert sql：

INSERT INTO BPI (CODE,CODE_CHINESE_NAME,CREATED,DESCRIPTION,RATE,RATE_FLOAT,SYMBOL,UPDATED) VALUES
	 ('USD','美元','2023/05/02 12:09:29','United States Dollar','27.85',27.85,'$',NULL),
	 ('GBP','英镑','2023/05/02 12:09:29','British Pound Sterling','37.85',37.85,'£',NULL),
	 ('EUR','歐元','2023/05/02 12:09:29','Euro','31.49',31.49,'€',NULL),
	 ('CNY','人民幣','2023/05/02 12:09:29','Chinese yuan','4.39',4.39,'¥',NULL),
	 ('JPY','日元','2023/05/02 12:09:29','Japanese Yen','0.24',0.24,'¥',NULL),
	 ('KRW','韓元','2023/05/02 12:09:29','Korea Hwan','0.02',0.023,'₩',NULL);


# BpiBackend# BpiBackend
server.port = 9999
<br>
swagger-ui url: http://localhost:9999/swagger-ui/
<br>
h2 console url: http://localhost:9999/h2-console/
