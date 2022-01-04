
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
    public class ReportService : BaseService
    {
        private MadingAppContext _context;
        private ResponseModel result = new ResponseModel();
        private IConfiguration config;

        public ReportService(MadingAppContext db, string username, IConfiguration config) : base(db, username)
        {
            this.config = config;
        }
        public async Task<ResponseModel> GetReport()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            response(result, true, statusCode: 200, data: await db.Reports.Select(a => a).ToListAsync());
            return result;
        }

        public async Task<ResponseModel> GetById(Guid Id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Report mb = await db.Reports.Where(s => s.ReportId == Id).FirstOrDefaultAsync();
            if (mb == null)
            {
                response(result, false, statusCode: 404, "Report not found");
                return result;
            }
            response(result, true, statusCode: 200, data: mb);
            return result;
        }

        public async Task<ResponseModel> PostReport(ReportViewModels req)
        {
            Report check = await db.Reports.Where(s => s.Name == req.Name).FirstOrDefaultAsync();
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }
            if (check != null)
            {
                response(result, false, statusCode: 409, "Report name already exist");
                return result;
            }
            Report add = mapper.Map<Report>(req);
            add.ReportId = newGuid;
            add.CreatedDate = DateTime.Now;
            add.CreatedBy = baseUser.UserId;

            req.ReportId = add.ReportId;
            req.CreatedBy = baseUser.UserId;
            await db.AddAsync(add);
            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: req);
            return result;
        }
        public async Task<ResponseModel> PutReport(Guid Id, ReportViewModels req)
        {
            Report check = await db.Reports.Where(s => s.ReportId == Id).FirstOrDefaultAsync();

            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            if (check == null)
            {
                response(result, false, statusCode: 404, "Report not found");
                return result;
            }
            if (Id != req.ReportId)
            {
                response(result, false, statusCode: 400, "Id request and parametes must be same");
                return result;
            }
            check = mapper.Map(req, check);
            check.ModifiedDate = DateTime.Now;
            check.ModifiedBy = baseUser.UserId;

            if (check == null)
            {
                response(result, false, statusCode: 404, "Report Not Found");
                return result;
            }
            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }
        public async Task<ResponseModel> deleteReport(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Report get = await db.Reports.Where(a => a.ReportId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "Report not found", statusCode: 404);
                return result;
            }

            db.Reports.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }
    }
}
