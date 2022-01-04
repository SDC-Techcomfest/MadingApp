using System;

namespace MadingApp.API.Service
{
    public class CommentViewModels
    {
        public Guid CommentId { get; set; }
        public Guid? MadingId { get; set; }
        public Guid? CommentBy { get; set; }
        public string Text { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
    }    
}
