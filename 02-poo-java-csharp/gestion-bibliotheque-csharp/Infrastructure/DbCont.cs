using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tp2.Models;

namespace tp2.Infrastructure
{
    internal class DbCont : DbContext
    {
        public virtual DbSet<Auteur> Auteurs { get; set; }
        public virtual DbSet<Client> Clients { get; set; }
        public virtual DbSet<Livre> Livres { get; set; }
        public virtual DbSet<Emprunt> Emprunts { get; set; } 

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)

       => optionsBuilder.UseSqlServer("Data Source=ANICETPC\\SQLEXPRESS;Initial Catalog=420-325-TP2;Trusted_Connection=true;TrustServerCertificate=true;");

    }
}
