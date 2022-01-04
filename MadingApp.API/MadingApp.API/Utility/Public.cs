using System;
using System.IO;
using System.Linq;
using System.Security.Cryptography;
using System.Text;

namespace MadingApp.API.Utility
{
    public class Public
    {
        public static string TOKEN;
        public static string PATH_IMAGE = Path.GetFullPath(Environment.CurrentDirectory + @"\wwwroot\Images");

        public static string encryptMD5(string password)
        {
            MD5 mD5 = MD5.Create();
            return string.Concat(mD5.ComputeHash(Encoding.UTF8.GetBytes(password)).Select(a => a.ToString("x2")));
        }
    }
}
