using System;
using System.Collections.Generic;

#nullable disable

namespace MadingApp.API.Models
{
    public partial class School
    {
        public Guid SchoolId { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
        public Guid? ModifiedBy { get; set; }
        public DateTime? ModifiedDate { get; set; }
    }
}
