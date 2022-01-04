using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MadingApp.API.Models;
using MadingApp.API.Service;
using Microsoft.Extensions.Configuration;
using Microsoft.AspNetCore.Authorization;

namespace MadingApp.API.Controllers
{
    [Produces("application/json")]
    [ApiVersion("1.0")]
    [Route("api/v{version:apiVersion}/Schools")]
    [ApiController]
    [Authorize]

    public class SchoolController : ControllerBase
    {
        private MadingAppContext _context;
        private SchoolService SchoolService;

        public SchoolController(MadingAppContext db, IConfiguration config, IHttpContextAccessor contextAccessor)
        {
            this._context = db;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            SchoolService = new SchoolService(db, email, config);
        }

        // GET: api/BookMarks
        [HttpGet]
        public async Task<ActionResult> GetSchool()
        {
            var result = await SchoolService.GetSchool();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // GET: api/BookMarks/5
        [HttpGet("{id}")]
        public async Task<ActionResult> GetSchool(Guid id)
        {
            var result = await SchoolService.GetById(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok(result);
        }

        // PUT: api/BookMarks/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<ActionResult> PutSchool(Guid id, SchoolViewModels request)
        {
            var result = await SchoolService.PutSchool(id, request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
            if (result.Code == StatusCodes.Status400BadRequest) return BadRequest(result);
            return NoContent();
        }

        // POST: api/BookMarks
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult> PostSchool(SchoolViewModels bookMark)
        {
            var result = await SchoolService.PostSchool(bookMark);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // DELETE: api/BookMarks/5
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteSchool(Guid id)
        {
            var result = await SchoolService.deleteSchool(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok();
        }

        private bool BookMarkExists(Guid id)
        {
            return _context.BookMarks.Any(e => e.BookMarkId == id);
        }
    }
}
