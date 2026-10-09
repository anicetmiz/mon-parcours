using Microsoft.EntityFrameworkCore;
using TP3.Models;

namespace TP3.Services
{
    /// <summary>
    /// Service pour gérer les opérations liées aux arrêts.
    /// </summary>
    public class ArretsService
    {
        private readonly TrajetDbContext _db;

        /// <summary>
        /// Constructeur du service Arrets.
        /// </summary>
        /// <param name="db">Instance de la base de données en mémoire.</param>
        public ArretsService(TrajetDbContext db)
        {
            _db = db;
        }

        /// <summary>
        /// Récupère un arrêt par son identifiant. Si l'arrêt est trouvé, les informations du trajet associé seront présentes, si le trajet existe.
        /// </summary>
        /// <param name="id">L'identifiant de l'arrêt à rechercher.</param>
        /// <returns>L'arrêt correspondant à l'identifiant ou null si non trouvé.</returns>
        public Arret? GetArretById(int id)
        {
            if (_db.Arrets == null)
                return null;

            var monArret = _db.Arrets.Include(a => a.Trajet).FirstOrDefault(a => a.Id == id);
            if (monArret == null)
            {
                return null;
            }
            return monArret;
        }


        /// <summary>
        /// Supprime un arrêt de la base de données.
        /// </summary>
        /// <param name="id">L'identifiant de l'arrêt à supprimer.</param>
        /// <returns>True si l'arrêt a été supprimé avec succès, sinon False.</returns>
        public bool DeleteArret(int id)
        {
            var monArret = GetArretById(id);
            if (monArret != null)
            {
                _db.Arrets.Remove(monArret);
                return true;
            }
            return false;
        }
    }
}