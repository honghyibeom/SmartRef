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
                                                                      (30, 'trash', '#F2D4C2') ON CONFLICT (location_id) DO NOTHING;

INSERT INTO storage_type (storage_type_id, storage_type_enum) VALUES
                                                            (1, 'FRIDGE'),
                                                            (2, 'FRIDGE'),
                                                            (3, 'FRIDGE'),
                                                            (4, 'FRIDGE'),
                                                            (5, 'FRIDGE'),
                                                            (6, 'FRIDGE'),
                                                            (7, 'PANTRY'),
                                                            (8, 'PANTRY'),
                                                            (9, 'PANTRY'),
                                                            (10, 'PANTRY'),
                                                            (11, 'PANTRY'),
                                                            (12, 'PANTRY'),
                                                            (13, 'PANTRY'),
                                                            (14, 'PANTRY'),
                                                            (15, 'PANTRY'),
                                                            (16, 'PANTRY'),
                                                            (17, 'PANTRY'),
                                                            (18, 'PANTRY'),
                                                            (19, 'PANTRY'),
                                                            (20, 'TRASH')
                                                            ON CONFLICT (storage_type_id) DO NOTHING;

INSERT INTO storage_location (storage_type_id, location_id) VALUES
-- 1
(1, 1),
(1, 2),
(1, 3),
(1, 4),

-- 2
(2, 10),
(2, 11),
(2, 8),
(2, 9),

-- 3
(3, 1),

-- 4
(4, 7),
(4, 9),

-- 5
(5, 7),
(5, 8),
(5, 9),

-- 6
(6, 13),
(6, 14),

-- 7
(7, 8),

-- 8
(8, 7),
(8, 9),

-- 9
(9, 13),
(9, 14),

-- 10
(10, 7),
(10, 8),
(10, 9),

-- 11
(11, 14),
(11, 8),
(11, 13),

-- 12
(12, 15),
(12, 16),
(12, 17),
(12, 18),

-- 13
(13, 19),
(13, 20),
(13, 21),
(13, 22),

-- 14
(14, 23),
(14, 24),
(14, 25),
(14, 26),

-- 15
(15, 15),
(15, 16),
(15, 8),
(15, 17),
(15, 18),

-- 16
(16, 23),
(16, 24),
(16, 25),
(16, 26),
(16, 20),
(16, 21),

-- 17
(17, 16),
(17, 17),
(17, 23),
(17, 24),
(17, 25),
(17, 26),

-- 18
(18, 23),
(18, 24),
(18, 25),
(18, 26),
(18, 27),
(18, 28),
(18, 29),
(18, 30),

-- 19
(19, 23),
(19, 24),
(19, 25),
(19, 26),
(19, 16),
(19, 17),
(19, 20),
(19, 21),
(19, 8),

--20
(20, 30) ON CONFLICT (storage_type_id, location_id) DO NOTHING;

