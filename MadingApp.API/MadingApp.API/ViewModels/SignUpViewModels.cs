using System;
using static MadingApp.API.Enum.EnumCollection;

namespace MadingApp.API.ViewModels
{
    public class SignUpViewModels
    {
        public Guid UserId { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public EnumGender Gender { get; set; }
        public DateTime DateOfBirth { get; set; }
    }
}
