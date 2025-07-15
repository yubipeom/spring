CREATE TABLE employees (
    user_id INT PRIMARY KEY,
    first_name NVARCHAR(50) NOT NULL,
    last_name NVARCHAR(50) NOT NULL,
    salary DECIMAL(10, 2) NOT NULL,
    currency CHAR(3) NOT NULL,
    birthdate DATETIME NOT NULL,
    is_active BIT NOT NULL,
    level TINYINT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 添加索引以提高查询性能
CREATE INDEX idx_employees_lastname ON employees(last_name);
CREATE INDEX idx_employees_active ON employees(is_active);
CREATE INDEX idx_employees_level ON employees(level);