-- label
INSERT INTO label (name) VALUES
                             ('egg'),
                             ('fish'),
                             ('seafood'),
                             ('meat'),
                             ('poultry'),
                             ('processed_meat'),
                             ('dairy'),
                             ('cheese'),
                             ('yogurt'),
                             ('vegetable'),
                             ('fruit'),
                             ('legume'),
                             ('nut'),
                             ('seed'),
                             ('grain'),
                             ('rice'),
                             ('noodle'),
                             ('bread'),
                             ('oil'),
                             ('sauce'),
                             ('spice'),
                             ('raw'),
                             ('cooked'),
                             ('fermented'),
                             ('snack'),
                             ('dessert'),
                             ('beverage'),
                             ('leftover'),
                             ('other'),
                             ('fermented_product') ON CONFLICT (name) DO NOTHING;


-- location
INSERT INTO location (location_id, location_name, location_color) VALUES
                                                                      (1, 'fridges', '#F2A7A7'),
                                                                      (2, 'freezers', '#F2B8A0'),
                                                                      (3, 'freshs', '#F2C6A0'),
                                                                      (4, 'doors', '#F2D5A0'),
                                                                      (5, 'doorl', '#F2E4A0'),
                                                                      (6, 'doorr', '#E6F2A0'),
                                                                      (7, 'top', '#D4F2A0'),
                                                                      (8, 'middle', '#C2F2A0'),
                                                                      (9, 'down', '#B0F2A0'),
                                                                      (10, 'fridgel', '#A0F2A7'),
                                                                      (11, 'fridger', '#A0F2B8'),
                                                                      (13, 'right', '#A0F2C9'),
                                                                      (14, 'left', '#A0F2DA'),
                                                                      (15, 'most_top', '#A0F2EB'),
                                                                      (16, 'center_top', '#A0E8F2'),
                                                                      (17, 'center_bottom', '#A0D6F2'),
                                                                      (18, 'most_bottom', '#A0C4F2'),
                                                                      (19, 'most_left', '#A0B2F2'),
                                                                      (20, 'center_left', '#A7A0F2'),
                                                                      (21, 'center_right', '#B8A0F2'),
                                                                      (22, 'most_right', '#C9A0F2'),
                                                                      (23, 'most_top_left', '#DAA0F2'),
                                                                      (24, 'most_top_right', '#EBA0F2'),
                                                                      (25, 'most_bottom_left', '#F2A0E4'),
                                                                      (26, 'most_bottom_right', '#F2A0D2'),
                                                                      (27, 'center_top_right', '#F2A0C0'),
                                                                      (28, 'center_bottom_right', '#F2A0AE'),
                                                                      (29, 'center_top_left', '#F2C2B8'),
                                                                      (30, 'center_bottom_left', '#F2D4C2') ON CONFLICT (location_id) DO NOTHING;

INSERT INTO storage_location (storage_type_id, storage_type, location_id) VALUES
-- 1
(1, 'FRIDGE', 1),
(1, 'FRIDGE', 2),
(1, 'FRIDGE', 3),
(1, 'FRIDGE', 4),

-- 2
(2, 'FRIDGE', 10),
(2, 'FRIDGE', 11),
(2, 'FRIDGE', 8),
(2, 'FRIDGE', 9),

-- 3
(3, 'FRIDGE', 1),

-- 4
(4, 'FRIDGE', 7),
(4, 'FRIDGE', 9),

-- 5
(5, 'FRIDGE', 7),
(5, 'FRIDGE', 8),
(5, 'FRIDGE', 9),

-- 6
(6, 'FRIDGE', 13),
(6, 'FRIDGE', 14),

-- 7
(7, 'PANTRY', 8),

-- 8
(8, 'PANTRY', 7),
(8, 'PANTRY', 9),

-- 9
(9, 'PANTRY', 13),
(9, 'PANTRY', 14),

-- 10
(10, 'PANTRY', 7),
(10, 'PANTRY', 8),
(10, 'PANTRY', 9),

-- 11
(11, 'PANTRY', 14),
(11, 'PANTRY', 8),
(11, 'PANTRY', 13),

-- 12
(12, 'PANTRY', 15),
(12, 'PANTRY', 16),
(12, 'PANTRY', 17),
(12, 'PANTRY', 18),

-- 13
(13, 'PANTRY', 19),
(13, 'PANTRY', 20),
(13, 'PANTRY', 21),
(13, 'PANTRY', 22),

-- 14
(14, 'PANTRY', 23),
(14, 'PANTRY', 24),
(14, 'PANTRY', 25),
(14, 'PANTRY', 26),

-- 15
(15, 'PANTRY', 15),
(15, 'PANTRY', 16),
(15, 'PANTRY', 8),
(15, 'PANTRY', 17),
(15, 'PANTRY', 18),

-- 16
(16, 'PANTRY', 23),
(16, 'PANTRY', 24),
(16, 'PANTRY', 25),
(16, 'PANTRY', 26),
(16, 'PANTRY', 20),
(16, 'PANTRY', 21),

-- 17
(17, 'PANTRY', 16),
(17, 'PANTRY', 17),
(17, 'PANTRY', 23),
(17, 'PANTRY', 24),
(17, 'PANTRY', 25),
(17, 'PANTRY', 26),

-- 18
(18, 'PANTRY', 23),
(18, 'PANTRY', 24),
(18, 'PANTRY', 25),
(18, 'PANTRY', 26),
(18, 'PANTRY', 27),
(18, 'PANTRY', 28),
(18, 'PANTRY', 29),
(18, 'PANTRY', 30),

-- 19
(19, 'PANTRY', 23),
(19, 'PANTRY', 24),
(19, 'PANTRY', 25),
(19, 'PANTRY', 26),
(19, 'PANTRY', 16),
(19, 'PANTRY', 17),
(19, 'PANTRY', 20),
(19, 'PANTRY', 21),
(19, 'PANTRY', 8)
    ON CONFLICT (storage_type_id, storage_type, location_id) DO NOTHING;