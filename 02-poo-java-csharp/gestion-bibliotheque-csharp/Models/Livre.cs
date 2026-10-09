using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tp2.Models
{
    [Index(nameof(Titre), IsUnique = true)]
    internal class Livre
    {
        public int LivreId {  get; set; }

        [MaxLength(120)]
        public string Titre { get; set; }
        public string Description { get; set; }
        public DateTime DatePublie { get; set; }

        // cle etranger vers auteur
        public int AuteurId {  get; set; }

        //navigation inverse 
        public Auteur Auteur { get; set; }

        //navigation vers emprunt
        public List<Emprunt> Emprunts { get; set; }

        

    }
}
