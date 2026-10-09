using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tp2.Models
{
    [Index(nameof(Prenom), nameof(Nom), IsUnique = true)]
    internal class Client
    {
        public int ClientId { get; set; }
        [MaxLength(60)]
        public string Prenom { get; set; }
        [MaxLength(60)]
        public string Nom { get; set; }
        public DateTime DateNaissance { get; set; }
        public string Courriel { get; set; }
        public string Telephone { get; set; }

        // navigation vers emprunt
        public List<Emprunt> Emprunts { get; set; }
    }
}
