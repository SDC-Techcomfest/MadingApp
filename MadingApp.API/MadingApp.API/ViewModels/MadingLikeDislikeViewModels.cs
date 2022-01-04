using Microsoft.AspNetCore.Http;
using System;

namespace MadingApp.API.ViewModels
{
    public class MadingLikeDislikeViewModels
    {
        public Guid MadingId { get; set; }
        public long? Likes { get; set; }
        public long? Dislikes { get; set; }
    }
}
