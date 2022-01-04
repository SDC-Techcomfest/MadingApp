
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
    public class CommentService : BaseService
    {
        private MadingAppContext _context;
        private ResponseModel result = new ResponseModel();
        private IConfiguration config;

        public CommentService(MadingAppContext db, string username, IConfiguration config) : base(db, username)
        {
            this.config = config;
        }
        public async Task<ResponseModel> GetComment()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            response(result, true, statusCode: 200, data: await db.Comments.Select(a => a).ToListAsync());
            return result;
        }

        public async Task<ResponseModel> GetById(Guid Id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Comment mb = await db.Comments.Where(s => s.CommentId == Id).FirstOrDefaultAsync();
            if (mb == null)
            {
                response(result, false, statusCode: 404, "Comment not found");
                return result;
            }
            response(result, true, statusCode: 200, data: mb);
            return result;
        }

        public async Task<ResponseModel> PostComment(CommentViewModels req)
        {
            Mading check = await db.Madings.Where(s => s.MadingId == req.MadingId).FirstOrDefaultAsync();
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }
            if (check == null)
            {
                response(result, false, statusCode: 404, "Mading Not Found");
                return result;
            }
            Comment add = mapper.Map<Comment>(req);
            add.CommentId = newGuid;
            add.CommentBy = baseUser.UserId;
            add.CreatedDate = DateTime.Now;
            add.CreatedBy = baseUser.UserId;

            req.CommentId = add.CommentId;
            req.MadingId = add.MadingId;
            req.CommentBy = add.CommentBy;
            req.CreatedBy = baseUser.UserId;
            await db.AddAsync(add);
            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: req);
            return result;
        }
        public async Task<ResponseModel> PutComment(Guid Id, CommentViewModels req)
        {
            Mading checkmading = await db.Madings.Where(s => s.MadingId == req.MadingId).FirstOrDefaultAsync();
            Comment check = await db.Comments.Where(s => s.CommentId == Id).FirstOrDefaultAsync();

            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            if (check == null)
            {
                response(result, false, statusCode: 404, "Comment not found");
                return result;
            }
            if (Id != req.CommentId)
            {
                response(result, false, statusCode: 400, "Id request and parametes must be same");
                return result;
            }
            check = mapper.Map(req, check);
            check.ModifiedDate = DateTime.Now;
            check.ModifiedBy = baseUser.UserId;

            if (check == null)
            {
                response(result, false, statusCode: 404, "Comment Not Found");
                return result;
            }
            await db.SaveChangesAsync();
            response(result, true, statusCode: 204);
            return result;
        }
        public async Task<ResponseModel> deleteComment(Guid id)
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            Comment get = await db.Comments.Where(a => a.CommentId == id).FirstOrDefaultAsync();

            if (get == null)
            {
                response(result, false, message: "Comment not found", statusCode: 404);
                return result;
            }

            db.Comments.Remove(get);
            await db.SaveChangesAsync();
            return result;
        }
    }
}
