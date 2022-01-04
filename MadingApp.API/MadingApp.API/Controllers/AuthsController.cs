using MadingApp.API.Models;
using MadingApp.API.Service;
using MadingApp.API.Utility;
using MadingApp.API.ViewModels;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System;
using System.Threading.Tasks;

namespace MadingApp.API.Controllers
{
    [Produces("application/json")]
    [ApiVersion("1.0")]
    [Route("api/v{version:apiVersion}/auth")]
    [ApiController]
    public class AuthsController : ControllerBase
    {
        private MadingAppContext db;
        private AuthService authService;
        private AuthViewModels AuthViewModels;
        private IHttpContextAccessor httpContextAccessor;

        public AuthsController(MadingAppContext db, IConfiguration iconf, IHttpContextAccessor contextAccessor)
        {
            this.db = db;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            authService = new AuthService(db, email, iconf);
            this.httpContextAccessor = contextAccessor;
        }

        [HttpPost]
        [Route("login")]
        public async Task<ActionResult> Login(AuthViewModels request)
        {
            try
            {
                var result = await authService.Login(request);
                if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
                return Ok(result);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [HttpPost]
        [ProducesResponseType(StatusCodes.Status201Created)]
        [Route("sign-up")]
        public async Task<ActionResult> SignUp(SignUpViewModels request)
        {
            try
            {
                var result = await authService.SignUp(request);
                if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
                return Created("localhost", result);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }

        [Authorize]
        [HttpGet]
        [Route("logout")]
        public async Task<ActionResult> Logout()
        {
            try
            {
                var result = await authService.Logout();
                if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
                return Ok(result);
            }
            catch (Exception e)
            {
                return StatusCode(500, e.Message);
            }
        }
    }
}
    