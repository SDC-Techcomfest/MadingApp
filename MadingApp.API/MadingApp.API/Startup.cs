using MadingApp.API.Models;
using MadingApp.API.Utility;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Hosting;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.HttpsPolicy;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.IdentityModel.Tokens;
using Microsoft.OpenApi.Models;
using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.Serialization;
using System.Text;
using System.Threading.Tasks;

namespace MadingApp.API
{
    public class Startup
    {
        public Startup(IConfiguration configuration)
        {
            Configuration = configuration;
        }

        public IConfiguration Configuration { get; }
        // This method gets called by the runtime. Use this method to add services to the container.
        public void ConfigureServices(IServiceCollection services)
        {
            services.AddControllersWithViews();
            services.AddMvc();
            services.AddApiVersioning();
            services.AddDbContext<MadingAppContext>(a => a.UseSqlServer(Configuration.GetConnectionString("db")));
            services.AddSingleton<IHttpContextAccessor, HttpContextAccessor>();
            services.AddControllers()
                .AddNewtonsoftJson(options =>
                {
                    var convert = new Newtonsoft.Json.Converters.IsoDateTimeConverter
                    {
                         DateTimeFormat = "dd MMMM yyyy"
                    };
                    options.SerializerSettings.Converters.Add(convert);
                    options.SerializerSettings.DateFormatHandling = DateFormatHandling.IsoDateFormat;
                });
            services.AddSwaggerGen(c =>
            {
                c.SwaggerDoc("v1", new OpenApiInfo
                {
                    Title = "MadingApp",
                    Description = "Welcome To MadingApp API",
                    Version = "v1"
                });

                //c.AddServer(new OpenApiServer
                //{
                //    Url = Configuration["url"]
                //});

                c.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
                {
                    Name = "Authorization",
                    Description = "JWT Authorization header using the Bearer scheme.\r\n\r\n Enter 'Bearer' [space] and then your token in the text input below.\r\n\r\n Example: 'Bearer ey12345abcdef'",
                    In = ParameterLocation.Header,
                    BearerFormat = "JWT",
                    Type = SecuritySchemeType.ApiKey,
                    Scheme = "Bearer"
                });

                c.AddSecurityRequirement(new OpenApiSecurityRequirement
                {
                    {
                        new OpenApiSecurityScheme
                        {
                            Reference = new OpenApiReference
                            {
                                Id = "Bearer",
                                Type = ReferenceType.SecurityScheme
                            }
                        }, new string[]{}
                    }
                });

                //var A = $"{Assembly.GetExecutingAssembly().GetName().Name}.xml";
                //var B = Path.Combine(AppContext.BaseDirectory, A);
                //a.IncludeXmlComments(B);
            });

            services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme).AddJwtBearer(a =>
            {
                a.TokenValidationParameters = new TokenValidationParameters
                {
                    ValidateIssuer = true,
                    ValidateAudience = true,
                    ValidateLifetime = true,
                    ValidateIssuerSigningKey = true,
                    ValidAudience = Configuration["Jwt:Audience"],
                    ValidIssuer = Configuration["Jwt:Issuer"],
                    IssuerSigningKey = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(Configuration["Jwt:Key"])),
                    ClockSkew = TimeSpan.Zero
                };
            });
        }

        // This method gets called by the runtime. Use this method to configure the HTTP request pipeline.
        public void Configure(IApplicationBuilder app, IWebHostEnvironment env, MadingAppContext db)
        {
            db.Database.Migrate();

            var x = db.Users.Where(a => a.UserId == new Guid("c12a9d49-73b5-4f1b-9128-abe4094b413c")).FirstOrDefault();
            if (x == null)
            {
                db.Users.Add(new User
                {
                    UserId = new Guid("c12a9d49-73b5-4f1b-9128-abe4094b413c"),
                    Username = "string",
                    Password = Public.encryptMD5("string"),
                    CreatedDate = DateTime.Now,
                    CreatedBy = new Guid(),
                    DateOfBirth = DateTime.Now,
                    FirstName = "Argi",
                    LastName = "Purwanto",
                    Gender = 1,
                    UserImage = "/user-images/default-image.png"
                });
                db.SaveChanges();
            }

            if (env.IsDevelopment())
            {
                app.UseDeveloperExceptionPage();
            }
            else
            {
                app.UseExceptionHandler("/Home/Error");
                // The default HSTS value is 30 days. You may want to change this for production scenarios, see https://aka.ms/aspnetcore-hsts.
                app.UseHsts();
            }
            app.UseHttpsRedirection();
            app.UseStaticFiles();

            app.UseRouting();

            app.UseAuthentication();
            app.UseAuthorization();

            app.UseStaticFiles();

            app.UseSwagger();
            app.UseSwaggerUI(o =>
            {
                o.SwaggerEndpoint("v1/swagger.json", "MadingApp v1");
               // o.RoutePrefix = "";
            });

            app.UseEndpoints(endpoints =>
            {
                endpoints.MapControllerRoute(
                    name: "default",
                    pattern: "{controller=Home}/{action=Index}/{id?}");
            });
        }
    }
}
