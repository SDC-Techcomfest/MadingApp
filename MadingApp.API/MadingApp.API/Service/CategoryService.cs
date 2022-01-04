
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
    public class CategoryService : BaseService
    {
        private MadingAppContext _context;
        private ResponseModel result = new ResponseModel();
        private IConfiguration config;

        public CategoryService(MadingAppContext db, string username, IConfiguration config) : base(db, username)
        {
            this.config = config;
        }
        public async Task<ResponseModel> GetCategory()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            response(result, true, statusCode: 200, data: await db.Categories.Select(a => a).ToListAsync());
            return result;
        }

        public async Task<ResponseModel> GetById(Guid Id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Category mb = await db.Categories.Where(s => s.CategoryId == Id).FirstOrDefaultAsync();
            if (mb == null)
            {
                response(result, false, statusCode: 404, "Category not found");
                return result;
            }
            response(result, true, statusCode: 200, data: mb);
            return result;
        }

        public async Task<ResponseModel> PostCategory(CategoryViewModels req)
        {
            Category check = await db.Categories.Where(s => s.Name == req.Name).FirstOrDefaultAsync();
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }
            if (check != null)
            {
                response(result, false, statusCode: 409, "Name already exist");
                return result;
            }
            Category add = mapper.Map<Category>(req);
            add.CategoryId = newGuid;
            add.CreatedDate = DateTime.Now;
            add.CreatedBy = baseUser.UserId;

            req.CategoryId = add.CategoryId;
            req.CreatedBy = baseUser.UserId;
            await db.AddAsync(add);
            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: req);
            return result;
        }
        public async Task<ResponseModel> PutCategory(Guid Id, CategoryViewModels req)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Category check = await db.Categories.Where(s => s.CategoryId == Id).FirstOrDefaultAsync();
            if (check == null)
            {
                response(result, false, statusCode: 404, "Category not found");
                return result;
            }
            if (Id != req.CategoryId)
            {
                response(result, false, statusCode: 400, "Id request and parametes must be same");
                return result;
            }
            check = mapper.Map(req, check);
            check.ModifiedDate = DateTime.Now;
            check.ModifiedBy = baseUser.UserId;

            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }
        public async Task<ResponseModel> deleteCategory(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Category get = await db.Categories.Where(a => a.CategoryId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "Category not found", statusCode: 404);
                return result;
            }

            db.Categories.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }
    }
}
