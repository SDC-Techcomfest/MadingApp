using System;
using System.ComponentModel.DataAnnotations;
using static MadingApp.API.Enum.EnumCollection;

namespace MadingApp.API.ViewModels
{
    public class UserViewModels
    {
        [Key]
        public Guid Id { get; set; }

        public Guid? SchoolId { get; set; }

        public EnumRole Role { get; set; }

        public int? Status { get; set; }

        public string Username { get; set; }

        public string Password { get; set; }

        public string AccessModule { get; set; }
    }
}
