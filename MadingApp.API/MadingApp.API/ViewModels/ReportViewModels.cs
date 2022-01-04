using System;

namespace MadingApp.API.Service
{
    public class ReportViewModels
    {
        public Guid ReportId { get; set; }
        public string Name { get; set; }
        public Guid? CreatedBy { get; set; }
        public DateTime? CreatedDate { get; set; }
    }
}
