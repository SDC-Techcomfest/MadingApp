using MadingApp.API.Models;
using System;
using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace MadingApp.API.ViewModels
{
    public class BookMarkViewModelResponse
    {
        public List<BookMarkViewModel> Bookmarks { get; set; }
    }

    public class BookMarkViewModel
    {
        public Guid BookMarkId { get; set; }
        public User User { get; set; }
        public object Mading { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }

    public class UserResponseBookMark
    {
        public Guid? UserId { get; set; }
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

    public class MadingResponseBookMark
    {
        public Guid MadingId { get; set; }
        public Guid? CateogryId { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public string MadingImage { get; set; }
        public long? Likes { get; set; }
        public long? Dislikes { get; set; }
        public Guid? ReportId { get; set; }
        public long? ReportCount { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}