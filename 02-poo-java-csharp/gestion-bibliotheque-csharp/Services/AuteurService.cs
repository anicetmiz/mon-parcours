using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using tp2.Infrastructure;
using tp2.Models;

namespace tp2.Services
{
    internal class AuteurService
    {
        public Auteur? Ajouter(string prenom,string nom)
        {
            using (DbCont dbContexte = new DbCont())
            {
                if(dbContexte.Auteurs.Any(c => c.Nom == nom && c.Prenom == prenom))
                {
                    return null;
                }
                else
                {
                    Auteur auteur = new Auteur();
                    auteur.Nom = nom;
                    auteur.Prenom = prenom;

                    dbContexte.Auteurs.Add(auteur);
                    dbContexte.SaveChanges();
                    return auteur;
                }
            }
        }
        public List<Auteur> GetAvecLivres()
        {
            using (DbCont dbContext = new DbCont())
            {

                return dbContext.Auteurs.Include(a => a.Livres).ToList();
            }


        }
    }

}

