using Microsoft.AspNetCore.Http;
using System;
using System.Collections.Generic;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class Mading
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
