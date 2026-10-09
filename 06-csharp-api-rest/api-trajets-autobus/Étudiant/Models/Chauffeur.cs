using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;
using Microsoft.EntityFrameworkCore;

namespace TP3.Models
{

    [Index(nameof(NoLicence), IsUnique = true)]
    public class Chauffeur
    {
        [Key]
        public int Id { get; set; }
        [MaxLength(50)]
        public string Nom { get; set; }
        [MaxLength(50)]
        public string Prenom { get; set; }
        [MaxLength(100)]
        public string? Courriel { get; set; }
        public float SalaireHoraire { get; set; }
        public DateTime DateNaissance { get; set; }

        [MaxLength(450)]
        public string NoLicence { get; set; }
        public DateTime DateEmbauche { get; set; }
        public DateTime? DateFinEmploi { get; set; }

        public int TrajetId { get; set; }
        [JsonIgnore]

        public Trajet Trajet { get; set; }
    }
}
