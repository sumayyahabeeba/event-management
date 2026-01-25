CREATE TABLE employees (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    email VARCHAR(100) UNIQUE NOT NULL,
    department VARCHAR(50)
);

CREATE TABLE events (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(100),
    description VARCHAR(255),
    event_date DATE,
    start_time TIME,
    end_time TIME,
    max_participants INT,
    status VARCHAR(20)
);

CREATE TABLE registrations (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT,
    event_id BIGINT,
    registered_on DATE,
    status VARCHAR(20),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE feedbacks (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    employee_id BIGINT,
    event_id BIGINT,
    rating INT,
    comments VARCHAR(255),
    FOREIGN KEY (employee_id) REFERENCES employees(id),
    FOREIGN KEY (event_id) REFERENCES events(id)
);
