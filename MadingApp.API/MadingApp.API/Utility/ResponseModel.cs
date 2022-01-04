using System.Text.Json.Serialization;

namespace MadingApp.API.Utility
{
    public class ResponseModel
    {
        public int Code { get; set; }
        public bool Status { get; set; }

        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public string Message { get; set; }

        [JsonIgnore(Condition = JsonIgnoreCondition.WhenWritingNull)]
        public object Data { get; set; }
    }
}
