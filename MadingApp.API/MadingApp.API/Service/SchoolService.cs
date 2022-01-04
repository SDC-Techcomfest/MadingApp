
using MadingApp.API.Models;
using MadingApp.API.ViewModels;
using System;
using System.Linq;
using System.Threading.Tasks;
using MadingApp.API.Models;
using MadingApp.API.Utility;
using Microsoft.Extensions.Configuration;
using Microsoft.EntityFrameworkCore;

namespace MadingApp.API.Service
{
    public class SchoolService : BaseService
    {
        private MadingAppContext _context;
        private ResponseModel result = new ResponseModel();
        private IConfiguration config;

        public SchoolService(MadingAppContext db, string username, IConfiguration config) : base(db, username)
        {
            this.config = config;
        }
        public async Task<ResponseModel> GetSchool()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            response(result, true, statusCode: 200, data: await db.Schools.Select(a => a).ToListAsync());
            return result;
        }

        public async Task<ResponseModel> GetById(Guid Id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            School mb = await db.Schools.Where(s => s.SchoolId == Id).FirstOrDefaultAsync();
            if (mb == null)
            {
                response(result, false, statusCode: 404, "School not found");
                return result;
            }
            response(result, true, statusCode: 200, data: mb);
            return result;
        }

        public async Task<ResponseModel> PostSchool(SchoolViewModels req)
        {
            School check = await db.Schools.Where(s => s.Name == req.Name).FirstOrDefaultAsync();
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }
            if (check != null)
            {
                response(result, false, statusCode: 4009, "School Name already exist");
                return result;
            }
            School add = mapper.Map<School>(req);
            add.SchoolId = newGuid;
            add.CreatedDate = DateTime.Now;
            add.CreatedBy = baseUser.UserId;

            req.SchoolId = add.SchoolId;
            req.CreatedBy = baseUser.UserId;
            await db.AddAsync(add);
            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: req);
            return result;
        }
        public async Task<ResponseModel> PutSchool(Guid Id, SchoolViewModels req)
        {
            School check = await db.Schools.Where(s => s.SchoolId == Id).FirstOrDefaultAsync();
            School checkname = await db.Schools.Where(s => s.Name == req.Name).FirstOrDefaultAsync();
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            if (check == null)
            {
                response(result, false, statusCode: 404, "School not found");
                return result;
            }
            if (Id != req.SchoolId)
            {
                response(result, false, statusCode: 400, "Id request and parametes must be same");
                return result;
            }
            check = mapper.Map(req, check);
            check.ModifiedDate = DateTime.Now;
            check.ModifiedBy = baseUser.UserId;

            if (check == null)
            {
                response(result, false, statusCode: 404, "School Not Found");
                return result;
            }
            if (check != null)
            {
                response(result, false, statusCode: 4009, "School Name already exist");
                return result;
            }
            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }
        public async Task<ResponseModel> deleteSchool(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            School get = await db.Schools.Where(a => a.SchoolId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "School not found", statusCode: 404);
                return result;
            }

            db.Schools.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }
    }
}
