/* =============================================================================
   REQUÊTES SQL AVANCÉES — SQL Server (T-SQL)
   Auteur : Anicet Mizumbi — Cégep de Granby, Hiver 2025
   Requêtes écrites sans outil d'IA, pendant mes cours de bases de données.

   SOMMAIRE (du plus simple au plus difficile)
   --------------------------------------------
     1. Fonctions prédéfinies ........ texte, nombres, conversions
     2. Fonctions d'agrégation ........ COUNT, SUM, AVG, MIN, MAX, GROUP BY, HAVING
     3. Création et modification ...... ALTER TABLE, contraintes (FK, CHECK, DEFAULT)
     4. Manipulation des données ...... INSERT, UPDATE, DELETE, transactions
     5. Sous-requêtes et ensembles .... sous-requêtes, ALL, ANY, UNION, auto-jointures
     6. Vues ......................... CREATE VIEW
     7. Normalisation ................ 1FN, 2FN, 3FN à partir d'un fichier CSV
     8. Déclencheurs (triggers) ...... historique automatique des modifications

   BASES DE DONNÉES UTILISÉES
   --------------------------
     EMPLOYE, GestionVOLS, BD_CLIENTS_FOURNISSEURS : bases fournies en classe.
     EVEMTL : créée dans la section 7 à partir du fichier donnees\EVEMTL.csv.

   AVANT D'EXÉCUTER LA SECTION 7 (fichier CSV)
   -------------------------------------------
     Le fichier EVEMTL.csv est fourni dans le dossier « donnees » de ce dépôt.
     1. Crée le dossier C:\SQL-BULKINSERT sur ton ordinateur.
     2. Copie-y donnees\EVEMTL.csv  ->  C:\SQL-BULKINSERT\EVEMTL.csv
     Tu peux aussi le laisser ailleurs : change alors le chemin dans la
     commande BULK INSERT (requête 7.0).
     Les sections qui touchent les tables CLIENTS, CLASSE, ENTRAVES et PEINTRE
     utilisent d'autres bases vues en classe.

   ASTUCE : dans SSMS, sélectionne une requête puis appuie sur F5 pour
   l'exécuter seule.
============================================================================= */



/* =============================================================================
   1. FONCTIONS PRÉDÉFINIES
   -----------------------------------------------------------------------------
   Transformer une valeur dans un SELECT : arrondir un nombre, changer la casse,
   remplacer ou découper du texte, assembler plusieurs valeurs.
   Fonctions : ROUND, CONVERT, CAST, FLOOR, CEILING, LOWER, UPPER, REPLACE,
               CONCAT, LEFT, SUBSTRING, LEN
============================================================================= */
USE GestionVOLS
GO

-- 1.1 Arrondir une moyenne de cinq façons différentes
SELECT MIN(PRIX_PAYE) AS MINPrix
	,MAX(PRIX_PAYE) AS MAXPrix
	,SUM(PRIX_PAYE) AS SUMPrix
	,AVG(PRIX_PAYE) AS AVGPrix
	,ROUND(AVG(PRIX_PAYE), 2) AS AVGPrixRound              -- arrondi à 2 décimales
	,CONVERT(NUMERIC(5, 2), AVG(PRIX_PAYE)) AS AVGPrixNumerique
	,CONVERT(INT, AVG(PRIX_PAYE)) AS AVGINT                -- coupe les décimales
	,FLOOR(AVG(PRIX_PAYE)) AS AVGPrixFloor                 -- arrondi vers le bas (plancher)
	,CEILING(AVG(PRIX_PAYE)) AS AVGPrixCeiling             -- arrondi vers le haut (plafond)
	,COUNT(*) AS NBPrix
FROM RESERVATION

-- 1.2 REPLACE : afficher « BRUXELLES » au lieu du code « BRU »
--     Nombre de vols directs (sans escale) vers Bruxelles
SELECT REPLACE(DESTIN, 'BRU', 'BRUXELLES') AS DESTINATION
	,COUNT(*) AS 'NOMBRE VOL'
FROM VOL
WHERE DESTIN = 'BRU'
	AND ESCALE IS NULL
GROUP BY DESTIN

-- 1.3 CONCAT + UPPER + LOWER : assembler un texte « nombre-AM-origine »
SELECT CONCAT (
		COUNT(*)
		,'-'
		,UPPER('am')
		,'-'
		,LOWER(ORIGINE)
		) AS ORIGINE
FROM VOL
GROUP BY ORIGINE

-- 1.4 Même chose, en excluant Boston et en ajoutant un calcul
SELECT CONCAT (
		COUNT(*)
		,'-'
		,UPPER('am')
		,'-'
		,LOWER(ORIGINE)
		,'-'
		,COUNT(*) * 3
		) AS ORIGINE
FROM VOL
WHERE ORIGINE != 'BOS'
GROUP BY ORIGINE

-- 1.5 LEFT + SUBSTRING + CAST : insérer la lettre « E » après les 2 premiers
--     caractères de la référence. On ne garde que les références qui ont
--     un nombre impair de voyageurs (% = modulo)
SELECT LEFT(CAST(REF AS VARCHAR), 2) + 'E' + SUBSTRING(CAST(REF AS VARCHAR), 3, LEN(CAST(REF AS VARCHAR)) - 2) AS 'REF'
	,COUNT(*) AS 'VOYAGEURS'
FROM PASSAGER
GROUP BY REF
HAVING (COUNT(*) % 2) != 0

-- 1.6 Afficher une vitesse moyenne avec son unité : « 912 Km/h »
SELECT MARQUE
	,CONCAT (
		CONVERT(INT, ROUND(AVG(VIT_CROI), 0))
		,' '
		,'Km/h'
		) AS Vitesse_Moyenne
FROM AVION
WHERE MARQUE != 'BOEING'
GROUP BY MARQUE
HAVING CONVERT(INT, ROUND(AVG(VIT_CROI), 0)) > 900



/* =============================================================================
   2. FONCTIONS D'AGRÉGATION — GROUP BY / HAVING
   -----------------------------------------------------------------------------
   Calculer un résultat pour un GROUPE de lignes (par vol, par marque…).
   - GROUP BY : forme les groupes.
   - WHERE    : filtre les lignes AVANT le regroupement.
   - HAVING   : filtre les groupes APRÈS le regroupement (on peut y utiliser
                COUNT, AVG…, ce qui est impossible dans un WHERE).
============================================================================= */
USE GestionVOLS
GO

-- 2.1 Nombre de passagers par mode de paiement
SELECT LOWER(MODE_PAIE) AS 'MODE DE PAIEMENT'
	,COUNT(*) AS 'PASSAGERS AYANT CE MODE DE PAIEMENT'
FROM RESERVATION
GROUP BY MODE_PAIE

-- 2.2 Prix minimum et maximum payés
SELECT MIN(PRIX_PAYE) AS 'PRIX MIN'
	,MAX(PRIX_PAYE) AS 'PRIX MAX'
FROM RESERVATION

-- 2.3 Nombre de réservations par vol
SELECT ID_VOL
	,COUNT(*) AS 'NOMBRE RES'
FROM RESERVATION
GROUP BY ID_VOL

-- 2.4 WHERE + HAVING ensemble : vols payés autrement qu'en argent comptant
--     ou par chèque, qui ont exactement 1 ou 4 réservations
SELECT ID_VOL
	,COUNT(*) AS NOMBRE_RES
FROM RESERVATION
WHERE MODE_PAIE NOT IN ('COMPTANT', 'CHEQUE')
GROUP BY ID_VOL
HAVING COUNT(*) IN (1, 4) -- condition sur une agrégation : toujours APRÈS le GROUP BY

-- 2.5 Minimum, maximum et total, convertis en entiers
SELECT CONVERT(INT, MIN(PRIX_PAYE)) AS 'MIN PRIX_PAYE'
	,CONVERT(INT, MAX(PRIX_PAYE)) AS 'MAX PRIX_PAYE'
	,CEILING(SUM(PRIX_PAYE)) AS 'TOTAL PRIX_PAYE'
FROM RESERVATION

-- 2.6 Montants minimum, maximum et moyen par vol, triés par montant minimum
SELECT ID_VOL
	,MIN(PRIX_PAYE) AS 'MONTANT MIN'
	,MAX(PRIX_PAYE) AS 'MONTANT MAX'
	,CONVERT(INT, ROUND(AVG(PRIX_PAYE), 0)) AS 'PRIX MOYEN'
FROM RESERVATION
GROUP BY ID_VOL
ORDER BY [MONTANT MIN] ASC

-- 2.7 Nombre de vols par ville d'origine
SELECT ORIGINE
	,COUNT(*) AS 'NB VOLS'
FROM VOL
GROUP BY ORIGINE
ORDER BY ORIGINE

-- 2.8 Vitesse de croisière moyenne par marque d'avion
SELECT MARQUE
	,CONVERT(INT, ROUND(AVG(VIT_CROI), 0)) AS Vitesse_Moyenne
FROM AVION
GROUP BY MARQUE

-- 2.9 GROUP BY sur une expression CASE : nombre d'employés par décennie
--     d'embauche (le même CASE doit être répété dans le GROUP BY)
USE EMPLOYE
GO

SELECT CASE
		WHEN YEAR(DATENT) BETWEEN 1990 AND 1999
			THEN '1990 - 2000'
		WHEN YEAR(DATENT) BETWEEN 2000 AND 2009
			THEN '2000 - 2010'
		ELSE 'DATE INCONNUE'
		END AS TRANCHE
	,COUNT(*) AS NBEMP
FROM EMP
GROUP BY CASE
		WHEN YEAR(DATENT) BETWEEN 1990 AND 1999
			THEN '1990 - 2000'
		WHEN YEAR(DATENT) BETWEEN 2000 AND 2009
			THEN '2000 - 2010'
		ELSE 'DATE INCONNUE'
		END



/* =============================================================================
   3. CRÉATION ET MODIFICATION DE LA STRUCTURE — ALTER TABLE ET CONTRAINTES
   -----------------------------------------------------------------------------
   Modifier une table existante et protéger les données avec des contraintes :
   - FOREIGN KEY : la valeur doit exister dans une autre table (ou la même).
   - CHECK       : la valeur doit respecter une condition.
   - DEFAULT     : valeur mise automatiquement si on n'en donne pas.
============================================================================= */
USE EMPLOYE
GO

-- 3.1 Changer le type de données d'une colonne
--     (à faire AVANT d'ajouter la clé étrangère sur cette colonne)
ALTER TABLE EMP
ALTER COLUMN NO_SUP INT

-- 3.2 Clé étrangère sur la même table : le supérieur (NO_SUP) d'un employé
--     doit lui-même être un employé
ALTER TABLE EMP
ADD CONSTRAINT FK_EMP_NO_SUP FOREIGN KEY (NO_SUP) REFERENCES EMP(ID_NUM)

-- 3.3 Contrainte CHECK : la commission doit être entre 0 et 1500
ALTER TABLE EMP
ADD CONSTRAINT CHK_COMM CHECK (COMM BETWEEN 0 AND 1500)

-- 3.4 Contrainte DEFAULT : date d'entrée = aujourd'hui si elle n'est pas fournie
ALTER TABLE EMP
ADD CONSTRAINT DF_DATENT DEFAULT GETDATE() FOR DATENT

-- 3.5 Test de la contrainte CHECK : cette requête DOIT échouer (5000 > 1500)
UPDATE EMP
SET COMM = 5000
WHERE ID_NUM = 1309

-- 3.6 Modifier une valeur de clé (le numéro de service 1 devient 100)
UPDATE SERV
SET NO_SER = 100
WHERE NO_SER = 1



/* =============================================================================
   4. MANIPULATION DES DONNÉES — INSERT, UPDATE, DELETE ET TRANSACTIONS
   -----------------------------------------------------------------------------
   Ajouter, modifier et supprimer des lignes.
   BEGIN TRANSACTION ... ROLLBACK permet de TESTER une modification puis de
   l'annuler : rien n'est enregistré pour de bon.
============================================================================= */
USE GestionVOLS
GO

-- 4.1 INSERT : ajouter un passager
INSERT INTO PASSAGER (ID_PASS, NOM, PRENOM, CODE, NO_CIVIQUE, RUE, LOCALITE, REF, ID_PAYS, NOPASSPT)
VALUES (159, 'Durand', 'Paul', 'DURPA45', 10, 'Rue des Fleurs', 'MONTRÉAL', 200, 'B', 987654321);

-- 4.2 UPDATE avec DATEADD : décaler d'un jour les vols des Boeing 727 et 737
UPDATE VOL
SET DATE_ARR = DATEADD(DAY, 1, DATE_ARR)
	,DATE_DEP = DATEADD(DAY, 1, DATE_DEP)
WHERE ID_AVION IN ('727', '737')

-- 4.3 UPDATE avec CASE : déplacer l'article du nom de rue à la fin
--     ex. « DE LA PAIX » devient « PAIX(DE LA) »
--     L'ordre des WHEN compte : « DE LA » et « DES » doivent être testés
--     avant « DE », sinon ils seraient traités comme « DE ».
UPDATE PASSAGER
SET RUE = CASE
		WHEN RUE LIKE 'DE LA%'
			THEN SUBSTRING(RUE, 6, LEN(RUE)) + '(DE LA)'
		WHEN RUE LIKE 'DES%'
			THEN SUBSTRING(RUE, 4, LEN(RUE)) + '(DES)'
		WHEN RUE LIKE 'DU%'
			THEN SUBSTRING(RUE, 3, LEN(RUE)) + '(DU)'
		WHEN RUE LIKE 'DE%'
			THEN SUBSTRING(RUE, 3, LEN(RUE)) + '(DE)'
		ELSE RUE
		END
WHERE RUE LIKE 'DE%'
	OR RUE LIKE 'DES%'
	OR RUE LIKE 'DU%'
	OR RUE LIKE 'DE LA%'

-- 4.4 Plusieurs modifications testées dans une transaction, puis annulées
BEGIN TRANSACTION

-- INSERT de plusieurs lignes à la fois
INSERT INTO CLIENTS (ID, NOM, VILLE, COURRIEL)
VALUES (1, 'ANICET', 'PORTO', 'LUISE@GMAIL.COM')
	,(2, 'LUI', 'POR', 'LUI@GMAIL.COM')
	,(3, 'SE', 'MONT', 'SE@GMAIL.COM')

-- DELETE avec deux conditions
DELETE
FROM PASSAGER
WHERE NOM = 'BARIL'
	AND PRENOM = 'MARCEL'

-- UPDATE avancé : mettre une majuscule au début de chaque mot
--   « INFORMATIQUE »      -> « Informatique »
--   « SCIENCES HUMAINES » -> « Sciences Humaines »
UPDATE CLASSE
SET CATEGORIE = CASE
		WHEN CHARINDEX(' ', CATEGORIE) = 0
			-- Cas sans espace : un seul mot
			THEN CONCAT (
					LEFT(CATEGORIE, 1)
					,LOWER(SUBSTRING(CATEGORIE, 2, CASE
								WHEN LEN(CATEGORIE) > 1
									THEN LEN(CATEGORIE) - 1
								ELSE 0
								END))
					)
		WHEN CHARINDEX(' ', CATEGORIE) > 0
			-- Cas avec espace : deux mots
			THEN CONCAT (
					UPPER(LEFT(CATEGORIE, 1))
					,LOWER(SUBSTRING(CATEGORIE, 2, CASE
								WHEN CHARINDEX(' ', CATEGORIE) > 2
									THEN CHARINDEX(' ', CATEGORIE) - 2
								ELSE 1
								END))
					,' '
					,UPPER(SUBSTRING(CATEGORIE, CHARINDEX(' ', CATEGORIE) + 1, 1))
					,LOWER(SUBSTRING(CATEGORIE, CHARINDEX(' ', CATEGORIE) + 2, CASE
								WHEN LEN(CATEGORIE) > CHARINDEX(' ', CATEGORIE) + 2
									THEN LEN(CATEGORIE) - (CHARINDEX(' ', CATEGORIE) + 1)
								ELSE 0
								END))
					)
		END

ROLLBACK -- annule tout ce qui a été fait depuis BEGIN TRANSACTION

-- 4.5 Générer un identifiant unique au moment de l'insertion
--     Format : OCT-<année><mois>AM<jour><6 chiffres aléatoires>
INSERT INTO ENTRAVES (
	ID_PERMIS
	,NOM_ENTRAVE
	,DATE_CHARGEMENT
	)
VALUES (
	CONCAT (
		'OCT-'
		,FORMAT(GETDATE(), 'yy')
		,FORMAT(GETDATE(), 'MM')
		,'AM'
		,FORMAT(GETDATE(), 'dd')
		,FORMAT(CONVERT(INT, RAND() * 999999 + 1), '000000')
		)
	,'Intre#2'
	,GETDATE()
	)



/* =============================================================================
   5. SOUS-REQUÊTES, OPÉRATEURS ENSEMBLISTES ET AUTO-JOINTURES
   -----------------------------------------------------------------------------
   - Sous-requête : une requête à l'intérieur d'une autre (souvent dans le WHERE).
   - ALL : la condition doit être vraie pour TOUTES les valeurs de la sous-requête.
   - ANY : la condition doit être vraie pour AU MOINS UNE valeur.
   - UNION : additionne les lignes de deux requêtes (sans les doublons).
   - Auto-jointure : joindre une table avec elle-même (alias A et B).
============================================================================= */
USE EMPLOYE
GO

-- 5.1 Afficher chaque employé avec la moyenne de tous les salaires
--     (OVER() calcule la moyenne sur toute la table sans regrouper les lignes)
SELECT *
	,AVG(SALAIRE) OVER () AS MOYEN
FROM EMP

-- 5.2 Employés qui gagnent moins que la moyenne
SELECT *
FROM EMP
WHERE SALAIRE < (
		SELECT AVG(SALAIRE)
		FROM EMP
		)
ORDER BY SALAIRE

USE GestionVOLS
GO

-- 5.3 Passagers qui ont le même prénom que le passager 330
--     Version 1 : auto-jointure
SELECT PASSAGER.NOM
	,PASSAGER.ID_PASS
	,PASSAGER.NOPASSPT
FROM PASSAGER
JOIN PASSAGER AS P ON PASSAGER.PRENOM = P.PRENOM
WHERE P.ID_PASS = 330

--     Version 2 : sous-requête avec IN (passagers 330 et 334, en les excluant)
SELECT NOM
	,ID_PASS
	,NOPASSPT
FROM PASSAGER
WHERE PRENOM IN (
		SELECT PRENOM
		FROM PASSAGER
		WHERE ID_PASS IN (330, 334)
		)
	AND ID_PASS NOT IN (330, 334)
ORDER BY ID_PASS DESC

-- 5.4 ANY : avions qui ont plus de places qu'au moins un Airbus ou Douglas
--     (IS NOT NULL évite qu'une valeur vide fausse la comparaison)
SELECT *
FROM AVION
WHERE NB_PASS > ANY (
		SELECT NB_PASS
		FROM AVION
		WHERE MARQUE IN ('AIRBUS', 'DOUGLAS')
			AND NB_PASS IS NOT NULL
		)
ORDER BY ID_AVION

--     Même résultat avec une auto-jointure
SELECT DISTINCT A.MARQUE
	,A.MODELE
	,A.NB_PASS AS 'NOMBRE DE PASSAGER'
FROM AVION A
JOIN AVION B ON A.NB_PASS > B.NB_PASS
WHERE B.MARQUE IN ('AIRBUS', 'DOUGLAS')
ORDER BY A.MARQUE

-- 5.5 ALL : avions qui ont moins de places que le 747 ET que l'AB4
SELECT *
FROM AVION
WHERE NB_PASS < ALL (
		SELECT NB_PASS
		FROM AVION
		WHERE ID_AVION IN ('747', 'AB4')
		)

-- 5.6 Avions qui ont au moins autant de places que la moyenne des Douglas
SELECT ID_AVION
	,MODELE
	,MARQUE
	,NB_PASS
FROM AVION
WHERE NB_PASS >= (
		SELECT AVG(NB_PASS)
		FROM AVION
		WHERE MARQUE = 'DOUGLAS'
		)

-- 5.7 Deux sous-requêtes : au-dessus de la moyenne des Douglas ET des Airbus
SELECT ID_AVION
	,MODELE
	,MARQUE
	,NB_PASS
FROM AVION
WHERE NB_PASS >= (
		SELECT AVG(NB_PASS)
		FROM AVION
		WHERE MARQUE = 'DOUGLAS'
		)
	AND NB_PASS >= (
		SELECT AVG(NB_PASS)
		FROM AVION
		WHERE MARQUE = 'AIRBUS'
		)
ORDER BY NB_PASS

-- 5.8 Avions plus lents que la vitesse moyenne
SELECT MODELE
	,MARQUE
	,VIT_CROI AS VITESSE
FROM AVION
WHERE VIT_CROI < (
		SELECT AVG(VIT_CROI)
		FROM AVION
		)

-- 5.9 Jointures + sous-requête : vols faits par des avions américains dont
--     l'autonomie est inférieure à celle du meilleur avion français
SELECT A.ID_VOL
	,B.ID_AVION
	,B.MARQUE
	,C.NOMPAYS
	,B.AUTONOME
FROM VOL A
JOIN AVION B ON A.ID_AVION = B.ID_AVION
JOIN PAYS C ON B.ID_PAYS = C.ID_PAYS
WHERE C.NOMPAYS = 'ETATS-UNIS'
	AND B.AUTONOME < (
		SELECT MAX(AUTONOME)
		FROM AVION A
		JOIN PAYS B ON A.ID_PAYS = B.ID_PAYS
		WHERE B.NOMPAYS = 'FRANCE'
		)

-- 5.10 4 tables jointes : passagers qui ont voyagé dans un avion dont la
--      vitesse dépasse de plus de 10 % la vitesse moyenne des avions en service
SELECT A.ID_PASS
	,A.NOM
	,A.PRENOM
	,C.ID_VOL
FROM PASSAGER A
JOIN RESERVATION B ON A.ID_PASS = B.ID_PASS
JOIN VOL C ON B.ID_VOL = C.ID_VOL
JOIN AVION D ON C.ID_AVION = D.ID_AVION
WHERE D.VIT_CROI > (
		SELECT AVG(VIT_CROI) + (AVG(VIT_CROI) * 10 / 100) AS 'AVG_VIT_CROI'
		FROM AVION A
		JOIN VOL B ON A.ID_AVION = B.ID_AVION
		)
ORDER BY A.ID_PASS ASC

-- 5.11 UNION de deux GROUP BY
--      ⚠ À VÉRIFIER : la 1re partie compte les ID_VOL dans la table VOL, où
--      chaque vol n'apparaît qu'une fois ; son COUNT vaut donc toujours 1 et le
--      HAVING >= 3 ne garde jamais rien. Seule la 2e partie donne des résultats.
SELECT ID_VOL
	,COUNT(ID_VOL) AS NOMBRE_DE_RESERVATION
FROM VOL
WHERE ESCALE IS NOT NULL
GROUP BY ID_VOL
HAVING COUNT(ID_VOL) >= 3

UNION

SELECT ID_VOL
	,COUNT(ID_VOL) AS NOMBRE_DE_RESERVATION
FROM RESERVATION
GROUP BY ID_VOL
HAVING COUNT(ID_VOL) >= 3

USE BD_CLIENTS_FOURNISSEURS
GO

-- 5.12 UNION : une seule liste de contacts avec les clients (C) et les
--      fournisseurs (F)
SELECT CONCAT (
		'C'
		,ID_CLIENT
		) AS ID
	,NOM
	,CONTACT
	,ADRESSE
	,VILLE
	,CODEPOSTAL
	,PAYS
FROM CLIENT

UNION

SELECT CONCAT (
		'F'
		,ID_FOURNISSEUR
		) AS ID
	,NOM
	,CONTACT
	,ADRESSE
	,VILLE
	,CODEPOSTAL
	,PAYS
FROM FOURNISSEUR
ORDER BY CONTACT

-- 5.13 UNION de deux regroupements : nombre de clients et de fournisseurs
--      par pays
SELECT 'CLIENT' AS 'TYPE'
	,COUNT(PAYS) AS NB_CONTACT
	,PAYS
FROM CLIENT
GROUP BY PAYS

UNION

SELECT 'FOURNISSEUR' AS 'TYPE'
	,COUNT(PAYS) AS NB_CONTACT
	,PAYS
FROM FOURNISSEUR
GROUP BY PAYS
ORDER BY NB_CONTACT DESC

-- 5.14 (base des entraves de Montréal)
--      Auto-jointure : entraves qui commencent le même jour qu'une entrave donnée
SELECT A.*
FROM ENTRAVES A
JOIN ENTRAVES B ON A.DATE_DEBUT = B.DATE_DEBUT
WHERE B.ID = '6787d9500dbaf200136258ff'

-- 5.15 Arrondissements qui ont moins d'entraves que Verdun, puis Verdun mis
--      en évidence avec des étoiles grâce à UNION
SELECT ID_ARRONDISSEMENT
	,COUNT(*) AS NOMBRE_ENTRAVES
FROM ENTRAVES
GROUP BY ID_ARRONDISSEMENT
HAVING COUNT(*) < (
		SELECT COUNT(*)
		FROM ENTRAVES
		WHERE ID_ARRONDISSEMENT = 'VERDUN'
		)

UNION

SELECT UPPER(CONCAT ('****', ID_ARRONDISSEMENT, '****'))
	,COUNT(*)
FROM ENTRAVES
WHERE ID_ARRONDISSEMENT = 'VERDUN'
GROUP BY ID_ARRONDISSEMENT
ORDER BY NOMBRE_ENTRAVES DESC
GO



/* =============================================================================
   6. VUES
   -----------------------------------------------------------------------------
   Une vue est une requête enregistrée sous un nom. On l'interroge ensuite
   comme une table, sans réécrire les jointures.
============================================================================= */

-- 6.1 Vue : tableaux vendus de certains styles, avec leur peintre et leur prix
CREATE OR ALTER VIEW ExempleVUE3
AS
SELECT PEINTRE.NOM_PEINT AS PEINTRE
	,TABLEAU.TITRE AS TABLEAU
	,PRIX_VENTE
FROM PEINTRE
JOIN TABLEAU ON PEINTRE.CODEPEINTRE = TABLEAU.CODEPEINTRE
JOIN TRANSAC ON TABLEAU.CODETABLEAU = TRANSAC.CODETABLEAU
WHERE STYLE IN ('RENAISSANCE', 'CUBISME', 'SYMBOLISME', 'IMPRESSIONNISME')
GO

-- 6.2 Utiliser la vue : afficher seulement les toiles de Toulouse-Lautrec
SELECT PEINTRE
	,TABLEAU
	,PRIX_VENTE
FROM ExempleVUE3
WHERE PEINTRE LIKE '%TOULOUSE%'



/* =============================================================================
   7. NORMALISATION — 1FN, 2FN, 3FN
   -----------------------------------------------------------------------------
   Point de départ : un fichier CSV d'événements montréalais, où tout est dans
   une seule table (événement, lieu, organisateur et participants mélangés).
   On le découpe étape par étape en tables propres, sans répétition :
   - 1FN : valeurs atomiques (une seule information par colonne) + clé primaire.
   - 2FN : aucune colonne ne dépend d'une PARTIE seulement de la clé composée.
   - 3FN : aucune colonne ne dépend d'une autre colonne qui n'est pas la clé.

   Résultat final :
     EVENTS3FN ── ID_LIEU ─────────► LIEU3FN
         │    └─ ID_ORGANISATEUR ──► ORGANISATEURS3FN
         └── ID ◄── EVEMTL2FN ──► PARTICIPANT2FN   (table de liaison)
============================================================================= */

-- 7.0 Créer la base et importer le fichier CSV avec BULK INSERT
--     FICHIER REQUIS : donnees\EVEMTL.csv (fourni dans ce dépôt)
--     Copie-le dans C:\SQL-BULKINSERT\ avant d'exécuter cette section,
--     ou remplace le chemin après FROM par l'endroit où se trouve ton fichier.
--     Le chemin est lu par le SERVEUR SQL : il doit exister sur l'ordinateur
--     où SQL Server est installé.
USE master
GO

DROP DATABASE IF EXISTS EVEMTL
GO

CREATE DATABASE EVEMTL
GO

ALTER AUTHORIZATION ON DATABASE::EVEMTL TO sa
GO

USE EVEMTL
GO

DROP TABLE IF EXISTS EVEMTL

CREATE TABLE EVEMTL (
	NOM VARCHAR(40)
	,DTEVENT DATE
	,HEUREDEBUT TIME
	,HEUREFIN TIME
	,LIEU VARCHAR(40)
	,DESCRIPTION VARCHAR(50)
	,COUT SMALLMONEY
	,NOM_ORGANISATEUR VARCHAR(40)
	,TEL_ORGANISATEUR CHAR(10)
	,COURRIEL_ORGANISATEUR VARCHAR(40)
	,NBPARTICIPANTS TINYINT
	,NOM_PARTICIPANT VARCHAR(40)
	,TEL_PARTICIPANT CHAR(10)
	)

BULK INSERT EVEMTL
FROM 'C:\SQL-BULKINSERT\EVEMTL.csv' -- <- modifie ce chemin si ton fichier est ailleurs
WITH (
		FIELDTERMINATOR = ';'
		,FIRSTROW = 2 -- la 1re ligne contient les titres des colonnes
		,CODEPAGE = 'ACP' -- encodage Windows habituel ('65001' pour UTF-8)
		)

SELECT *
FROM EVEMTL

/* ---------- 1FN : rendre les colonnes atomiques et ajouter une clé ---------- */

-- 7.1 NOM_PARTICIPANT contient « prénom nom » : on le sépare en 2 colonnes
DROP TABLE IF EXISTS EVEMTL1FN

SELECT NOM
	,DTEVENT
	,HEUREDEBUT
	,HEUREFIN
	,LIEU
	,DESCRIPTION
	,COUT
	,NOM_ORGANISATEUR
	,TEL_ORGANISATEUR
	,COURRIEL_ORGANISATEUR
	,NBPARTICIPANTS
	,TRIM(LEFT(NOM_PARTICIPANT, CHARINDEX(' ', NOM_PARTICIPANT))) AS PRENOM_PARTICIPANT
	,TRIM(RIGHT(NOM_PARTICIPANT, LEN(NOM_PARTICIPANT) - CHARINDEX(' ', NOM_PARTICIPANT))) AS NOM_PARTICIPANT
	,TEL_PARTICIPANT
INTO EVEMTL1FN
FROM EVEMTL

-- 7.2 Ajouter un identifiant pour l'événement et pour le participant
ALTER TABLE EVEMTL1FN
ADD ID TINYINT

ALTER TABLE EVEMTL1FN
ADD ID_PARTICIPANT TINYINT

UPDATE EVEMTL1FN
SET ID = 1
WHERE NOM = 'Francos de Montréal'

UPDATE EVEMTL1FN
SET ID = 2
WHERE NOM = 'Festival YATAI MTL'

UPDATE EVEMTL1FN
SET ID_PARTICIPANT = 1
WHERE PRENOM_PARTICIPANT = 'EMMA'
	AND NOM_PARTICIPANT = 'WILLIAM'

UPDATE EVEMTL1FN
SET ID_PARTICIPANT = 2
WHERE PRENOM_PARTICIPANT = 'THOMAS'
	AND NOM_PARTICIPANT = 'COTE'

UPDATE EVEMTL1FN
SET ID_PARTICIPANT = 3
WHERE PRENOM_PARTICIPANT = 'LÉO'
	AND NOM_PARTICIPANT = 'FORTIN'

UPDATE EVEMTL1FN
SET ID_PARTICIPANT = 4
WHERE PRENOM_PARTICIPANT = 'ROMY'
	AND NOM_PARTICIPANT = 'BOUCHARD'

UPDATE EVEMTL1FN
SET ID_PARTICIPANT = 5
WHERE PRENOM_PARTICIPANT = 'CHARLOTTE'
	AND NOM_PARTICIPANT = 'CHAN'

-- 7.3 Clé primaire composée (événement + participant)
--     Une colonne de clé primaire ne peut pas accepter NULL
ALTER TABLE EVEMTL1FN
ALTER COLUMN ID TINYINT NOT NULL

ALTER TABLE EVEMTL1FN
ALTER COLUMN ID_PARTICIPANT TINYINT NOT NULL

ALTER TABLE EVEMTL1FN
ADD CONSTRAINT PK_EVEMTL1FN PRIMARY KEY (ID, ID_PARTICIPANT)

EXEC SP_HELP EVEMTL1FN -- affiche la structure de la table

SELECT *
FROM EVEMTL1FN

/* ---------- 2FN : séparer ce qui dépend d'une partie de la clé ---------- */
-- Les infos de l'événement ne dépendent que de ID, celles du participant que
-- de ID_PARTICIPANT : on les sépare en deux tables, reliées par une table de
-- liaison.

-- 7.4 Table des événements
DROP TABLE IF EXISTS EVENTS2FN

SELECT DISTINCT ID
	,NOM
	,DTEVENT
	,HEUREDEBUT
	,HEUREFIN
	,LIEU
	,DESCRIPTION
	,COUT
	,NOM_ORGANISATEUR
	,TEL_ORGANISATEUR
	,COURRIEL_ORGANISATEUR
	,NBPARTICIPANTS
INTO EVENTS2FN
FROM EVEMTL1FN

-- 7.5 Table des participants
DROP TABLE IF EXISTS PARTICIPANT2FN

SELECT DISTINCT ID_PARTICIPANT
	,PRENOM_PARTICIPANT
	,NOM_PARTICIPANT
	,TEL_PARTICIPANT
INTO PARTICIPANT2FN
FROM EVEMTL1FN

-- 7.6 Table de liaison : quel participant va à quel événement
DROP TABLE IF EXISTS EVEMTL2FN

SELECT ID
	,ID_PARTICIPANT
INTO EVEMTL2FN
FROM EVEMTL1FN

-- 7.7 Clés primaires des 3 tables
ALTER TABLE EVEMTL2FN
ALTER COLUMN ID TINYINT NOT NULL

ALTER TABLE EVEMTL2FN
ALTER COLUMN ID_PARTICIPANT TINYINT NOT NULL

ALTER TABLE EVEMTL2FN
ADD CONSTRAINT PK_EVEMTL2FN PRIMARY KEY (ID, ID_PARTICIPANT)

ALTER TABLE EVENTS2FN
ALTER COLUMN ID TINYINT NOT NULL

ALTER TABLE EVENTS2FN
ADD CONSTRAINT PK_EVENTS2FN PRIMARY KEY (ID)

ALTER TABLE PARTICIPANT2FN
ALTER COLUMN ID_PARTICIPANT TINYINT NOT NULL

ALTER TABLE PARTICIPANT2FN
ADD CONSTRAINT PK_PARTICIPANT2FN PRIMARY KEY (ID_PARTICIPANT)

-- 7.8 Clés étrangères pour relier la table de liaison aux deux autres
ALTER TABLE EVEMTL2FN
ADD CONSTRAINT FK_EVEMTL2FN_ID FOREIGN KEY (ID) REFERENCES EVENTS2FN(ID)

ALTER TABLE EVEMTL2FN
ADD CONSTRAINT FK_EVEMTL2FN_ID_PARTICIPANT FOREIGN KEY (ID_PARTICIPANT) REFERENCES PARTICIPANT2FN(ID_PARTICIPANT)

SELECT *
FROM EVEMTL2FN

SELECT *
FROM PARTICIPANT2FN

SELECT *
FROM EVENTS2FN

/* ---------- 3FN : séparer ce qui dépend d'une autre colonne ---------- */
-- Le téléphone et le courriel dépendent de l'organisateur, pas de l'événement :
-- on crée une table pour les lieux et une pour les organisateurs.

-- 7.9 Table des lieux (IDENTITY génère l'identifiant automatiquement)
DROP TABLE IF EXISTS LIEU3FN

SELECT DISTINCT LIEU
INTO LIEU3FN
FROM EVENTS2FN

ALTER TABLE LIEU3FN
ADD ID TINYINT IDENTITY(1, 1)

ALTER TABLE LIEU3FN
ALTER COLUMN ID TINYINT NOT NULL

ALTER TABLE LIEU3FN
ADD CONSTRAINT PK_LIEU PRIMARY KEY (ID)

SELECT *
FROM LIEU3FN

-- 7.10 Table des organisateurs (même méthode que pour les lieux)
DROP TABLE IF EXISTS ORGANISATEURS3FN

SELECT DISTINCT NOM_ORGANISATEUR
	,TEL_ORGANISATEUR
	,COURRIEL_ORGANISATEUR
INTO ORGANISATEURS3FN
FROM EVENTS2FN

ALTER TABLE ORGANISATEURS3FN
ADD ID_ORGANISATEUR TINYINT IDENTITY(1, 1)

ALTER TABLE ORGANISATEURS3FN
ALTER COLUMN ID_ORGANISATEUR TINYINT NOT NULL

ALTER TABLE ORGANISATEURS3FN
ADD CONSTRAINT PK_ORGANISATEURS PRIMARY KEY (ID_ORGANISATEUR)

SELECT *
FROM ORGANISATEURS3FN

-- 7.11 Table des événements en 3FN : on remplace le lieu et l'organisateur
--      par leur identifiant
DROP TABLE IF EXISTS EVENTS3FN

SELECT DISTINCT *
INTO EVENTS3FN
FROM EVENTS2FN

ALTER TABLE EVENTS3FN
ADD ID_LIEU TINYINT

-- UPDATE avec jointure : on récupère l'ID du lieu à partir de son nom
UPDATE A
SET A.ID_LIEU = B.ID
FROM EVENTS3FN A
JOIN LIEU3FN B ON A.LIEU = B.LIEU

ALTER TABLE EVENTS3FN
DROP COLUMN LIEU

ALTER TABLE EVENTS3FN
ADD ID_ORGANISATEUR TINYINT

UPDATE A
SET A.ID_ORGANISATEUR = B.ID_ORGANISATEUR
FROM EVENTS3FN A
JOIN ORGANISATEURS3FN B ON A.NOM_ORGANISATEUR = B.NOM_ORGANISATEUR

ALTER TABLE EVENTS3FN
DROP COLUMN NOM_ORGANISATEUR
	,TEL_ORGANISATEUR
	,COURRIEL_ORGANISATEUR

-- 7.12 Clés étrangères vers les lieux et les organisateurs
ALTER TABLE EVENTS3FN
ALTER COLUMN ID_LIEU TINYINT NOT NULL

ALTER TABLE EVENTS3FN
ADD CONSTRAINT FK_EVENTS3FN_ID_LIEU FOREIGN KEY (ID_LIEU) REFERENCES LIEU3FN(ID)

ALTER TABLE EVENTS3FN
ADD CONSTRAINT FK_EVENTS3FN_ID_ORGANISATEUR FOREIGN KEY (ID_ORGANISATEUR) REFERENCES ORGANISATEURS3FN(ID_ORGANISATEUR)

SELECT *
FROM EVENTS3FN

SELECT *
FROM EVEMTL2FN

-- 7.13 NBPARTICIPANTS est une donnée CALCULÉE (on peut la compter) : on la
--      retire de la table et on la calcule dans une vue
ALTER TABLE EVENTS3FN
DROP COLUMN NBPARTICIPANTS
GO

CREATE OR ALTER VIEW EVENTS3FN_V
AS
SELECT A.ID
	,A.NOM
	,A.DTEVENT
	,A.HEUREDEBUT
	,A.HEUREFIN
	,A.DESCRIPTION
	,A.COUT
	,A.ID_LIEU
	,A.ID_ORGANISATEUR
	,COUNT(B.ID_PARTICIPANT) AS NOMBREPARTICIPANT
FROM EVENTS3FN A
LEFT JOIN EVEMTL2FN B ON A.ID = B.ID -- LEFT JOIN : garde aussi les événements sans participant
GROUP BY A.ID
	,A.NOM
	,A.DTEVENT
	,A.HEUREDEBUT
	,A.HEUREFIN
	,A.DESCRIPTION
	,A.COUT
	,A.ID_LIEU
	,A.ID_ORGANISATEUR
GO



/* =============================================================================
   8. DÉCLENCHEURS (TRIGGERS)
   -----------------------------------------------------------------------------
   Un déclencheur s'exécute TOUT SEUL après un INSERT, un UPDATE ou un DELETE.
   Ici, on garde un historique : chaque fois qu'un employé est modifié ou
   supprimé, son ancienne version est copiée dans la table HIST_EMP.
   Dans un déclencheur, deux tables spéciales sont disponibles :
   - deleted  : les lignes AVANT la modification (ou les lignes supprimées).
   - inserted : les lignes APRÈS la modification (ou les lignes ajoutées).
   Un UPDATE remplit les deux tables ; un DELETE remplit seulement deleted.
============================================================================= */
USE EMPLOYE
GO

-- 8.1 Créer la table d'historique avec la même structure que EMP, plus la
--     date de l'action, le type de modification et un identifiant.
--     WHERE 1=2 est toujours faux : on copie la STRUCTURE, sans aucune ligne.
DROP TABLE IF EXISTS HIST_EMP
GO

SELECT *
	,GETDATE() AS DTE_ACTION
	,CONVERT(CHAR(1), '') AS TYPE_MODIF
	,IDENTITY(INT) AS ID
INTO HIST_EMP
FROM EMP
WHERE 1 = 2
GO

/* ---------- Version 1 : un seul déclencheur pour UPDATE et DELETE ---------- */

-- 8.2 Si deleted ET inserted contiennent des lignes, c'est un UPDATE (U) ;
--     si seule deleted en contient, c'est un DELETE (D)
CREATE OR ALTER TRIGGER TRIG_EMP_UI ON EMP -- I = INSERT / U = UPDATE / D = DELETE
AFTER UPDATE, DELETE
AS
SET NOCOUNT ON

IF EXISTS (
		SELECT 0
		FROM deleted
		)
BEGIN
	IF EXISTS (
			SELECT 0
			FROM inserted
			)
	BEGIN
		INSERT INTO HIST_EMP
		SELECT *
			,GETDATE()
			,UPPER('U')
		FROM deleted
	END
	ELSE
	BEGIN
		INSERT INTO HIST_EMP
		SELECT *
			,GETDATE()
			,UPPER('D')
		FROM deleted
	END
END
GO

-- 8.3 Tester le déclencheur, puis tout annuler
BEGIN TRANSACTION

DELETE EMP
WHERE ID_NUM = 1904

SELECT *
FROM HIST_EMP

ROLLBACK

BEGIN TRANSACTION

UPDATE EMP
SET COMM = 500
WHERE ID_NUM = 1708

SELECT *
FROM HIST_EMP

ROLLBACK

/* ---------- Version 2 : un déclencheur par opération ---------- */
-- Autre façon de faire la même chose. On supprime d'abord la version 1,
-- sinon chaque modification serait enregistrée deux fois dans l'historique.
DROP TRIGGER IF EXISTS TRIG_EMP_UI
GO

-- 8.4 Déclencheur pour UPDATE
--     Les deux SELECT affichent le contenu de inserted et deleted, pour
--     voir ce que chaque table contient pendant un UPDATE
CREATE OR ALTER TRIGGER TRIG_EMP_U ON EMP
AFTER UPDATE
AS
BEGIN
	INSERT INTO HIST_EMP
	SELECT *
		,GETDATE()
		,UPPER('U')
	FROM deleted

	SELECT 'INSERTED' AS SOURCE
		,*
	FROM inserted

	SELECT 'DELETED' AS SOURCE
		,*
	FROM deleted
END
GO

-- 8.5 Déclencheur pour DELETE (inserted sera vide)
CREATE OR ALTER TRIGGER TRIG_EMP_D ON EMP
AFTER DELETE
AS
BEGIN
	INSERT INTO HIST_EMP
	SELECT *
		,GETDATE()
		,UPPER('D')
	FROM deleted

	SELECT 'INSERTED' AS SOURCE
		,*
	FROM inserted

	SELECT 'DELETED' AS SOURCE
		,*
	FROM deleted
END
GO

-- 8.6 Tester la version 2, puis tout annuler
BEGIN TRANSACTION

UPDATE EMP
SET SALAIRE = 1750
WHERE ID_NUM = 1409

DELETE EMP
WHERE ID_NUM = 1409

SELECT *
FROM HIST_EMP

ROLLBACK
