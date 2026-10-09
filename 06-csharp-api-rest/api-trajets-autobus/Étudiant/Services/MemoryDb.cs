using TP3.Models;

namespace TP3.Services
{
    public class MemoryDb : IMemoryDb
    {
        public List<Trajet> Trajets { get; set; } = [];
        public List<Arret> Arrets { get; set; } = [];
    }
}