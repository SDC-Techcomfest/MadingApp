using System;

namespace MadingApp.API.Service
{
    public class SchoolViewModels
    {
        public Guid SchoolId { get; set; }
        public string Name { get; set; }
        public string Address { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
    }
}
