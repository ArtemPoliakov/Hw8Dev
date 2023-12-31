INSERT INTO worker(name, birthday, level, salary)
VALUES
    ('Andres Antham', '2000-12-12', 'Junior', 900),
    ('Kizzie Haynes', '1990-08-09', 'Middle', 33575),
    ('Chaddy Cicci', '1971-10-14', 'Senior', 34122),
    ('Darius Sallis', '1993-11-08', 'Trainee', 50418),
    ('Ambrose Tame', '1993-02-04', 'Middle', 42917),
    ('Eldon Beauchop', '2003-03-23', 'Middle', 62970),
    ('Tab Woodland', '1975-09-11', 'Senior', 62853),
    ('Dallis Simmon', '1983-06-14', 'Middle', 73470),
    ('Alex Blain', '1965-12-11', 'Trainee', 55473),
    ('Jessey Everard', '1978-10-19', 'Trainee', 83175),
    ('Pascal Lorne', '1968-09-01', 'Middle', 32370),
    ('Borg Wallis', '1968-07-20', 'Middle', 43438),
    ('Bradley Dulanty', '1977-09-21', 'Senior', 57117),
    ('Bernie Acton', '1973-03-08', 'Junior', 39300),
    ('Krystyna Kohrt', '1971-01-17', 'Junior', 6965);

INSERT INTO client(name)
VALUES
     ('Garvey Crosgrove'),
     ('Chrissy Stannah'),
     ('Araldo Wilcinskis'),
     ('Elly Wrettum'),
     ('Brendin Patinkin');

INSERT INTO project(client_id, start_date, finish_date)
VALUES
    (1, '2025-09-09', '2030-07-16'),
    (1, '2023-11-27', '2024-03-03'),
    (3, '2024-12-21', '2031-02-25'),
    (2, '2025-07-16', '2025-08-21'),
    (3, '2025-09-12', '2026-06-27'),
    (5, '2025-05-27', '2026-12-09'),
    (2, '2026-01-22', '2027-07-06'),
    (5, '2025-01-12', '2026-08-09'),
    (4, '2024-02-24', '2026-07-23'),
    (4, '2025-08-25', '2031-08-26');

INSERT INTO project_worker (worker_id, project_id)
VALUES
    (1, 6), (1, 10), (13, 4), (2, 5), (2, 8), (2, 9), (14, 1), (3, 5), (3, 8), (4, 2),
    (4, 5), (3, 6), (5, 3), (5, 6), (5, 8), (6, 4), (7, 9), (8, 1), (8, 6),
    (8, 7), (8, 2), (9, 10), (10, 7), (10, 10), (14, 8), (15, 2), (10, 5);

