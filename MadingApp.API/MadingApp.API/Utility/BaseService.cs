using AutoMapper;
using MadingApp.API.Models;
using MadingApp.API.Service;
using MadingApp.API.ViewModels;
using Microsoft.Extensions.Configuration;
using Microsoft.IdentityModel.Tokens;
using System;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Security.Claims;
using System.Text;

namespace MadingApp.API.Utility
{
    public class BaseService
    {
        #region MyRegion

        public MadingAppContext db = new MadingAppContext();
        public static User baseUser = new User();
        public Mapper mapper;
        public Guid newGuid = Guid.NewGuid();

        #endregion MyRegion

        public BaseService(MadingAppContext db, string username)
        {
            this.mapper = new Mapper(config);
            this.db = db;
            baseUser = db.Users.Where(a => a.Username == username).FirstOrDefault();
        }


        #region CheckUsrLogin

        public bool checkUsrLogin()
        {
            bool result = baseUser.Token == null ? true : false;
            return result;
        }

        #endregion CheckUsrLogin

        #region Env

        private string jwtKey = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build().GetSection("Jwt")["Key"];
        private string jwtIssuer = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build().GetSection("Jwt")["Issuer"];
        private string jwtAudience = new ConfigurationBuilder().AddJsonFile("appsettings.json").Build().GetSection("Jwt")["Audience"];

        #endregion Env

        #region GetJwt

        public string getJWT(User us)
        {
            var claim = new[] { new Claim("Username", us.Username) };
            var Key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(jwtKey));
            var Sign = new SigningCredentials(Key, SecurityAlgorithms.HmacSha256);
            var Token = new JwtSecurityToken(
                jwtIssuer,
                jwtAudience,
                claims: claim,
                expires: DateTime.Now.AddMinutes(10),
                signingCredentials: Sign);
            return new JwtSecurityTokenHandler().WriteToken(Token);
        }

        #endregion GetJwt

        #region ResponseApi

        public static void response(ResponseModel response, bool status, int statusCode, string message = null, object data = null)
        {
            response.Code = statusCode;
            response.Status = status;
            response.Message = message;
            response.Data = data;
        }

        #endregion ResponseApi

        #region Mapper

        private MapperConfiguration config = new MapperConfiguration(cfg =>
        {
            cfg.AllowNullCollections = true;
            cfg.AllowNullDestinationValues = true;
            cfg.CreateMap<BookMark, BookMarkViewModels>().ReverseMap();
            cfg.CreateMap<Category, CategoryViewModels>().ReverseMap();
            cfg.CreateMap<Comment, CommentViewModels>().ReverseMap();
            cfg.CreateMap<Mading, MadingViewModels>().ReverseMap();
            cfg.CreateMap<Report, ReportViewModels>().ReverseMap();
            cfg.CreateMap<School, SchoolViewModels>().ReverseMap();
            cfg.CreateMap<User, UserViewModels>().ReverseMap();
            cfg.CreateMap<User, SignUpViewModels>().ReverseMap();

        });

        #endregion Mapper


      
    }
}
