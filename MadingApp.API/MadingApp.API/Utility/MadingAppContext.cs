using System;
using System.IdentityModel.Tokens.Jwt;
using System.Text.Json;
using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class MadingAppContext : DbContext
    {
        private class UserUsername
        { public string Username { get; set; } }

        public string getUserUsername(string token)
        {
            try
            {
                if (token != null)
                {
                    var bearer = new JwtSecurityToken(token.Replace("Bearer", "").Trim());
                    var payload = JsonSerializer.Serialize(bearer.Payload);
                    var retult = JsonSerializer.Deserialize<UserUsername>(payload);
                    return retult.Username;
                }


            }
            catch { }
            return null;
        }
        public MadingAppContext()
        {
        }

        public MadingAppContext(DbContextOptions<MadingAppContext> options)
            : base(options)
        {
        }

        public virtual DbSet<BookMark> BookMarks { get; set; }
        public virtual DbSet<Category> Categories { get; set; }
        public virtual DbSet<Comment> Comments { get; set; }
        public virtual DbSet<Mading> Madings { get; set; }
        public virtual DbSet<Report> Reports { get; set; }
        public virtual DbSet<School> Schools { get; set; }
        public virtual DbSet<User> Users { get; set; }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            if (!optionsBuilder.IsConfigured)
            {
                optionsBuilder.UseSqlServer("Server=.\\IT;Database=MadingApp;Trusted_Connection=True");
            }
        }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.HasAnnotation("Relational:Collation", "SQL_Latin1_General_CP1_CI_AS");

            modelBuilder.Entity<BookMark>(entity =>
            {
                entity.Property(e => e.BookMarkId)
                    .ValueGeneratedNever()
                    .HasColumnName("BookMarkID");

                entity.Property(e => e.MadingId).HasColumnName("MadingID");

                entity.Property(e => e.UserId).HasColumnName("UserID");
            });

            modelBuilder.Entity<Category>(entity =>
            {
                entity.Property(e => e.CategoryId)
                    .ValueGeneratedNever()
                    .HasColumnName("CategoryID");

                entity.Property(e => e.Name)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Comment>(entity =>
            {
                entity.Property(e => e.CommentId)
                    .ValueGeneratedNever()
                    .HasColumnName("CommentID");

                entity.Property(e => e.MadingId).HasColumnName("MadingID");

                entity.Property(e => e.Text).HasColumnType("text");
            });

            modelBuilder.Entity<Mading>(entity =>
            {
                entity.Property(e => e.MadingId)
                    .ValueGeneratedNever()
                    .HasColumnName("MadingID");

                entity.Property(e => e.CateogryId).HasColumnName("CateogryID");

                entity.Property(e => e.Description)
                    .HasMaxLength(200)
                    .IsUnicode(false);

                entity.Property(e => e.MadingImage)
                    .HasMaxLength(2000)
                    .IsUnicode(false);

                entity.Property(e => e.ReportId).HasColumnName("ReportID");

                entity.Property(e => e.Title)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<Report>(entity =>
            {
                entity.Property(e => e.ReportId)
                    .ValueGeneratedNever()
                    .HasColumnName("ReportID");

                entity.Property(e => e.Name)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<School>(entity =>
            {
                entity.Property(e => e.SchoolId)
                    .ValueGeneratedNever()
                    .HasColumnName("SchoolID");

                entity.Property(e => e.Address)
                    .HasMaxLength(200)
                    .IsUnicode(false);

                entity.Property(e => e.Name)
                    .HasMaxLength(50)
                    .IsUnicode(false);
            });

            modelBuilder.Entity<User>(entity =>
            {
                entity.Property(e => e.UserId)
                    .ValueGeneratedNever()
                    .HasColumnName("UserID");

                entity.Property(e => e.DateOfBirth).HasColumnType("date");

                entity.Property(e => e.FirstName)
                    .HasMaxLength(20)
                    .IsUnicode(false);

                entity.Property(e => e.LastName)
                    .HasMaxLength(50)
                    .IsUnicode(false);

                entity.Property(e => e.Password)
                    .HasMaxLength(100)
                    .IsUnicode(false);

                entity.Property(e => e.SchoolId).HasColumnName("SchoolID");

                entity.Property(e => e.UserImage)
                    .HasMaxLength(2000)
                    .IsUnicode(false);

                entity.Property(e => e.Username)
                    .HasMaxLength(10)
                    .IsUnicode(false);
            });

            OnModelCreatingPartial(modelBuilder);
        }

        partial void OnModelCreatingPartial(ModelBuilder modelBuilder);
    }
}
