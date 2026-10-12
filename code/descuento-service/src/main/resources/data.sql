INSERT INTO discounts (product_id, description, percentage) VALUES
  (1, 'Promo septiembre', 10.0),
  (2, 'Liquidacion', 25.0)
ON CONFLICT DO NOTHING;