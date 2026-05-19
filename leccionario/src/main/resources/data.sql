-- Datos iniciales de ejemplo
INSERT INTO cursos (nombre) VALUES ('Matemáticas');
INSERT INTO cursos (nombre) VALUES ('Historia');


INSERT INTO leccionarios (asignatura, fecha, contenido, id_profesor, id_curso) VALUES
('Álgebra básica', '2026-05-18', 'Repaso de ecuaciones lineales', 10, 1),
('Revolución Francesa', '2026-05-17', 'Causas y consecuencias', 12, 2);
