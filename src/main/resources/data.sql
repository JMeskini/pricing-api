INSERT INTO prices (brand_id, start_date, end_date, price_list, product_id, priority, price, curr)
SELECT
    BrandId,
    PARSEDATETIME(StartDate, 'yyyy-MM-dd-HH.mm.ss'),
    PARSEDATETIME(EndDate, 'yyyy-MM-dd-HH.mm.ss'),
    PriceList,
    ProductId,
    Priority,
    Price,
    Currency
FROM CSVREAD('classpath:static/prices_.csv', NULL, 'charset=UTF-8 fieldSeparator=,');
