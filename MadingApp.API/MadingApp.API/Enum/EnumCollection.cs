using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace MadingApp.API.Enum
{
    public class EnumCollection
    {
        [JsonConverter(typeof(JsonStringEnumConverter))]
        public enum EnumGender
        {
            Male = 1,
            Female = 2,
        }

        public enum EnumRole
        {
            [Display(Name = "Super Administration")]
            SuperAdministration = 1,

            [Display(Name = "Administration")]
            Administration = 2,

            [Display(Name = "Teacher")]
            Teacher = 3,

            [Display(Name = "Student")]
            Student = 4,

            [Display(Name = "Parent")]
            Parent = 5,
        }
    }
}