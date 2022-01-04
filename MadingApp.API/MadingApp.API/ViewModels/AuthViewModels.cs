using System.ComponentModel.DataAnnotations;

namespace MadingApp.API.ViewModels
{
    public class AuthViewModels
    {
        [Required]
        public string Username { get; set; }
        [Required]
        public string Password { get; set; }
    }
}
