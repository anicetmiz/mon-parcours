using System.Text.Json.Serialization;

namespace TP3.Models
{
    public class Arret
    {
        public int Id { get; set; }
        public double Latitude { get; set; }
        public double Longitude { get; set; }
        public bool EstActif { get; set; } = true;
        [JsonIgnore]
        public Trajet? Trajet { get; set; }

        public int TrajetId { get; set; }



    }
}