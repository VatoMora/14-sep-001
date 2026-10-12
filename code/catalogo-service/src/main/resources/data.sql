INSERT INTO catalog (product_id, name, base_price, stock) VALUES
  (1, 'Laptop Pro 14', 25000.00, 15),
  (2, 'Mouse Inalambrico', 450.00, 120),
  (3, 'Teclado Mecanico', 1800.00, 40)
ON CONFLICT DO NOTHING;
