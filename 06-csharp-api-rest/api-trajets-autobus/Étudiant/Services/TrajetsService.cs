using Microsoft.EntityFrameworkCore;
using TP3.Models;

namespace TP3.Services
{
    /// <summary>
    /// Service gérant les opérations liées aux trajets et aux arrêts.
    /// </summary>
    public class TrajetsService
    {
        private readonly TrajetDbContext _db;

        /// <summary>
        /// Constructeur du service Trajets.
        /// </summary>
        /// <param name="db">Instance de la base de données en mémoire.</param>
        public TrajetsService(TrajetDbContext db)
        {
            _db = db;
        }

        /// <summary>
        /// Récupère tous les trajets.
        /// </summary>
        /// <returns>Liste de tous les trajets.</returns>
        public List<Trajet> GetAllTrajets() => _db.Trajets
            .Include(a => a.Chauffeurs)
            .Include(a => a.PointsArret)
            .ToList();

        /// <summary>
        /// Récupère un trajet par son identifiant.
        /// </summary>
        /// <param name="idTrajet">Identifiant du trajet.</param>
        /// <param name="includeArrets">Indique si les arrêts doivent être inclus dans l'objet Trajet ou non.</param>
        /// <returns>Le trajet correspondant ou null si non trouvé.</returns>
        public Trajet? GetTrajetById(int idTrajet, bool? includeArrets)
        {
            var unTrajet = _db.Trajets.Include(a => a.Chauffeurs).FirstOrDefault(t => t.Id == idTrajet);

            if (unTrajet == null)
            {
                return null;
            }

            if (includeArrets == true)
            {
                unTrajet.PointsArret = _db.Arrets.Where(a => a.TrajetId == idTrajet).ToList();
            }
            //else if (unTrajet.PointsArret == null)
            //{
            //    unTrajet.PointsArret = new List<Arret>();
            //}
            return unTrajet;

        }

        /// <summary>
        /// Crée un nouveau trajet avec comme valeur d'identifiant, le plus haut nombre existant des trajets +1
        /// </summary>
        /// <param name="trajet">Le trajet à créer.</param>
        /// <returns>Le trajet créé avec son nouvel identifiant.</returns>
        public Trajet CreateTrajet(Trajet trajet)
        {

            _db.Trajets.Add(trajet);
            _db.SaveChanges();

            return trajet;
        }

        /// <summary>
        /// Ajoute des arrêts à un trajet existant. Si un arrêt est en double dans la liste, il est ignoré.
        /// </summary>
        /// <param name="id">Identifiant du trajet.</param>
        /// <param name="arrets">Liste des arrêts à ajouter. Chaque arrêt est créé avec comme valeur d'identifiant, le plus haut nombre existant des arrêts +1 </param>
        /// <returns>Le trajet mis à jour ou null si le trajet n'existe pas.</returns>
        public Trajet? AddArretsToTrajet(int id, List<Arret> arrets)

        {
            Trajet? monTrajet = GetTrajetById(id, true);
            if (monTrajet == null)
            {
                return null;
            }
            if (monTrajet.PointsArret == null)
            {
                monTrajet.PointsArret = new List<Arret>();
            }

            var arretsDistinct = arrets.Distinct(new ArretEqualityComparer()).ToList();



            foreach (var arret in arretsDistinct)
            {

                arret.TrajetId = monTrajet.Id;
                arret.Trajet = monTrajet;

                monTrajet.PointsArret.Add(arret);
                _db.Arrets.Add(arret);
            }
            monTrajet.DateDerniereModification = DateTime.Now;

            _db.SaveChanges();

            return monTrajet;
        }


        /// <summary>
        /// Récupère tous les arrêts d'un trajet spécifique.
        /// </summary>
        /// <param name="idTrajet">Identifiant du trajet.</param>
        /// <returns>Liste des arrêts du trajet.</returns>
        public List<Arret> GetArretsForTrajet(int idTrajet)
        {
            if (_db.Trajets == null)
            {
                return new List<Arret>();
            }
            var monTrajet = _db.Trajets.Include(a => a.PointsArret).FirstOrDefault(t => t.Id == idTrajet);

            if (monTrajet == null)
            {
                return new List<Arret>();
            }

            if (monTrajet.PointsArret == null)
            {
                return monTrajet.PointsArret = new List<Arret>();
            }


            return monTrajet.PointsArret;

        }

        /// <summary>
        /// Supprime un trajet et tous ses arrêts associés.
        /// </summary>
        /// <param name="id">Identifiant du trajet à supprimer.</param>
        /// <returns>True si le trajet a été supprimé, False sinon.</returns>
        public bool DeleteTrajet(int id)
        {
            var monTrajet = GetTrajetById(id, null);

            if (monTrajet == null)
            {
                return false;
            }

            if (_db.Arrets != null)
            {
                var lesArrets = _db.Arrets.Where(a => a.TrajetId == id).ToList();
                foreach (var arret in lesArrets)
                {
                    _db.Arrets.Remove(arret);
                }
            }

            _db.Trajets.Remove(monTrajet);
            return true;
        }
    }
}