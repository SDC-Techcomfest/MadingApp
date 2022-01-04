using System;

namespace MadingApp.API.Service
{
    public class CategoryViewModels
    {
        public Guid CategoryId { get; set; }
        public string Name { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
    }
}
