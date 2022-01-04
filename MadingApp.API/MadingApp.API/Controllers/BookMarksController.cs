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
using MadingApp.API.ViewModels;

namespace MadingApp.API.Controllers
{
    [Produces("application/json")]
    [ApiVersion("1.0")]
    [Route("api/v{version:apiVersion}/book-marks")]
    [ApiController]
    [Authorize]
    public class BookMarksController : ControllerBase
    {
        private MadingAppContext _context;
        private BookMarkService bookmarkservice;

        public BookMarksController(MadingAppContext db, IConfiguration config, IHttpContextAccessor contextAccessor)
        {
            this._context = db;
            string auth = contextAccessor.HttpContext.Request.Headers["Authorization"];
            string email = auth == string.Empty ? string.Empty : db.getUserUsername(auth);
            bookmarkservice = new BookMarkService(db, email, config, contextAccessor);
        }

        [HttpGet]
        [Route("my-bookmarks")]
        public async Task<ActionResult> getMyBookMarks()
        {
            var result = await bookmarkservice.getMyBookMarks();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);

            return Ok(result);
        }
        [HttpGet]
        public async Task<ActionResult<List<BookMarkViewModelResponse>>> GetBookMarks()
        {
            var result = await bookmarkservice.GetBookMark();
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // GET: api/BookMarks/5
        [HttpGet("{id}")]
        public async Task<ActionResult> GetBookMark(Guid id)
        {
            var result = await bookmarkservice.GetById(id);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            return Ok(result);
        }

        // PUT: api/BookMarks/5
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPut("{id}")]
        public async Task<ActionResult> PutBookMark(Guid id, BookMarkViewModels request)
        {
            var result = await bookmarkservice.PutBookMark(id, request);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            if (result.Code == StatusCodes.Status404NotFound) return NotFound(result);
            if (result.Code == StatusCodes.Status409Conflict) return Conflict(result);
            if (result.Code == StatusCodes.Status400BadRequest) return BadRequest(result);
            return NoContent();
        }

        // POST: api/BookMarks
        // To protect from overposting attacks, see https://go.microsoft.com/fwlink/?linkid=2123754
        [HttpPost]
        public async Task<ActionResult> PostBookMark(BookMarkViewModels bookMark)
        {
            var result = await bookmarkservice.PostBookMark(bookMark);
            if (result.Code == StatusCodes.Status401Unauthorized) return Unauthorized(result);
            return Ok(result);
        }

        // DELETE: api/BookMarks/5
        [HttpDelete("{id}")]
        public async Task<ActionResult> DeleteBookMark(Guid id)
        {
            var result = await bookmarkservice.deleteBookMark(id);
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