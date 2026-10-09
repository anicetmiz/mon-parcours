using Microsoft.EntityFrameworkCore;
using TP3.Models;

namespace TP3
{
    public class TrajetDbContext : DbContext
    {
        public TrajetDbContext(DbContextOptions options) : base(options)
        {
        }

        protected TrajetDbContext()
        {
        }
        public DbSet<Trajet> Trajets { get; set; }
        public DbSet<Arret> Arrets { get; set; }
        public DbSet<Chauffeur> Chauffeurs { get; set; }
    }
}
