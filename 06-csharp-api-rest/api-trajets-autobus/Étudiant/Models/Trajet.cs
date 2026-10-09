using System.ComponentModel.DataAnnotations;

namespace TP3.Models
{
    public class Trajet
    {
        public int Id { get; set; }
        public string? Nom { get; set; }
        public List<Arret>? PointsArret { get; set; } = new();
        public List<Chauffeur>? Chauffeurs { get; set; }
        public DateTime DateCreationTrajet { get; set; } = DateTime.Now;
        public DateTime DateDerniereModification { get; set; } = DateTime.Now;
    }
}
