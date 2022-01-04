using MadingApp.API.Models;
using MadingApp.API.Utility;
using MadingApp.API.ViewModels;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using System;
using System.Linq;
using System.Threading.Tasks;

namespace MadingApp.API.Service
{
    public class AuthService : BaseService
    {
        private IConfiguration iconf;
        private ResponseModel result = new ResponseModel();
        private MadingAppContext _context;

        public AuthService(MadingAppContext db, string username, IConfiguration iconf) : base(db, username)
        {
            this.iconf = iconf;
        }

        public async Task<ResponseModel> Login(AuthViewModels request)
        {
            User check = await db.Users.Where(a => a.Username == request.Username && a.Password == Public.encryptMD5(request.Password)).FirstOrDefaultAsync();

            if (check == null)
            {
                response(result, false, statusCode: 404, "Invalid Username or Password");
                return result;
            }

            var data = getJWT(check);

            check.Token = data;

            Public.TOKEN = data;

            await db.SaveChangesAsync();
            response(result, true, statusCode: 200, data: data);
            return result;
        }

        public async Task<ResponseModel> SignUp(SignUpViewModels request)
        {
            User check = await db.Users.Where(a => a.Username == request.Username).FirstOrDefaultAsync();

            if (check != null)
            {
                response(result, false, statusCode: 409, "Username already exist");
                return result;
            }

            User add = mapper.Map<User>(request);
            add.UserId = newGuid;
            add.DateOfBirth = request.DateOfBirth;
            add.Password = Public.encryptMD5(request.Password);
            add.UserImage = "/user-images/default-image.png";
            add.CreatedBy = new Guid();
            add.CreatedDate = DateTime.Now;

            await db.Users.AddAsync(add);
            await db.SaveChangesAsync();

            response(result, true, statusCode: 201, data: request);
            return result;
        }

        public async Task<ResponseModel> Logout()
        {
            if (checkUsrLogin())
            {
                response(result, false, statusCode: 401, "Unauthorized");
                return result;
            }

            User user = await db.Users
                        .Where(x => x.Token == Public.TOKEN).FirstOrDefaultAsync();

            if (user != null)
            {
                Public.TOKEN = string.Empty;
                user.Token = null;
                await db.SaveChangesAsync();
                response(result, true, statusCode: 200, "Successfully Logout");
            }
            return result;
        }
    }
}
