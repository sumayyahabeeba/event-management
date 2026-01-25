-- Employees
INSERT INTO employees (name, email, department) VALUES
('Ayaan Khan', 'ayaan@office.com', 'IT'),
('Sara Ahmed', 'sara@office.com', 'HR'),
('Rahul Verma', 'rahul@office.com', 'Finance');

-- Events
INSERT INTO events (title, description, event_date, start_time, end_time, max_participants, status) VALUES
('Spring Boot Workshop', 'Intro to Spring Boot', '2026-02-10', '10:00:00', '12:00:00', 50, 'PLANNED'),
('Team Meet', 'Monthly sync-up', '2026-02-05', '15:00:00', '16:00:00', 20, 'PLANNED');

-- Registrations
INSERT INTO registrations (employee_id, event_id, registered_on, status) VALUES
(1, 1, '2026-02-01', 'ATTENDED'),
(2, 1, '2026-02-01', 'ATTENDED'),
(3, 2, '2026-02-02', 'REGISTERED');

-- Feedback
INSERT INTO feedbacks (employee_id, event_id, rating, comments) VALUES
(1, 1, 5, 'Very informative session'),
(2, 1, 4, 'Good explanations'),
(3, 2, 4, 'Well organized meeting');