INSERT INTO users (username, password) VALUES
('admin', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2uheWG/igi.');

INSERT INTO box (txref, weight_limit, battery_capacity, state) VALUES
('BOX001', 500, 85, 'IDLE'),
('BOX002', 300, 45, 'IDLE'),
('BOX003', 400, 20, 'IDLE'),
('BOX004', 250, 90, 'LOADED'),
('BOX005', 500, 75, 'IDLE');

INSERT INTO item (name, weight, code, box_id) VALUES
('Medicine-Pack', 50, 'MED_001', 4),
('Emergency-Kit', 120, 'EMG_KIT', 4);