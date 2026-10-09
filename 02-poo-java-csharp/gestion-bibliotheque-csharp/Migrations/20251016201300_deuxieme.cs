using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace tp2.Migrations
{
    /// <inheritdoc />
    public partial class deuxieme : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "livreId",
                table: "Livres",
                newName: "LivreId");

            migrationBuilder.AddColumn<int>(
                name: "AuteurId",
                table: "Livres",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "ClientId",
                table: "Emprunts",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "LivreId",
                table: "Emprunts",
                type: "int",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateIndex(
                name: "IX_Livres_AuteurId",
                table: "Livres",
                column: "AuteurId");

            migrationBuilder.CreateIndex(
                name: "IX_Emprunts_ClientId",
                table: "Emprunts",
                column: "ClientId");

            migrationBuilder.CreateIndex(
                name: "IX_Emprunts_LivreId",
                table: "Emprunts",
                column: "LivreId");

            migrationBuilder.AddForeignKey(
                name: "FK_Emprunts_Clients_ClientId",
                table: "Emprunts",
                column: "ClientId",
                principalTable: "Clients",
                principalColumn: "ClientId",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Emprunts_Livres_LivreId",
                table: "Emprunts",
                column: "LivreId",
                principalTable: "Livres",
                principalColumn: "LivreId",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_Livres_Auteurs_AuteurId",
                table: "Livres",
                column: "AuteurId",
                principalTable: "Auteurs",
                principalColumn: "AuteurId",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Emprunts_Clients_ClientId",
                table: "Emprunts");

            migrationBuilder.DropForeignKey(
                name: "FK_Emprunts_Livres_LivreId",
                table: "Emprunts");

            migrationBuilder.DropForeignKey(
                name: "FK_Livres_Auteurs_AuteurId",
                table: "Livres");

            migrationBuilder.DropIndex(
                name: "IX_Livres_AuteurId",
                table: "Livres");

            migrationBuilder.DropIndex(
                name: "IX_Emprunts_ClientId",
                table: "Emprunts");

            migrationBuilder.DropIndex(
                name: "IX_Emprunts_LivreId",
                table: "Emprunts");

            migrationBuilder.DropColumn(
                name: "AuteurId",
                table: "Livres");

            migrationBuilder.DropColumn(
                name: "ClientId",
                table: "Emprunts");

            migrationBuilder.DropColumn(
                name: "LivreId",
                table: "Emprunts");

            migrationBuilder.RenameColumn(
                name: "LivreId",
                table: "Livres",
                newName: "livreId");
        }
    }
}
