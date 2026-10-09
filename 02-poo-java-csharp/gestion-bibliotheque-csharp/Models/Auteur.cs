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
    internal class Auteur
    {

        public int AuteurId { get; set; }
        [MaxLength(60)]
        public string Prenom { get; set; }
        [MaxLength(60)]
        public string Nom { get; set; }


        //propieter de navigation vers livres
        public List<Livre> Livres { get; set; }
    }
}
