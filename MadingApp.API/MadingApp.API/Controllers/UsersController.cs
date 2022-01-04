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
    [Route("api/v{version:apiVersion}/users")]
    [ApiController]
    [Authorize]
    public class UsersController : ControllerBase
    {
        private MadingAppContext db;
        private UserService usersService;
        private IWebHostEnvironment webHostEnvironment;

        public UsersController(MadingAppContext db, IConfiguration iconf, IHttpContextAccessor contextAccessor, IWebHostEnvironment webHostEnvironment)
        {
            this.db = db;
            this.webHostEnvironment = webHostEnvironment;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            usersService = new UserService(db, email, iconf, contextAccessor, webHostEnvironment);
        }

        [HttpGet]
        public async Task<ActionResult> getUsers()
        {
            var result = await usersService.getUsers();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            return Ok(result);
        }

        [HttpGet("{id}")]
        public async Task<ActionResult> getUserById(Guid id)
        {
            var result = await usersService.getUsers();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok(result.Data);
        }

        [HttpGet]
        [Route("me")]
        public async Task<ActionResult> Me()
        {
            var result = await usersService.Me();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        [HttpGet]
        [Route("image")]
        public async Task<ActionResult> UserImage()
        {
            var result = await usersService.UserImage();
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

        [HttpPost]
        [Route("upload-image")]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult> PostImage(IFormFile request)
        {
            var result = await usersService.PostImage(request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Created("Upload Image",result.Data);
        }

        [HttpPut]
        [Route("{id}")]
        [ProducesResponseType(StatusCodes.Status204NoContent)]
        public async Task<ActionResult> putUser(Guid id, UserViewModels request)
        {
            var result = await usersService.putUser(id, request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
            if (result.Code == StatusCodes.Status400BadRequest) return BadRequest(result);
            return NoContent();
        }

        [HttpPut]
        [ProducesResponseType(StatusCodes.Status201Created)]
        public async Task<ActionResult> editUserwithImage([FromForm] UserPutDataViewModels request)
        {
            try
            {
                var result = await usersService.editUserwithImage(request);
                if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
                return Created("localhost", result);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpDelete]
        [Route("{id}")]
        public async Task<ActionResult> deleteUser(Guid id)
        {
            var result = await usersService.deleteUser(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok();
        }
        [HttpPut]
        [Route("Change Password")]
        public async Task<ActionResult> ChangePassword(ChangePasswordViewModels request)
        {
            var result = await usersService.changepasword(request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok(result.Message);
        }
    }
}
