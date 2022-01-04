using Microsoft.AspNetCore.Http;
using System;
using System.ComponentModel.DataAnnotations;
using static MadingApp.API.Enum.EnumCollection;

namespace MadingApp.API.ViewModels
{
    public class UserPutDataViewModels
    {
        public Guid UserId { get; set; }
        public string Firstname { get; set; }
        public string Lastname { get; set; }
        public IFormFile UserImage { get; set; }
    }
}
