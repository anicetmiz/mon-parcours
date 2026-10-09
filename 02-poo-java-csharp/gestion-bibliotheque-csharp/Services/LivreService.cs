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
    internal class LivreService
    {
        public Livre Ajouter(string titre,string description,DateTime datePublication,string prenom,string nom)
        {
            using(DbCont bdContexte = new DbCont())
            {
                if(bdContexte.Livres.Any(a => a.Titre == titre && a.Description == description))
                {
                    return null;
                }
                else
                {
                    Livre livre = new Livre();
                    livre.Titre = titre;
                    livre.Description = description;
                    livre.DatePublie = datePublication;
                    livre.Auteur.Prenom = prenom;
                    livre.Auteur.Nom = nom;
                    bdContexte.Livres.Add(livre);
                    bdContexte.SaveChanges();
                    return livre;
                }
            }
        }
        public void Supprimer(string titre)
        {
            using (DbCont bdContexte = new DbCont())
            {
                if (!bdContexte.Livres.Any(a => a.Titre == titre))
                {
                    return;
                }
                else
                {
                    Livre unLivre = new Livre();
                    unLivre = bdContexte.Livres.Include(e => e.Emprunts).First(a => a.Titre == titre);
                    bdContexte.Emprunts.RemoveRange(unLivre.Emprunts);
                    bdContexte.Livres.Remove(unLivre);
                }
            }
        }
        public List<Livre> Populaires()
        {
            using (DbCont bdContexte = new DbCont())
            {
                List<Livre> mesLivres = new List<Livre>();

                mesLivres = bdContexte.Livres
                                    .Include(e => e.Emprunts)
                                    .OrderByDescending(a => a.Emprunts.Count)
                                    .Take(3)
                                    .ToList();


            return mesLivres;

            }
        }

        public List<Livre> Disponibles()
        {
            using(DbCont bdContexte = new DbCont() )
            {
                return bdContexte.Livres.Include(a=>a.Emprunts).Where(a=>!a.Emprunts.Any(e=>e.DateRetour==null)).ToList();
            }
        }
    }
}
