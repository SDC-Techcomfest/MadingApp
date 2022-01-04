using System;

namespace MadingApp.API.ViewModels
{
    public class ChangePasswordViewModels
    {
        public Guid UserId { get; set; }
        public string OldPassword { get; set; }
        public string newPassword { get; set; }
    }
}
