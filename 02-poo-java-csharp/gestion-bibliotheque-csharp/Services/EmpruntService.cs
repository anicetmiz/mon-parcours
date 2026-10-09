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
    internal class EmpruntService
    {
       public Emprunt? Emprunter(string titre,string prenom,string nom)
        {
            using(DbCont bdContexte = new DbCont())
            {
                Livre unLivre = new Livre();
                Client unClient = new Client();
                Emprunt unEmprunt = new Emprunt();
                if (bdContexte.Livres.Any(a => a.Titre == titre) && bdContexte.Clients.Any(a => a.Prenom == prenom && a.Nom == nom))
                {
                    unLivre=bdContexte.Livres.First(a=>a.Titre == titre);
                    unClient = bdContexte.Clients.First(a => a.Prenom == prenom && a.Nom == nom);
                    if (bdContexte.Emprunts.Any(a => a.LivreId == unLivre.LivreId && a.DateRetour == null))
                    {
                        return null;
                    }
                    else
                    {
                        unEmprunt.DateEmprunt = DateTime.Now;
                        unEmprunt.DateEcheance = DateTime.Now.AddDays(14);
                        unEmprunt.ClientId = unClient.ClientId;
                        unEmprunt.LivreId = unLivre.LivreId;
                       
                    }
                    bdContexte.Emprunts.Add(unEmprunt);
                    bdContexte.SaveChanges();
                    return unEmprunt;
                }
                else
                {
                    return null ;
                }
            }
        }

        public void Retourner(string titre)
        {
            using (DbCont bdContexte = new DbCont())
            {
                Livre unLivre = new Livre();
                unLivre =  bdContexte.Livres.First(a=>a.Titre==titre);
                if (unLivre == null)
                {
                    // Le livre n'existe pas → on arrête
                    return;
                }
                Emprunt e = bdContexte.Emprunts.First(a => a.LivreId == unLivre.LivreId && a.DateRetour == null);
                if (e == null)
                {
                    throw new Exception("Aucun emprunt actif trouvé pour ce livre.");
                }
                 e.DateRetour = DateTime.Now;
                bdContexte.SaveChanges();
                
            }
           
        }
        public List<Emprunt> Retards()
        {
            using (DbCont dbContext = new DbCont())
            {
                return dbContext.Emprunts
                    .Include(e => e.Client)
                    .Include(e => e.Livres)
                    .Where(e => e.DateRetour == null && e.DateEcheance < DateTime.Now)
                    .ToList();
            }
        }
    }
}
