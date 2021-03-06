USE [master]
GO
/****** Object:  Database [MadingApp]    Script Date: 12/27/2021 2:20:06 PM ******/
CREATE DATABASE [MadingApp]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'MadingApp', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.ITSS\MSSQL\DATA\MadingApp.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'MadingApp_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.ITSS\MSSQL\DATA\MadingApp_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [MadingApp] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [MadingApp].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [MadingApp] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [MadingApp] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [MadingApp] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [MadingApp] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [MadingApp] SET ARITHABORT OFF 
GO
ALTER DATABASE [MadingApp] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [MadingApp] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [MadingApp] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [MadingApp] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [MadingApp] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [MadingApp] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [MadingApp] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [MadingApp] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [MadingApp] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [MadingApp] SET  DISABLE_BROKER 
GO
ALTER DATABASE [MadingApp] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [MadingApp] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [MadingApp] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [MadingApp] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [MadingApp] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [MadingApp] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [MadingApp] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [MadingApp] SET RECOVERY FULL 
GO
ALTER DATABASE [MadingApp] SET  MULTI_USER 
GO
ALTER DATABASE [MadingApp] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [MadingApp] SET DB_CHAINING OFF 
GO
ALTER DATABASE [MadingApp] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [MadingApp] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [MadingApp] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [MadingApp] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'MadingApp', N'ON'
GO
ALTER DATABASE [MadingApp] SET QUERY_STORE = OFF
GO
USE [MadingApp]
GO
/****** Object:  Table [dbo].[__EFMigrationsHistory]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[__EFMigrationsHistory](
	[MigrationId] [nvarchar](150) NOT NULL,
	[ProductVersion] [nvarchar](32) NOT NULL,
 CONSTRAINT [PK___EFMigrationsHistory] PRIMARY KEY CLUSTERED 
(
	[MigrationId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[BookMarks]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[BookMarks](
	[BookMarkID] [uniqueidentifier] NOT NULL,
	[UserID] [uniqueidentifier] NULL,
	[MadingID] [uniqueidentifier] NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_BookMarks] PRIMARY KEY CLUSTERED 
(
	[BookMarkID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Categories]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Categories](
	[CategoryID] [uniqueidentifier] NOT NULL,
	[Name] [varchar](50) NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Categories] PRIMARY KEY CLUSTERED 
(
	[CategoryID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Comments]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Comments](
	[CommentID] [uniqueidentifier] NOT NULL,
	[MadingID] [uniqueidentifier] NULL,
	[CommentBy] [uniqueidentifier] NULL,
	[Text] [text] NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Comments] PRIMARY KEY CLUSTERED 
(
	[CommentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Madings]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Madings](
	[MadingID] [uniqueidentifier] NOT NULL,
	[CateogryID] [uniqueidentifier] NULL,
	[Title] [varchar](50) NULL,
	[Description] [varchar](200) NULL,
	[MadingImage] [varchar](2000) NULL,
	[Likes] [bigint] NULL,
	[Dislikes] [bigint] NULL,
	[ReportID] [uniqueidentifier] NULL,
	[ReportCount] [bigint] NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Madings] PRIMARY KEY CLUSTERED 
(
	[MadingID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Reports]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Reports](
	[ReportID] [uniqueidentifier] NOT NULL,
	[Name] [varchar](50) NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Reports] PRIMARY KEY CLUSTERED 
(
	[ReportID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Schools]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Schools](
	[SchoolID] [uniqueidentifier] NOT NULL,
	[Name] [varchar](50) NULL,
	[Address] [varchar](200) NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Schools] PRIMARY KEY CLUSTERED 
(
	[SchoolID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 12/27/2021 2:20:06 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [uniqueidentifier] NOT NULL,
	[SchoolID] [uniqueidentifier] NULL,
	[Username] [varchar](10) NULL,
	[Password] [varchar](100) NULL,
	[FirstName] [varchar](20) NULL,
	[LastName] [varchar](50) NULL,
	[Gender] [int] NULL,
	[DateOfBirth] [date] NULL,
	[UserImage] [varchar](2000) NULL,
	[Token] [nvarchar](max) NULL,
	[CreatedBy] [uniqueidentifier] NULL,
	[CreatedDate] [datetime2](7) NULL,
	[ModifiedBy] [uniqueidentifier] NULL,
	[ModifiedDate] [datetime2](7) NULL,
 CONSTRAINT [PK_Users] PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
INSERT [dbo].[__EFMigrationsHistory] ([MigrationId], [ProductVersion]) VALUES (N'20211227071248_First Migration', N'5.0.13')
GO
INSERT [dbo].[Users] ([UserID], [SchoolID], [Username], [Password], [FirstName], [LastName], [Gender], [DateOfBirth], [UserImage], [Token], [CreatedBy], [CreatedDate], [ModifiedBy], [ModifiedDate]) VALUES (N'c12a9d49-73b5-4f1b-9128-abe4094b413c', NULL, N'string', N'b45cffe084dd3d20d928bee85e7b0f21', NULL, NULL, NULL, NULL, NULL, NULL, NULL, CAST(N'2021-12-27T14:13:28.1821654' AS DateTime2), NULL, NULL)
GO
USE [master]
GO
ALTER DATABASE [MadingApp] SET  READ_WRITE 
GO
