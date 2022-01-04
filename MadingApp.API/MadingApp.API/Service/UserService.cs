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
    public class UserService : BaseService
    {
        private IConfiguration iconf;
        private ResponseModel result = new ResponseModel();
        private IHttpContextAccessor contextAccessor;
        private IWebHostEnvironment webHostEnvironment;

        public UserService(MadingAppContext db, string username, IConfiguration iconf, IHttpContextAccessor contextAccessor, IWebHostEnvironment webHostEnvironment) : base(db, username)
        {
            this.iconf = iconf;
            this.webHostEnvironment = webHostEnvironment;
            this.contextAccessor = contextAccessor;
        }

        public async Task<ResponseModel> getUsers()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            response(result, true, statusCode: 200, data: await
                db.Users.Select(a => new
                {
                    a.UserId,
                    a.SchoolId,
                    a.Username,
                    a.FirstName,
                    a.LastName,
                    a.Gender,
                    a.DateOfBirth,
                    userImage = getUrl() + a.UserImage,
                    a.CreatedBy,
                    a.CreatedDate,
                    a.ModifiedBy,
                    a.ModifiedDate
                }).ToListAsync());
            return result;
        }

        public async Task<ResponseModel> getUserById(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            User get = await db.Users.Where(a => a.UserId == id).FirstOrDefaultAsync();
            if (get == null)
            {
                response(result, false, message: "User not found", statusCode: 404);
                return result;
            }
            response(result, true, statusCode: 200, data: get);
            return result;
        }

        public async Task<ResponseModel> postUser(UserViewModels request)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            User check = await db.Users.Where(a => a.Username == request.Username).FirstOrDefaultAsync();

            if (check != null)
            {
                response(result, false, statusCode: 409, "Username already exist");
                return result;
            }

            User add = mapper.Map<User>(request);
            add.UserId = newGuid;
            add.Password = Public.encryptMD5(request.Password);
            add.CreatedBy = baseUser.UserId;
            add.CreatedDate = DateTime.Now;

            await db.Users.AddAsync(add);
            await db.SaveChangesAsync();

            response(result, true, statusCode: 201, data: request);
            return result;
        }

        public async Task<ResponseModel> test()
        {
            List<string> x = Directory.GetFiles(Public.PATH_IMAGE).Where(a => a.Contains(baseUser.UserImage)).Select(a => a).ToList();
            string getFileName = Path.GetFileName(x[0]);
            response(result, true, statusCode: 200, data: getFileName);
            return result;
        }

        public async Task<ResponseModel> Me()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            var data = await db.Users.Where(a => a.UserId == baseUser.UserId).Select(a => new
            {
                a.UserId,
                a.SchoolId,
                a.Username,
                a.FirstName,
                a.LastName,
                a.Gender,
                a.DateOfBirth,
                userImage = getUrl() + a.UserImage,
                a.CreatedBy,
                a.CreatedDate,
                a.ModifiedBy,
                a.ModifiedDate
            }).FirstOrDefaultAsync();
            response(result, true, statusCode: 200, data: data);
            return result;
        }
        public async Task<ResponseModel> editUserwithImage(UserPutDataViewModels request)
        {
           // Category checkFkCategory = await db.Categories.Where(a => a.CategoryId == request.CateogryId).FirstOrDefaultAsync();
            //Report checkFKReport = await db.Reports.Where(a => a.ReportId == request.ReportId).FirstOrDefaultAsync();
             

            string path = webHostEnvironment.WebRootPath;

            if (!Directory.Exists(path))
            {
                Directory.CreateDirectory(path);
            }

            var fileName = "/user-images/" + Guid.NewGuid() + "-" + request.UserImage.FileName;

            using (FileStream fileStream = System.IO.File.Create(path + fileName))
            {
                request.UserImage.CopyTo(fileStream);
                fileStream.Flush();

                User add = await db.Users.Where(a => a.UserId == request.UserId).FirstOrDefaultAsync();
                add.LastName = request.Lastname;
                add.FirstName = request.Firstname;
                add.UserImage = fileName;
                add.CreatedBy = new Guid("c12a9d49-73b5-4f1b-9128-abe4094b413c");
                add.CreatedDate = DateTime.Now;
                await db.SaveChangesAsync();

                response(result, true, statusCode: 201, data: request);
                return result;
            }
        }
        public async Task<ResponseModel> putUser(Guid id, UserViewModels request)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            User edt = await db.Users.Where(a => a.UserId == id).FirstOrDefaultAsync();
            User checkExist = await db.Users.Where(a => a.Username == request.Username).FirstOrDefaultAsync();

            if (edt == null)
            {
                response(result, false, message: "User not found", statusCode: 404);
                return result;
            }

            if (id != request.Id)
            {
                response(result, false, message: "Id in request & parameter must be same", statusCode: 400);
                return result;
            }

            if (edt.Username != request.Username)
            {
                if (checkExist != null)
                {
                    response(result, false, message: "Username already exist", statusCode: 409);
                    return result;
                }
            }

            edt = mapper.Map(request, edt);
            edt.Password = Public.encryptMD5(request.Password);
            edt.ModifiedBy = baseUser.UserId;
            edt.ModifiedDate = DateTime.Now;

            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }

        public async Task<ResponseModel> deleteUser(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            User get = await db.Users.Where(a => a.UserId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "User not found", statusCode: 404);
                return result;
            }

            db.Users.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }

        public async Task<ResponseModel> UserImage()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            string path = webHostEnvironment.WebRootPath;
            string pathUser = baseUser.UserImage;
            var filePath = path + pathUser;

            response(result, false, statusCode: 200, data: filePath);
            return result;
        }

        public async Task<ResponseModel> PostImage(IFormFile request)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            if (request.Length > 0)
            {
                string path = webHostEnvironment.WebRootPath;

                if (!Directory.Exists(path))
                {
                    Directory.CreateDirectory(path);
                }

                var fileName = "/user-images/" + Guid.NewGuid() + "-" + request.FileName;

                var x = Path.GetExtension(request.FileName);

                var orderid = "/user-images/" + new Random().Next(1000, 9999) + "-" + DateTime.Now.ToString("yyyyMMddhhmmss") + x;//Create Unik OrderId

                using (FileStream fileStream = System.IO.File.Create(path + orderid))
                {
                    request.CopyTo(fileStream);
                    fileStream.Flush();

                    baseUser.UserImage = orderid;

                    await db.SaveChangesAsync();

                    response(result, false, statusCode: 200, data: "Successfully upload image");
                    return result;
                }
            }
            else
            {
                response(result, false, statusCode: 400, "Please select the image");
                return result;
            }
        }

        public async Task<ResponseModel> changepasword(ChangePasswordViewModels request)
        {
            User us = await db.Users.Where(s => s.UserId == request.UserId).FirstOrDefaultAsync();
            User check = await db.Users.Where(a => a.Password == Public.encryptMD5(request.OldPassword)).FirstOrDefaultAsync();

            //untuk meng-encrypt password
            var encryptold = Public.encryptMD5(us.Password);
            var encryptnew = Public.encryptMD5(request.newPassword);
            var encryptreqoldpass = Public.encryptMD5(request.OldPassword);

            if (us == null)
            {
                response(result, false, statusCode: 404, "User not found");
                return result;
            }
            if (check == null)
            {
                response(result, false, statusCode: 404, "Old Password not valid");
                return result;
            }
            if (us.Password == encryptnew)
            {
                response(result, false, statusCode: 404, "Please use a different password");
                return result;
            }
            else
            {
                us.Password = Public.encryptMD5(request.newPassword);
                await db.SaveChangesAsync();
                response(result, true, statusCode: 200, "Success change password");
                return result;
            }
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