using System.Globalization;
using System.Linq.Expressions;
using tp2.Models;
using tp2.Services;

namespace tp2
{
    internal class Program
    {
        static void Main(string[] args)
        {
            bool navigation = true;
            while (navigation) {
            Console.WriteLine( $" Système de Bibliothèque \n\n 1. Ajouter un client\n" +
                $"2. modifier un client\n3.Ajoiuter un auteur\n4.Ajouter un livre\n5.supprimer un livre" +
                $"6.Emprunter un livre\n7.Retourner un livre\n8.Afficher livres populaires" +
                $"9.afficher livres disponible\n" +
                $"10.Afficher les auteurs avec leurs llivres\n" +
                $"11.Afficher les retards\n" +
                $"0.Quitter\n" );
                Console.WriteLine($"Votre choix: ");
                string? saisieTexte = Console.ReadLine();

                if (saisieTexte == null)
                {
                    Console.WriteLine("Erreur : entrée vide.");
                    continue; 
                }

                int saisie ;
                if (!int.TryParse(saisieTexte,out saisie) )
                {
                    Console.WriteLine("Ecrire un nombre");
                    continue;
                }
                try
                {
                    switch (saisie)
                    {
                        case 1:
                            AjouterClient();
                            break;
                        case 2:
                            ModifierClient();
                            break;
                        case 3:
                            AjouterAuteur();
                            break;
                        case 4:
                            AjouterLivre();
                            break;
                        case 5:
                            SupprimerLivre();
                            break;
                        case 6:
                            EmprunterLivre();
                            break;
                        case 7:
                            RetournerLivre();
                            break;
                        case 8:
                            AfficherLivresPopulaires();
                            break;
                        case 9:
                            AfficherLivresDisponibles();
                            break;
                        case 10:
                            AfficherAuteursEtLivres();
                            break;
                        case 11:
                            AfficherRetards();
                            break;
                        case 0:
                            navigation = true;
                            break;
                        default:
                            Console.WriteLine("Choix invalide.");
                            break;
                    }
                }
                catch (Exception ex)
                {
                    Console.WriteLine($"Erreur: {ex.Message}");
                }

                Console.WriteLine("\nAppuyez sur une touche pour continuer...");
                Console.ReadKey();
            }
        }
        private static void AjouterClient()
        {
            var service = new ClientService();

            Console.Write("Prénom: ");
            string prenom = Console.ReadLine();
            Console.Write("Nom: ");
            string nom = Console.ReadLine();
            Console.Write("Date de naissance (yyyy-mm-dd): ");
            DateTime dateNaissance = DateTime.Parse(Console.ReadLine());
            Console.Write("Courriel: ");
            string courriel = Console.ReadLine();
            Console.Write("Téléphone: ");
            string telephone = Console.ReadLine();

            var client = service.Ajouter(prenom, nom, dateNaissance, courriel, telephone);
            Console.WriteLine($"Client ajouté: {client.Prenom} {client.Nom}");
        }

        private static void ModifierClient()
        {
            var service = new ClientService();

            Console.Write("Prénom: ");
            string prenom = Console.ReadLine();
            Console.Write("Nom: ");
            string nom = Console.ReadLine();
            Console.Write("Nouveau courriel: ");
            string courriel = Console.ReadLine();
            Console.Write("Nouveau téléphone: ");
            string telephone = Console.ReadLine();

            var client = service.Modifier(prenom, nom, courriel, telephone);
            Console.WriteLine($"Client modifié: {client.Prenom} {client.Nom}");
        }

        private static void AjouterAuteur()
        {
            var service = new AuteurService();

            Console.Write("Prénom auteur: ");
            string prenom = Console.ReadLine();
            Console.Write("Nom auteur: ");
            string nom = Console.ReadLine();

            var auteur = service.Ajouter(prenom, nom);
            Console.WriteLine($"Auteur ajouté: {auteur.Prenom} {auteur.Nom}");
        }

        private static void AjouterLivre()
        {
            var service = new LivreService();

            Console.Write("Titre: ");
            string titre = Console.ReadLine();
            Console.Write("Description: ");
            string description = Console.ReadLine();
            Console.Write("Date de publication (yyyy-mm-dd): ");
            DateTime date = DateTime.Parse(Console.ReadLine());
            Console.Write("Prénom auteur: ");
            string prenomAuteur = Console.ReadLine();
            Console.Write("Nom auteur: ");
            string nomAuteur = Console.ReadLine();

            var livre = service.Ajouter(titre, description, date, prenomAuteur, nomAuteur);
            Console.WriteLine($"Livre ajouté: {livre.Titre}");
        }

        private static void SupprimerLivre()
        {
            var service = new LivreService();

            Console.Write("Titre du livre à supprimer: ");
            string titre = Console.ReadLine();

            service.Supprimer(titre);
            Console.WriteLine("Livre supprimé.");
        }

        private static void EmprunterLivre()
        {
            EmpruntService service = new EmpruntService();

            Console.Write("Titre du livre: ");
            string titre = Console.ReadLine();
            Console.Write("Prénom client: ");
            string prenom = Console.ReadLine();
            Console.Write("Nom client: ");
            string nom = Console.ReadLine();

            var emprunt = service.Emprunter(titre, prenom, nom);
            Console.WriteLine($"Livre emprunté: {emprunt.Livres.Titre} par {emprunt.Client.Prenom} {emprunt.Client.Nom}");
        }

        private static void RetournerLivre()
        {
            EmpruntService service = new EmpruntService();

            Console.Write("Titre du livre: ");
            string titre = Console.ReadLine();

             service.Retourner(titre);
            Console.WriteLine($"Livre retourné");
        }

        private static void AfficherLivresPopulaires()
        {
            var service = new LivreService();

            var livres = service.Populaires();
            Console.WriteLine("Livres populaires :");
            foreach (var l in livres)
            {
                Console.WriteLine($"- {l.Titre} ({l.Emprunts.Count} emprunts)");
            }
        }

        private static void AfficherLivresDisponibles()
        {
            var service = new LivreService();

            var livres = service.Disponibles();
            Console.WriteLine("Livres disponibles :");
            foreach (var l in livres)
            {
                Console.WriteLine($"- {l.Titre}");
            }
        }

        private static void AfficherAuteursEtLivres()
        {
            var service = new AuteurService();

            var auteurs = service.GetAvecLivres();
            Console.WriteLine("Auteurs et leurs livres :");
            foreach (var a in auteurs)
            {
                Console.WriteLine($"Auteur: {a.Prenom} {a.Nom}");
                foreach (var l in a.Livres)
                {
                    Console.WriteLine($"\t- {l.Titre}");
                }
            }
        }

        private static void AfficherRetards()
        {
            var service = new EmpruntService();

            var retards = service.Retards();
            Console.WriteLine("Emprunts en retard :");
            foreach (var e in retards)
            {
                Console.WriteLine($"Livre: {e.Livres.Titre}, Client: {e.Client.Prenom} {e.Client.Nom}, Échéance: {e.DateEcheance}");
            }
        }

    }
}
