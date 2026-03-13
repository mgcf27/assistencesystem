CREATE UNIQUE INDEX ux_one_open_so_per_product
ON service_orders (prod_id)
WHERE status IN ('OPEN', 'IN_PROGRESS', 'WAITING_PARTS');