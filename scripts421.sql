---Возраст не меньше 15 лет
---Имена уникальные и не ноль
---Пара "значение названия" - "цвет факультета" д.б. уникальной
---При создании студента без возраста ему автоматически должно присваиваться 20 лет

ALTER TABLE students ADD CONSTRAINT age_more_then_15 CHECK ( age >= 15 );
ALTER TABLE students ADD CONSTRAINT name_unique UNIQUE (name);
ALTER TABLE students ALTER COLUMN name SET NOT NULL;
ALTER TABLE students ALTER COLUMN name SET DEFAULT 20;


ALTER TABLE faculties ADD CONSTRAINT name_color_uniqie UNIQUE (name, color);




