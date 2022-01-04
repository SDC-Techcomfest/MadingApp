using System;
using System.Collections.Generic;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class BookMark
    {
        public Guid BookMarkId { get; set; }
        public Guid? UserId { get; set; }
        public Guid? MadingId { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}
