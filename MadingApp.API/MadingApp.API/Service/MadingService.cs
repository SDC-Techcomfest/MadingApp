using MadingApp.API.Models;
using MadingApp.API.Utility;
using MadingApp.API.ViewModels;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;

namespace MadingApp.API.Service
{
    public class MadingService : BaseService
    {
        private IConfiguration iconf;
        private ResponseModel result = new ResponseModel();
        private IHttpContextAccessor contextAccessor;
        private IWebHostEnvironment webHostEnvironment;

        public MadingService(MadingAppContext db, string username, IConfiguration iconf, IHttpContextAccessor contextAccessor, IWebHostEnvironment webHostEnvironment) : base(db, username)
        {
            this.iconf = iconf;
            this.webHostEnvironment = webHostEnvironment;
            this.contextAccessor = contextAccessor;
        }

        public async Task<ResponseModel> getMadings()
        {
            var data = await (from a in db.Madings
                              join b in db.Users
                              on a.CreatedBy equals b.UserId
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
                                  authorImage= getUrl() + b.UserImage,
                                  a.CreatedDate,
                                  a.ModifiedBy,
                                  a.ModifiedDate,
                              }).OrderByDescending(a => a.CreatedDate).ToListAsync();
            response(result, true, statusCode: 200, data: data);
            return result;
        }

        public async Task<ResponseModel> getMyMadings()
        {
            var data = await (from a in db.Madings
                              join b in db.Users
                              on a.CreatedBy equals b.UserId
                              where a.CreatedBy == baseUser.UserId
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
                              }).OrderByDescending(a => a.CreatedDate).ToListAsync();
            response(result, true, statusCode: 200, data: data);
            return result;
        }

        public async Task<ResponseModel> getMadingsHotTopics()
        {
            var data = await (from a in db.Madings
                              join b in db.Users
                              on a.CreatedBy equals b.UserId
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
                              }).OrderByDescending(a => a.Likes).Take(10).ToListAsync();
            response(result, true, statusCode: 200, message: "10", data: data);
            return result;
        }

        public async Task<ResponseModel> postMading(MadingViewModels request)
        {
            //if (checkUsrLogin())
            //{
            //    response(result, false, statusCode: 401, "Unauthorized");
            //    return result;
            //}

            Category checkFkCategory = await db.Categories.Where(a => a.CategoryId == request.CateogryId).FirstOrDefaultAsync();
            Report checkFKReport = await db.Reports.Where(a => a.ReportId == request.ReportId).FirstOrDefaultAsync();

            //if (checkFkCategory == null)
            //{
            //    response(result, false, statusCode: 404, "Category ID not found");
            //    return result;
            //}

            //if (checkFKReport == null)
            //{
            //    response(result, false, statusCode: 404, "Report ID not found");
            //    return result;
            //}

            string path = webHostEnvironment.WebRootPath;

            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }

            var fileName = "/mading-images/" + Guid.NewGuid() + "-" + request.MadingImage.FileName;

            using (FileStream fileStream = System.IO.File.Create(path + fileName))
            {
                request.MadingImage.CopyTo(fileStream);
                fileStream.Flush();

                Mading add = mapper.Map<Mading>(request);
                add.MadingId = newGuid;
                add.MadingImage = fileName;
                add.CreatedBy = new Guid("c12a9d49-73b5-4f1b-9128-abe4094b413c");
                add.CreatedDate = DateTime.Now;

                await db.Madings.AddAsync(add);
                await db.SaveChangesAsync();

                response(result, true, statusCode: 201, data: request);
                return result;
            }
        }

        //public async Task<ResponseModel> postMadingImage(IFormFile request, Guid id)
        //{
        //    if (checkUsrLogin())
        //    {
        //        response(result, false, statusCode: 401, "Unauthorized");
        //        return result;
        //    }

        //    if (request.Length > 0)
        //    {
        //        string path = webHostEnvironment.WebRootPath;

        //        if (!Directory.Exists(path))
        //        {
        //            Directory.CreateDirectory(path);
        //        }

        //        var fileName = "/mading-images/" + Guid.NewGuid() + "-" + request.FileName;

        //        using (FileStream fileStream = System.IO.File.Create(path + fileName))
        //        {
        //            request.CopyTo(fileStream);
        //            fileStream.Flush();

        //            Mading edt = await db.Madings.Where(a => a.MadingId == id).FirstOrDefaultAsync();
        //            edt.MadingImage = fileName;
        //            edt.ModifiedBy = baseUser.UserId;
        //            edt.ModifiedDate = DateTime.Now;

        //            await db.SaveChangesAsync();

        //            response(result, false, statusCode: 200, data: "Successfully upload image");
        //            return result;
        //        }
        //    }
        //    else
        //    {
        //        response(result, false, statusCode: 400, "Please select the image");
        //        return result;
        //    }
        //}

        public async Task<ResponseModel> madingImage(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Mading mading = await db.Madings.Where(a => a.MadingId == id).FirstOrDefaultAsync();

            string path = webHostEnvironment.WebRootPath;
            string pathMading = mading.MadingImage;
            var filePath = path + pathMading;

            response(result, false, statusCode: 200, data: filePath);
            return result;
        }
        public async Task<ResponseModel> madingLikeDislike(Guid id, MadingLikeDislikeViewModels req)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Mading mading = await db.Madings.Where(a => a.MadingId == id).FirstOrDefaultAsync();
            mading.Likes = mading.Likes +  req.Likes;
            mading.Dislikes = mading.Dislikes + req.Dislikes;

            await db.SaveChangesAsync();

            response(result, false, statusCode: 200);
            return result;
        }

        public string getUrl()
        {
            var http = contextAccessor.HttpContext.Request.Scheme;
            var slash = "://";
            var host = contextAccessor.HttpContext.Request.Host.Value;
            return http + slash + host;
        }
    }
}