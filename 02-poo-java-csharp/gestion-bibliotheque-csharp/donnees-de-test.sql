DELETE FROM Emprunts;
DELETE FROM Clients;
DELETE FROM Livres;
DELETE FROM Auteurs;

USE "420-325-TP2"
GO
select *
from Auteurs
-- Auteurs
INSERT INTO Auteurs (Prenom, Nom) VALUES
('Jane','Austen'),
('Victor','Hugo'),
('Jules','Verne'),
('Mary','Shelley'),
('Émile','Zola');

-- Clients
INSERT INTO Clients (Prenom, Nom, DateNaissance, Courriel, Telephone) VALUES
('Alice','Martin','1998-05-12','alice.martin@example.com','514-555-0101'),
('Bruno','Tremblay','1995-09-23','bruno.tremblay@example.com','450-555-0202'),
('Chloé','Dubois','2001-02-03','chloe.dubois@example.com','438-555-0303');

-- Livres
INSERT INTO Livres (Titre, Description, DatePublie, AuteurId)
SELECT 'Orgueil et Préjugés', 'Roman de Jane Austen.', '1813-01-28', a.AuteurId
FROM Auteurs a WHERE a.Prenom='Jane' AND a.Nom='Austen';

INSERT INTO Livres (Titre, Description, DatePublie, AuteurId)
SELECT 'Les Misérables', 'Roman de Victor Hugo.', '1862-04-03', a.AuteurId
FROM Auteurs a WHERE a.Prenom='Victor' AND a.Nom='Hugo';

INSERT INTO Livres (Titre, Description, DatePublie, AuteurId)
SELECT 'Vingt mille lieues sous les mers', 'Roman de Jules Verne.', '1870-06-20', a.AuteurId
FROM Auteurs a WHERE a.Prenom='Jules' AND a.Nom='Verne';

INSERT INTO Livres (Titre, Description, DatePublie, AuteurId)
SELECT 'Frankenstein', 'Roman de Mary Shelley.', '1818-01-01', a.AuteurId
FROM Auteurs a WHERE a.Prenom='Mary' AND a.Nom='Shelley';

INSERT INTO Livres (Titre, Description, DatePublie, AuteurId)
SELECT 'Germinal', 'Roman d’Émile Zola.', '1885-04-01', a.AuteurId
FROM Auteurs a WHERE a.Prenom='Émile' AND a.Nom='Zola';

-- Emprunts
-- Emprunt ACTIF : Alice → Orgueil et Préjugés (échéance future)
INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -2, GETDATE()), DATEADD(day, 12, GETDATE()), NULL, l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Alice' AND c.Nom='Martin'
WHERE l.Titre='Orgueil et Préjugés';

-- Emprunt RETOURNÉ : Bruno → Les Misérables
INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -20, GETDATE()), DATEADD(day, -6, GETDATE()), DATEADD(day, -8, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Bruno' AND c.Nom='Tremblay'
WHERE l.Titre='Les Misérables';

-- Emprunt EN RETARD : Chloé → Vingt mille lieues sous les mers
INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -18, GETDATE()), DATEADD(day, -4, GETDATE()), NULL, l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Chloé' AND c.Nom='Dubois'
WHERE l.Titre='Vingt mille lieues sous les mers';


INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -60, GETDATE()), DATEADD(day, -46, GETDATE()), DATEADD(day, -55, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Chloé' AND c.Nom='Dubois'
WHERE l.Titre='Vingt mille lieues sous les mers';

INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -50, GETDATE()), DATEADD(day, -36, GETDATE()), DATEADD(day, -45, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Chloé' AND c.Nom='Dubois'
WHERE l.Titre='Vingt mille lieues sous les mers';

INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -40, GETDATE()), DATEADD(day, -26, GETDATE()), DATEADD(day, -35, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Chloé' AND c.Nom='Dubois'
WHERE l.Titre='Vingt mille lieues sous les mers';

INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -55, GETDATE()), DATEADD(day, -41, GETDATE()), DATEADD(day, -50, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Bruno' AND c.Nom='Tremblay'
WHERE l.Titre='Les Misérables';

INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -45, GETDATE()), DATEADD(day, -31, GETDATE()), DATEADD(day, -40, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Bruno' AND c.Nom='Tremblay'
WHERE l.Titre='Les Misérables';

INSERT INTO Emprunts (DateEmprunt, DateEcheance, DateRetour, LivreId, ClientId)
SELECT DATEADD(day, -30, GETDATE()), DATEADD(day, -16, GETDATE()), DATEADD(day, -21, GETDATE()), l.LivreId, c.ClientId
FROM Livres l
JOIN Clients c ON c.Prenom='Alice' AND c.Nom='Martin'
WHERE l.Titre='Orgueil et Préjugés';

select*
from Livres

select*
from Emprunts