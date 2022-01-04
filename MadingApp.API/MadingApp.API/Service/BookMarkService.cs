using MadingApp.API.Models;
using MadingApp.API.ViewModels;
using System;
using System.Linq;
using System.Threading.Tasks;

using MadingApp.API.Models;

using MadingApp.API.Utility;
using Microsoft.Extensions.Configuration;
using Microsoft.EntityFrameworkCore;
using System.Collections.Generic;
using Microsoft.AspNetCore.Http;

namespace MadingApp.API.Service
{
    public class BookMarkService : BaseService
    {
        private MadingAppContext _context;
        private BookMarkService bookmarkservice;
        private ResponseModel result = new ResponseModel();
        private IHttpContextAccessor contextAccessor;
        private IConfiguration config;

        public BookMarkService(MadingAppContext db, string username, IConfiguration config, IHttpContextAccessor contextAccessor) : base(db, username)
        {
            this.config = config;
            this.contextAccessor = contextAccessor;
        }
        public async Task<ResponseModel> getMyBookMarks()
        {
            List<BookMark> bn = await db.BookMarks.Where(a => a.UserId == baseUser.UserId).ToListAsync();
            response(result, true, statusCode: 200, data: bn);
            return result;
        }
        public async Task<ResponseModel> GetBookMark()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            List<BookMark> bookMarks = await db.BookMarks.Where(a => a.UserId == baseUser.UserId).Select(a => a).OrderByDescending(a => a.CreatedDate).ToListAsync();
            UserResponseBookMark userResponseBookMark = new UserResponseBookMark();
            MadingResponseBookMark madingResponseBookMark = new MadingResponseBookMark();
            List<BookMarkViewModel> books = new List<BookMarkViewModel>();
            BookMarkViewModelResponse bookMarkViewModel1 = new BookMarkViewModelResponse();
            foreach (var bookMark in bookMarks)
            {
                BookMarkViewModel bookMarkViewModel = new BookMarkViewModel();
                bookMarkViewModel.BookMarkId = bookMark.BookMarkId;
                bookMarkViewModel.CreatedBy = bookMark.CreatedBy;
                bookMarkViewModel.CreatedDate = bookMark.CreatedDate;
                bookMarkViewModel.ModifiedBy = bookMark.ModifiedBy;
                bookMarkViewModel.ModifiedDate = bookMark.ModifiedDate;

                User us = await db.Users.Where(a => a.UserId == bookMark.UserId).FirstOrDefaultAsync();
                bookMarkViewModel.User = us;

                Mading mad = new Mading();

                object x = await (from a in db.Madings
                                  join b in db.Users
                                  on a.CreatedBy equals b.UserId
                                  where a.MadingId == bookMark.MadingId
                                  select new
                                  {
                                      a.MadingId,
                                      a.CateogryId,
                                      a.Title,
                                      a.Description,
                                      madingImage = getUrl() + a.MadingImage,
                                      a.Likes,
                                      a.Dislikes,
                                      a.ReportId,
                                      a.ReportCount,
                                      a.CreatedBy,
                                      authors = b.FirstName,
                                      a.CreatedDate,
                                      a.ModifiedBy,
                                      a.ModifiedDate,
                                  }).FirstOrDefaultAsync();
                bookMarkViewModel.Mading = x;

                books.Add(bookMarkViewModel);
            }

            bookMarkViewModel1.Bookmarks = books;
            var data = bookMarkViewModel1;
            response(result, true, statusCode: 200, data: data);
            return result;
        }

        public string getUrl()
        {
            var http = contextAccessor.HttpContext.Request.Scheme;
            var slash = "://";
            var host = contextAccessor.HttpContext.Request.Host.Value;
            return http + slash + host;
        }

        public async Task<ResponseModel> GetById(Guid Id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            BookMark mb = await db.BookMarks.Where(s => s.BookMarkId == Id).FirstOrDefaultAsync();
            if (mb == null)
            {
                response(result, false, statusCode: 404, "Book mark not found");
                return result;
            }
            response(result, true, statusCode: 200, data: mb);
            return result;
        }

        public async Task<ResponseModel> PostBookMark(BookMarkViewModels req)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }
            BookMark check = await db.BookMarks.Where(a => a.UserId == baseUser.UserId).FirstOrDefaultAsync();

            if (check.MadingId == req.MadingId)
            {
                response(result, false, statusCode: 409, "Mading already bookmark!");
                return result;
            }

            Mading checkmading = await db.Madings.Where(s => s.MadingId == req.MadingId).FirstOrDefaultAsync();
           
            if (checkmading == null)
            {
                response(result, false, statusCode: 404, "Mading Not found");
                return result;
            }
            BookMark add = mapper.Map<BookMark>(req);
            add.BookMarkId = newGuid;
            add.MadingId = req.MadingId;
            add.UserId = baseUser.UserId;
            add.CreatedDate = DateTime.Now;
            add.CreatedBy = baseUser.UserId;

            //req.UserId = add.UserId;
            req.MadingId = add.MadingId;
            await db.AddAsync(add);
            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: req);
            return result;
        }

        public async Task<ResponseModel> PutBookMark(Guid Id, BookMarkViewModels req)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            BookMark check = await db.BookMarks.Where(s => s.BookMarkId == Id).FirstOrDefaultAsync();
            if (check == null)
            {
                response(result, false, statusCode: 404, "Book mark not found");
                return result;
            }
            //if (Id != req.BookMarkId)
            //{
            //    response(result, false, statusCode: 400, "Id request and parametes must be same");
            //    return result;
            //}
            check = mapper.Map(req, check);
            check.ModifiedDate = DateTime.Now;
            check.ModifiedBy = baseUser.UserId;

            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }

        public async Task<ResponseModel> deleteBookMark(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            BookMark get = await db.BookMarks.Where(a => a.BookMarkId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "Book Author not found", statusCode: 404);
                return result;
            }

            db.BookMarks.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }
    }
}