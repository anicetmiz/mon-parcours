using TP3.Models;

namespace TP3.Services
{
    public interface IMemoryDb
    {
        List<Arret> Arrets { get; }
        List<Trajet> Trajets { get; }
    }
}