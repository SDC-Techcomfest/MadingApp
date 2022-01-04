using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class User
    {
        public Guid UserId { get; set; }
        public Guid? SchoolId { get; set; }
        public string Username { get; set; }
        [JsonIgnore]
        public string Password { get; set; }
        public string FirstName { get; set; }
        public string LastName { get; set; }
        public int? Gender { get; set; }
        public DateTime? DateOfBirth { get; set; }
        public string UserImage { get; set; }
        [JsonIgnore]
        public string Token { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}
