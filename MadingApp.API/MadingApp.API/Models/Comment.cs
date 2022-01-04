using System;
using System.Collections.Generic;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class Comment
    {
        public Guid CommentId { get; set; }
        public Guid? MadingId { get; set; }
        public Guid? CommentBy { get; set; }
        public string Text { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}
