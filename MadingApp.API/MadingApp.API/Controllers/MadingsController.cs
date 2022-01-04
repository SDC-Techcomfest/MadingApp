using MadingApp.API.Models;
using MadingApp.API.Service;
using MadingApp.API.Utility;
using MadingApp.API.ViewModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.IO;
using System.Threading.Tasks;

namespace MadingApp.API.Controllers
{
    [Produces("application/json")]
    [ApiVersion("1.0")]
    [Route("api/v{version:apiVersion}/madings")]
    [ApiController]
    public class MadingsController : ControllerBase
    {
        private MadingAppContext db;
        private MadingService madingService;
        private IWebHostEnvironment webHostEnvironment;

        public MadingsController(MadingAppContext db, IConfiguration iconf, IHttpContextAccessor contextAccessor, IWebHostEnvironment webHostEnvironment)
        {
            this.db = db;
            this.webHostEnvironment = webHostEnvironment;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            madingService = new MadingService(db, email, iconf, contextAccessor, webHostEnvironment);
        }

        [HttpGet]
        public async Task<ActionResult> getMadings()
        {
            var result = await madingService.getMadings();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            return Ok(result);
        }

        [HttpGet]
        [Route("my-madings")]
        public async Task<ActionResult> getMyMadings()
        {
            var result = await madingService.getMyMadings();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            return Ok(result);
        }

        [HttpGet]
        [Route("hot-topics")]
        public async Task<ActionResult> getMadingsHotTopics()
        {
            var result = await madingService.getMadingsHotTopics();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            return Ok(result);
        }

       // [Authorize]
        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult> postMading([FromForm] MadingViewModels request)
        {
            try
            {
                var result = await madingService.postMading(request);
                if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
                return Created("localhost", result);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        //[HttpPost]
        //[Route("upload-image/{id}")]
        //public async Task<ActionResult> postMadingImage(IFormFile request, Guid id)
        //{
        //    try
        //    {
        //        var result = await madingService.postMadingImage(request,id);
        //        if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
        //        return Created("Upload Image", result.Data);
        //    }
        //    catch (Exception e)
        //    {
        //        return StatusCode(500, e.Message);
        //    }
        //}

        [Authorize]
        [HttpGet]
        [Route("{id}")]
        public async Task<ActionResult> madingImage(Guid id)
        {
            var result = await madingService.madingImage(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            if (System.IO.File.Exists(result.Data.ToString()))
            {
                var path = Path.GetExtension(result.Data.ToString());
                var getNameExtention = path.Replace(".", "");
                byte[] b = System.IO.File.ReadAllBytes(result.Data.ToString());
                return File(b, $"image/{getNameExtention}");
            }

            return NotFound();
        }

        [Authorize]
        [HttpPut]
        [Route("{id}")]
        public async Task<ActionResult> madingLikeDislike(Guid id, MadingLikeDislikeViewModels req)
        {
            var result = await madingService.madingLikeDislike(id, req);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
            if (result.Code == StatusCodes.Status400BadRequest) return BadRequest(result);
            return NoContent();
        }
    }
}