insert into cart (id, user_id) values (1, 1);
insert into cart (id, user_id) values (2, 2);

insert into cart_product (id, count, product_id, cart_id) values (1, 2, 1, 1);
insert into cart_product (id, count, product_id, cart_id) values (2, 3, 2, 1);

insert into cart_product (id, count, product_id, cart_id) values (3, 2, 3, 2);
insert into cart_product (id, count, product_id, cart_id) values (4, 3, 2, 2);
