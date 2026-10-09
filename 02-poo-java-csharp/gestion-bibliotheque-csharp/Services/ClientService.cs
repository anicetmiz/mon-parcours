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
    internal class ClientService
    {
       public Client? Ajouter (string prenom, string nom, DateTime dateNaissance, string courriel, string telephone)
        {
            using (DbCont dbContext = new DbCont())
            { 
                if(dbContext.Clients.Any(a => a.Prenom == prenom && a.Nom == nom))
                {
                    return null;
                }
                else
                {
                    Client client = new Client();
                    client.Prenom = prenom;
                    client.Nom = nom;
                    client.DateNaissance = dateNaissance;
                    client.Courriel = courriel;
                    client.Telephone = telephone;

                    dbContext.Clients.Add(client);
                    dbContext.SaveChanges();
                    return client;
                }
            }
        }
        public Client? Modifier(string prenom,string nom,string courriel,string telephone)
        {
            using (DbCont dbContext = new DbCont())
            {
                    if (dbContext.Clients.Any(a => a.Prenom == prenom && a.Nom == nom))
                {
                     return null;
                }
                else
                {
                    Client client = new Client();
                    client.Prenom = prenom;
                    client.Nom = nom;
                    client.Courriel= courriel;
                    client.Telephone = telephone;
                    dbContext.Clients.Add(client);
                    dbContext.SaveChanges();
                    return client;
                }
            }
        } 
        
    }
}
