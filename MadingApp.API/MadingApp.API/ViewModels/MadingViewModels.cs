using Microsoft.AspNetCore.Http;
using System;

namespace MadingApp.API.ViewModels
{
    public class MadingViewModels
    {
        public Guid? CateogryId { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public IFormFile MadingImage { get; set; }
        public long? Likes { get; set; }
        public long? Dislikes { get; set; }
        public Guid? ReportId { get; set; }
        public long? ReportCount { get; set; }
    }
}
