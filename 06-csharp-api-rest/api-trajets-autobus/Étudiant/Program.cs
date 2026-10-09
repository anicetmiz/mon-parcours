using Microsoft.EntityFrameworkCore;
using System.Text.Json.Serialization;
using TP3;
using TP3.Models;
using TP3.Services;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<TrajetDbContext>(options =>
    options.UseSqlServer(
        builder.Configuration
            .GetConnectionString("DefaultConnection")
    )
);

// Register the new services
builder.Services.AddScoped<TrajetsService>();
builder.Services.AddScoped<ArretsService>();

// Add services to the container. Required for MinimalAPIs
builder.Services.AddEndpointsApiExplorer();

// Permet d'éviter des boucles infinies lors de la réception du JSON.
builder.Services.ConfigureHttpJsonOptions(options =>
    options.SerializerOptions.ReferenceHandler = ReferenceHandler.IgnoreCycles);

var app = builder.Build();

// Configure the HTTP request pipeline.
if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}

app.UseHttpsRedirection();

//get all trajets
app.MapGet("/trajets", (TrajetsService service) =>
{
    return Results.Ok(service.GetAllTrajets());
});

//trajets avec / ss bools
app.MapGet("/trajets/{idTrajet:int}", (int idTrajet, bool? includeArrets, TrajetsService service) =>
{
    var unTrajet = service.GetTrajetById(idTrajet, includeArrets);

    if (unTrajet != null)
    {
        return Results.Ok(unTrajet);
    }
    else
    {
        return Results.NotFound();
    }
});

//creer trajets avec prop nom;
app.MapPost("/trajets", (Trajet trajet, TrajetsService service) =>
{
    if (string.IsNullOrWhiteSpace(trajet.Nom))
    {
        return Results.BadRequest();
    }

    var nouveauTrajet = service.CreateTrajet(trajet);
    return Results.Created($"/trajets/{nouveauTrajet.Id}", nouveauTrajet);

});

app.MapPost("/trajets/{idTrajet:int}/arrets", (int idTrajet, List<Arret> arrets, TrajetsService service) =>
{
    var unTrajet = service.AddArretsToTrajet(idTrajet, arrets);

    if (unTrajet != null)
    {
        return Results.Ok(unTrajet);
    }
    else
    {
        return Results.NotFound("Aucun trajet trouvé");
    }
});

//Retourne les arrêt d'un trajet selon le ID
app.MapGet("/trajets/{idTrajet:int}/arrets", (int idTrajet, TrajetsService service) =>
{
    return Results.Ok(service.GetArretsForTrajet(idTrajet));
});


//delete un trajet
app.MapDelete("/trajets/{idTrajet:int}", (int idTrajet, TrajetsService service) =>
{
    bool unArret = service.DeleteTrajet(idTrajet);

    if (unArret == true)
    {
        return Results.NoContent();
    }
    else
    {
        return Results.NotFound();
    }
});

//get un arrêt
app.MapGet("/arrets/{idArret:int}", (int idArret, ArretsService arret) =>
{
    var unArret = arret.GetArretById(idArret);
    if (unArret != null)
    {
        return Results.Ok(unArret);
    }
    else
    {
        return Results.NotFound();
    }

});

//delete un arrêt
app.MapDelete("/arrets/{idArret:int}", (int idArret, ArretsService arret) =>
{
    bool unArret = arret.DeleteArret(idArret);
    if (unArret == true)
    {
        return Results.NoContent();
    }
    else
    {
        return Results.NotFound();
    }

});
app.MapDelete("/trajets", (TrajetDbContext context) =>
{
    context.Database.ExecuteSqlRaw("IF OBJECT_ID('Arrets') IS NOT NULL TRUNCATE TABLE Arrets");
    context.Database.ExecuteSqlRaw("IF OBJECT_ID('Chauffeurs') IS NOT NULL TRUNCATE TABLE Chauffeurs");
    context.Database.ExecuteSqlRaw("DELETE FROM Trajets; DBCC CHECKIDENT ('[Trajets]', RESEED, 0); ");
    context.SaveChanges();
});

using (var scope = app.Services.CreateScope())
{
    var dbContext = scope.ServiceProvider.GetRequiredService<TrajetDbContext>();

}

app.Run();
