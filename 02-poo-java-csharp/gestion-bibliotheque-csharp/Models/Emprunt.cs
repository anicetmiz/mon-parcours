using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace tp2.Models
{
    internal class Emprunt
    {
        public int EmpruntId { get; set; }
        public DateTime DateEmprunt { get; set; }
        public DateTime DateEcheance { get; set; }
        public DateTime? DateRetour { get; set; }

        // cle etrangere vers client
        public int ClientId { get; set; }

        //navigation inverse
        public Client Client { get; set; }

        //  navigation vers livre
        public Livre Livres { get; set; }

        //cle etranger vers livre 
        public int LivreId { get; set; }

    }
}
