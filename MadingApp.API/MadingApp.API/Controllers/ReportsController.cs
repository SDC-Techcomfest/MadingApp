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
    [Route("api/v{version:apiVersion}/Reports")]
    [ApiController]
    [Authorize]

    public class ReportController : ControllerBase
    {
        private MadingAppContext _context;
        private ReportService ReportService;

        public ReportController(MadingAppContext db, IConfiguration config, IHttpContextAccessor contextAccessor)
        {
            this._context = db;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            ReportService = new ReportService(db, email, config);
        }

        // GET: api/BookMarks
        [HttpGet]
        public async Task<ActionResult> GetReport()
        {
            var result = await ReportService.GetReport();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // GET: api/BookMarks/5
        [HttpGet("{id}")]
        public async Task<ActionResult> GetReport(Guid id)
        {
            var result = await ReportService.GetById(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok(result);
        }

        // PUT: api/BookMarks/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<ActionResult> PutReport(Guid id, ReportViewModels request)
        {
            var result = await ReportService.PutReport(id, request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
            if (result.Code == StatusCodes.Status400BadRequest) return BadRequest(result);
            return NoContent();
        }

        // POST: api/BookMarks
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult> PostReport(ReportViewModels bookMark)
        {
            var result = await ReportService.PostReport(bookMark);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // DELETE: api/BookMarks/5
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteReport(Guid id)
        {
            var result = await ReportService.deleteReport(id);
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
