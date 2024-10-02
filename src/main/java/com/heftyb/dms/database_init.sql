USE [master]
GO
/****** Object:  Database [dms]    Script Date: 10/2/2024 10:49:21 AM ******/
CREATE DATABASE [dms]
 CONTAINMENT = NONE
 ON  PRIMARY
( NAME = N'dms', FILENAME = N'/var/opt/mssql/data/dms.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON
( NAME = N'dms_log', FILENAME = N'/var/opt/mssql/data/dms_log.ldf' , SIZE = 73728KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT, LEDGER = OFF
GO
ALTER DATABASE [dms] SET COMPATIBILITY_LEVEL = 160
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [dms].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [dms] SET ANSI_NULL_DEFAULT OFF
GO
ALTER DATABASE [dms] SET ANSI_NULLS OFF
GO
ALTER DATABASE [dms] SET ANSI_PADDING OFF
GO
ALTER DATABASE [dms] SET ANSI_WARNINGS OFF
GO
ALTER DATABASE [dms] SET ARITHABORT OFF
GO
ALTER DATABASE [dms] SET AUTO_CLOSE OFF
GO
ALTER DATABASE [dms] SET AUTO_SHRINK OFF
GO
ALTER DATABASE [dms] SET AUTO_UPDATE_STATISTICS ON
GO
ALTER DATABASE [dms] SET CURSOR_CLOSE_ON_COMMIT OFF
GO
ALTER DATABASE [dms] SET CURSOR_DEFAULT  GLOBAL
GO
ALTER DATABASE [dms] SET CONCAT_NULL_YIELDS_NULL OFF
GO
ALTER DATABASE [dms] SET NUMERIC_ROUNDABORT OFF
GO
ALTER DATABASE [dms] SET QUOTED_IDENTIFIER OFF
GO
ALTER DATABASE [dms] SET RECURSIVE_TRIGGERS OFF
GO
ALTER DATABASE [dms] SET  ENABLE_BROKER
GO
ALTER DATABASE [dms] SET AUTO_UPDATE_STATISTICS_ASYNC OFF
GO
ALTER DATABASE [dms] SET DATE_CORRELATION_OPTIMIZATION OFF
GO
ALTER DATABASE [dms] SET TRUSTWORTHY OFF
GO
ALTER DATABASE [dms] SET ALLOW_SNAPSHOT_ISOLATION OFF
GO
ALTER DATABASE [dms] SET PARAMETERIZATION SIMPLE
GO
ALTER DATABASE [dms] SET READ_COMMITTED_SNAPSHOT OFF
GO
ALTER DATABASE [dms] SET HONOR_BROKER_PRIORITY OFF
GO
ALTER DATABASE [dms] SET RECOVERY FULL
GO
ALTER DATABASE [dms] SET  MULTI_USER
GO
ALTER DATABASE [dms] SET PAGE_VERIFY CHECKSUM
GO
ALTER DATABASE [dms] SET DB_CHAINING OFF
GO
ALTER DATABASE [dms] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF )
GO
ALTER DATABASE [dms] SET TARGET_RECOVERY_TIME = 60 SECONDS
GO
ALTER DATABASE [dms] SET DELAYED_DURABILITY = DISABLED
GO
ALTER DATABASE [dms] SET ACCELERATED_DATABASE_RECOVERY = OFF
GO
EXEC sys.sp_db_vardecimal_storage_format N'dms', N'ON'
GO
ALTER DATABASE [dms] SET QUERY_STORE = ON
GO
ALTER DATABASE [dms] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [dms]
GO
USE [dms]
GO

SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[account_transactions](
    [id] [bigint] IDENTITY(100, 1) NOT NULL,
    [date] [date] NULL,
    [amount] [int] NOT NULL,
    [transaction_id] [bigint] NULL,
    [type] [smallint] NULL,
    [account_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[accounts](
    [id] [bigint] IDENTITY(1000, 100) NOT NULL,
    [account_number] [varchar](255) NULL,
    [account_type] [smallint] NULL,
    [name] [varchar](255) NULL,
    [description] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[appointment_blocks](
    [id] [bigint] IDENTITY(1, 1) NOT NULL,
    [start_date_time] [datetime2](6) NULL,
    [duration] [numeric](21, 0) NULL,
    [end_date_time] [datetime2](6) NULL,
    [is_available] [bit] NOT NULL,
    [advisor_id] [bigint] NULL,
    [appointment_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[appointment_concerns](
    [appointment_id] [bigint] NOT NULL,
    [concerns] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[appointments](
    [id] [bigint] IDENTITY(1, 1) NOT NULL,
    [start_date_time] [datetime2](6) NULL,
    [end_date_time] [datetime2](6) NULL,
    [advisor_id] [bigint] NULL,
    [confirmation_code] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [contact_name] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[customers](
    [id] [bigint] IDENTITY(1001, 1) NOT NULL,
    [first_name] [varchar](255) NULL,
    [last_name] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [email] [varchar](255) NULL,
    [address_line1] [varchar](255) NULL,
    [address_line2] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [city] [varchar](255) NULL,
    [state] [varchar](255) NULL,
    [zip] [varchar](255) NULL,
    [plus4] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [contact_name] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[employees](
    [id] [bigint] IDENTITY(100, 1) NOT NULL,
    [first_name] [varchar](255) NULL,
    [last_name] [varchar](255) NULL,
    [preferred_name] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [tax_id] [varchar](255) NULL,
    [job_title] [smallint] NULL,
    [clocked_in] [bit] NOT NULL,
    [is_active] [bit] NOT NULL,
    [job_in_progress] [bit] NOT NULL,
    [hired_date] [datetime2](6) NULL,
    [address_line1] [varchar](255) NULL,
    [address_line2] [varchar](255) NULL,
    [city] [varchar](255) NULL,
    [state] [varchar](255) NULL,
    [zip] [varchar](255) NULL,
    [plus4] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [contact_name] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[federal_taxes](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [is_active] [bit] NOT NULL,
    [rate] [float] NOT NULL,
    [type] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[fees](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [name] [varchar](255) NULL,
    [description] [varchar](255) NULL,
    [fee] [float] NOT NULL,
    [type] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[interest_charges](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [statement_item_id] [bigint] NULL,
    [interest_amount] [float] NOT NULL,
    [previous_bal] [float] NOT NULL,
    [ending_bal] [float] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[invoice_items](
    [quantity] [float] NULL,
    [rate] [float] NULL,
    [total] [float] NULL,
    [invoice_id] [bigint] NOT NULL,
    [description] [varchar](255) NULL,
    [internal_reference_id] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[invoices](
    [id] [bigint] IDENTITY(60000, 1) NOT NULL,
    [date] [date] NULL,
    [invoice_number] [varchar](255) NULL,
    [invoice_status] [smallint] NULL,
    [total] [float] NOT NULL,
    [employee_id] [bigint] NULL,
    [authorizingponumber] [varchar](255) NULL,
    [notes] [varchar](255) NULL,
    [terms_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[job_time_punch_sets](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [date] [date] NULL,
    [employee_id] [bigint] NULL,
    [job_id] [bigint] NULL,
    [in_id] [bigint] NULL,
    [out_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[local_taxes](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [is_active] [bit] NOT NULL,
    [rate] [float] NOT NULL,
    [type] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[paid_time_off](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [date] [date] NULL,
    [employee_id] [bigint] NULL,
    [hours] [float] NOT NULL,
    [approved_by_id] [bigint] NULL,
    [time_sheet_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[part_received_stock](
    [quantity] [int] NULL,
    [received] [date] NULL,
    [unit_cost] [float] NULL,
    [checked_inby_id] [bigint] NULL,
    [part_id] [bigint] NOT NULL,
    [alt_bin] [varchar](255) NULL,
    [shipment_id] [varchar](255) NULL,
    [shipper] [varchar](255) NULL,
    [source] [varchar](255) NULL,
    [supplier_invoice] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[parts](
    [id] [bigint] IDENTITY(4000, 1) NOT NULL,
    [oempart_number] [varchar](255) NULL,
    [part_number] [varchar](255) NULL,
    [description] [varchar](255) NULL,
    [qty] [int] NOT NULL,
    [bin] [varchar](255) NULL,
    [source] [varchar](255) NULL,
    [cost] [real] NOT NULL,
    [in_stock] [bit] NOT NULL,
    [markup] [real] NOT NULL,
    [price] [real] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[password_reset_tokens](
    [id] [bigint] IDENTITY(100015,1) NOT NULL,
    [expiry_date] [datetime2](6) NULL,
    [user_id] [bigint] NOT NULL,
    [token] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    CONSTRAINT [UKf90ivichjaokvmovxpnlm5nin] UNIQUE NONCLUSTERED
(
[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[pay_periods](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [start_date] [date] NULL,
    [end_date] [date] NULL,
    [period] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[payment_terms](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [terms] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[payments](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [customer_id] [bigint] NULL,
    [payment_method] [smallint] NULL,
    [total] [float] NOT NULL,
    [receipt] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[purchase_order_items](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [po_id] [bigint] NOT NULL,
    [total_cost] [float] NOT NULL,
    [description] [varchar](255) NULL,
    [qty] [varchar](255) NULL,
    [unit1] [varchar](255) NULL,
    [unit2] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[purchase_orders](
    [id] [bigint] IDENTITY(8800,1) NOT NULL,
    [date] [date] NULL,
    [from_id] [bigint] NULL,
    [to_id] [bigint] NULL,
    [required_by_date] [varchar](255) NULL,
    [shipping_method] [varchar](255) NULL,
    [approved_by_id] [bigint] NULL,
    [other] [float] NOT NULL,
    [shipping] [float] NOT NULL,
    [sub_total] [float] NOT NULL,
    [total_cost] [float] NOT NULL,
    [notes] [varchar](255) NULL,
    [payment_terms] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [contact_name] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[repair_order_fees](
    [fee_id] [bigint] NOT NULL,
    [repair_order_id] [bigint] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
    [fee_id] ASC,
[repair_order_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[repair_order_job_parts](
    [job_id] [bigint] NOT NULL,
    [part_id] [bigint] NOT NULL,
    [quantity] [int] NOT NULL,
    [unit_price] [float] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
    [job_id] ASC,
[part_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[repair_order_misc_items](
    [repair_order_id] [bigint] NOT NULL,
    [cost] [float] NULL,
    [description] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[repair_orders](
    [id] [bigint] IDENTITY(6000,1) NOT NULL,
    [open_date] [date] NULL,
    [finalized_date] [date] NULL,
    [closed_date] [datetime2](6) NULL,
    [is_active] [bit] NOT NULL,
    [invoice_id] [bigint] NULL,
    [advisor_id] [bigint] NULL,
    [customer_id] [bigint] NULL,
    [vehicle_id] [bigint] NULL,
    [mileage_in] [int] NOT NULL,
    [mileage_out] [int] NOT NULL,
    [priority] [varchar](255) NULL,
    [service_tag] [varchar](255) NULL,
    [status] [smallint] NULL,
    [subtotal] [real] NOT NULL,
    [tax_charge_id] [bigint] NULL,
    [total_amount] [float] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[roles](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [role] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[sale_lead_sale_lead_notes](
    [date] [date] NULL,
    [customer_id] [bigint] NULL,
    [employee_id] [bigint] NULL,
    [lead_id] [bigint] NULL,
    [sale_lead_id] [bigint] NOT NULL,
    [note] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[sale_leads](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [date] [date] NULL,
    [employee_id] [bigint] NULL,
    [contact_name] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [message] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[service_menu_items](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [item] [varchar](255) NULL,
    [blocks] [int] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[state_taxes](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [is_active] [bit] NOT NULL,
    [rate] [float] NOT NULL,
    [type] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[statement_status_rates](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [rate] [float] NOT NULL,
    [status] [varchar](255) NULL,
    [payment_term_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,

    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[statements](
    [id] [bigint] IDENTITY(1000,1) NOT NULL,
    [date] [date] NULL,
    [customer_id] [bigint] NULL,
    [vendor_id] [bigint] NULL,
    [amount] [float] NOT NULL,
    [total] [float] NOT NULL,
    [invoice_date] [datetime2](6) NULL,
    [invoice_id] [bigint] NULL,
    [invoice_number] [varchar](255) NULL,
    [statement_id] [bigint] NULL,
    [description] [varchar](255) NULL,
    [notes] [varchar](255) NULL,
    [po_number] [varchar](255) NULL,
    [status] [varchar](255) NULL,
    [terms] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[tax_charges](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [purchase_order] [bigint] NULL,
    [repair_order] [bigint] NULL,
    [total_tax] [float] NOT NULL,
    [type] [smallint] NULL,
    [federal_taxes] [bigint] NULL,
    [state_taxes] [bigint] NULL,
    [local_taxes] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[technician_flat_rate_hours](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [job_id] [bigint] NULL,
    [technician_id] [bigint] NULL,
    [flat_rate_hours] [float] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[time_clock_punch_sets](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [date] [date] NULL,
    [employee_id] [bigint] NULL,
    [in_id] [bigint] NULL,
    [out_id] [bigint] NULL,
    [pay_period_id] [bigint] NULL,
    [time_sheet_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[time_sheets](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [total_hours] [float] NOT NULL,
    [pay_period_id] [bigint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[time_punch_in](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [employee_id] [bigint] NULL,
    [time] [datetime2](6) NULL,
    [code] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[time_punch_out](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [employee_id] [bigint] NULL,
    [time] [datetime2](6) NULL,
    [code] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[transactions](
    [id] [bigint] IDENTITY(10000, 1) NOT NULL,
    [date] [datetime2](6) NULL,
    [amount] [int] NOT NULL,
    [payment_method] [smallint] NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[user_roles](
    [role_id] [bigint] NOT NULL,
    [user_id] [bigint] NOT NULL,
     PRIMARY KEY CLUSTERED
    (
    [role_id] ASC,
[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[users](
    [id] [bigint] IDENTITY(100,1) NOT NULL,
    [username] [varchar](255) NOT NULL,
    [password] [varchar](255) NULL,
    [email] [varchar](255) NULL,
    [enabled] [bit] NOT NULL,
    [employee_id] [bigint] NULL,
    [display_photourl] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    CONSTRAINT [UKr43af9ap4edm43mmtq01oddj6] UNIQUE NONCLUSTERED
(
[username] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[vehicles](
    [id] [bigint] IDENTITY(1001,1) NOT NULL,
    [vin] [varchar](255) NULL,
    [customer_id] [bigint] NULL,
    [model_year] [int] NOT NULL,
    [make] [varchar](255) NULL,
    [model] [varchar](255) NULL,
    [trim] [varchar](255) NULL,
    [trim2] [varchar](255) NULL,
    [base_price] [varchar](255) NULL,
    [body_class] [varchar](255) NULL,
    [color] [varchar](255) NULL,
    [displacementl] [varchar](255) NULL,
    [doors] [varchar](255) NULL,
    [drive_type] [varchar](255) NULL,
    [engine_manufacturer] [varchar](255) NULL,
    [engine_model] [varchar](255) NULL,
    [engine_type] [varchar](255) NULL,
    [fuel_type] [varchar](255) NULL,
    [vehicle_type] [varchar](255) NULL,
    [manufacturer] [varchar](255) NULL,
    [plant_city] [varchar](255) NULL,
    [plant_company_name] [varchar](255) NULL,
    [plant_state] [varchar](255) NULL,
    [transmission_style] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[vendors](
    [id] [bigint] IDENTITY(7001,1) NOT NULL,
    [company_name] [varchar](255) NULL,
    [primary_phone_ext] [varchar](255) NULL,
    [primary_phone_number] [varchar](255) NULL,
    [email] [varchar](255) NULL,
    [tax_id] [varchar](255) NULL,
    [payment_method] [varchar](255) NULL,
    [address_line1] [varchar](255) NULL,
    [address_line2] [varchar](255) NULL,
    [city] [varchar](255) NULL,
    [state] [varchar](255) NULL,
    [zip] [varchar](255) NULL,
    [plus4] [varchar](255) NULL,
    [alt1_phone_number] [varchar](255) NULL,
    [alt1_phone_ext] [varchar](255) NULL,
    [alt2_phone_number] [varchar](255) NULL,
    [alt2_phone_ext] [varchar](255) NULL,
    [fax_phone_number] [varchar](255) NULL,
    [fax_phone_ext] [varchar](255) NULL,
    [contact_name] [varchar](255) NULL,
    [contact_email] [varchar](255) NULL,
    [contact_address_line_1] [varchar](255) NULL,
    [contact_address_line_2] [varchar](255) NULL,
    [contact_city] [varchar](255) NULL,
    [contact_state] [varchar](255) NULL,
    [contact_zip] [varchar](255) NULL,
    [contact_zip+4] [varchar](255) NULL,
    [contact_information_notes] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[verification_tokens](
    [id] [bigint] IDENTITY(5050,1) NOT NULL,
    [token] [varchar](255) NULL,
    [expiry_date] [datetime2](6) NULL,
    [user_id] [bigint] NOT NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL,
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
    CONSTRAINT [UKq6jibbenp7o9v6tq178xg88hg] UNIQUE NONCLUSTERED
(
[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[work_order_job_misc_items](
    [work_order_job_id] [bigint] NOT NULL,
    [cost] [float] NULL,
    [description] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL
    ) ON [PRIMARY]
    GO

    SET ANSI_NULLS ON
    GO
    SET QUOTED_IDENTIFIER ON
    GO
CREATE TABLE [dbo].[work_order_jobs](
    [id] [bigint] IDENTITY(1,1) NOT NULL,
    [status] [smallint] NULL,
    [repair_order_id] [bigint] NULL,
    [cause] [varchar](255) NULL,
    [concern] [varchar](255) NULL,
    [correction] [varchar](255) NULL,
    [created_date] [datetime2](6) NULL,
    [created_by] [varchar](255) NULL,
    [last_modified_date] [datetime2](6) NULL,
    [last_modified_by] [varchar](255) NULL
    PRIMARY KEY CLUSTERED
(
[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
    ) ON [PRIMARY]
    GO
/****** Object:  Index [UKdifluqquw4sivknkqq46thpxs]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKdifluqquw4sivknkqq46thpxs] ON [dbo].[job_time_punch_sets]
(
	[in_id] ASC
)
WHERE ([in_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKhebs5rm02daarr073wb33p9kg]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKhebs5rm02daarr073wb33p9kg] ON [dbo].[job_time_punch_sets]
(
	[out_id] ASC
)
WHERE ([out_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UK5m7nul9v2rjux7yl7w3wiw39m]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK5m7nul9v2rjux7yl7w3wiw39m] ON [dbo].[repair_orders]
(
	[invoice_id] ASC
)
WHERE ([invoice_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKecvlhbpwhvub2ybj9wl1rchd6]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKecvlhbpwhvub2ybj9wl1rchd6] ON [dbo].[repair_orders]
(
	[tax_charge_id] ASC
)
WHERE ([tax_charge_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UK9j1d07dnw54nholv4jfbshl1k]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK9j1d07dnw54nholv4jfbshl1k] ON [dbo].[tax_charges]
(
	[purchase_order] ASC
)
WHERE ([purchase_order] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKjohrcueygaa8fcsxbs66kpyrp]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKjohrcueygaa8fcsxbs66kpyrp] ON [dbo].[tax_charges]
(
	[repair_order] ASC
)
WHERE ([repair_order] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKf45b85rvy22s1yf2vi6b3kbf8]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKf45b85rvy22s1yf2vi6b3kbf8] ON [dbo].[time_clock_punch_sets]
(
	[in_id] ASC
)
WHERE ([in_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKodpp9u4dnhye4gyu0mk9wl7e6]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKodpp9u4dnhye4gyu0mk9wl7e6] ON [dbo].[time_clock_punch_sets]
(
	[out_id] ASC
)
WHERE ([out_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
/****** Object:  Index [UKd1s31g1a7ilra77m65xmka3ei]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UKd1s31g1a7ilra77m65xmka3ei] ON [dbo].[users]
(
	[employee_id] ASC
)
WHERE ([employee_id] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
SET ANSI_PADDING ON
GO
/****** Object:  Index [UK6brka0b8j7n06xd43x37hlvlt]    Script Date: 10/2/2024 10:49:22 AM ******/
CREATE UNIQUE NONCLUSTERED INDEX [UK6brka0b8j7n06xd43x37hlvlt] ON [dbo].[vehicles]
(
	[vin] ASC
)
WHERE ([vin] IS NOT NULL)
WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, IGNORE_DUP_KEY = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
GO
ALTER TABLE [dbo].[account_transactions]  WITH CHECK ADD  CONSTRAINT [FK1n6ys6f08bm5km34nlb09fs2y] FOREIGN KEY([account_id])
    REFERENCES [dbo].[accounts] ([id])
    GO
ALTER TABLE [dbo].[account_transactions] CHECK CONSTRAINT [FK1n6ys6f08bm5km34nlb09fs2y]
    GO
ALTER TABLE [dbo].[account_transactions]  WITH CHECK ADD  CONSTRAINT [FKfdtdj5mpy67vbl8qcp2e1a03] FOREIGN KEY([transaction_id])
    REFERENCES [dbo].[transactions] ([id])
    GO
ALTER TABLE [dbo].[account_transactions] CHECK CONSTRAINT [FKfdtdj5mpy67vbl8qcp2e1a03]
    GO
ALTER TABLE [dbo].[appointment_blocks]  WITH CHECK ADD  CONSTRAINT [FK70c45pn5rtqf8sg33rfkwmulk] FOREIGN KEY([appointment_id])
    REFERENCES [dbo].[appointments] ([id])
    GO
ALTER TABLE [dbo].[appointment_blocks] CHECK CONSTRAINT [FK70c45pn5rtqf8sg33rfkwmulk]
    GO
ALTER TABLE [dbo].[appointment_blocks]  WITH CHECK ADD  CONSTRAINT [FK8g9jie0i9giimc8256uhsyxl6] FOREIGN KEY([advisor_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[appointment_blocks] CHECK CONSTRAINT [FK8g9jie0i9giimc8256uhsyxl6]
    GO
ALTER TABLE [dbo].[appointment_concerns]  WITH CHECK ADD  CONSTRAINT [FK3021vc7xx46b3wt5gfjd7oue9] FOREIGN KEY([appointment_id])
    REFERENCES [dbo].[appointments] ([id])
    GO
ALTER TABLE [dbo].[appointment_concerns] CHECK CONSTRAINT [FK3021vc7xx46b3wt5gfjd7oue9]
    GO
ALTER TABLE [dbo].[appointments]  WITH CHECK ADD  CONSTRAINT [FKn9racn1ll3pokkhjopmwfd0xw] FOREIGN KEY([advisor_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[appointments] CHECK CONSTRAINT [FKn9racn1ll3pokkhjopmwfd0xw]
    GO
ALTER TABLE [dbo].[interest_charges]  WITH CHECK ADD  CONSTRAINT [FKms0vs6ntaxdt3bpdu53mlg3x5] FOREIGN KEY([statement_item_id])
    REFERENCES [dbo].[statements] ([id])
    GO
ALTER TABLE [dbo].[interest_charges] CHECK CONSTRAINT [FKms0vs6ntaxdt3bpdu53mlg3x5]
    GO
ALTER TABLE [dbo].[invoice_items]  WITH CHECK ADD  CONSTRAINT [FK46ae0lhu1oqs7cv91fn6y9n7w] FOREIGN KEY([invoice_id])
    REFERENCES [dbo].[invoices] ([id])
    GO
ALTER TABLE [dbo].[invoice_items] CHECK CONSTRAINT [FK46ae0lhu1oqs7cv91fn6y9n7w]
    GO
ALTER TABLE [dbo].[invoices]  WITH CHECK ADD  CONSTRAINT [FK7jvkv15u5k6nh4y3g0g725ol4] FOREIGN KEY([terms_id])
    REFERENCES [dbo].[payment_terms] ([id])
    GO
ALTER TABLE [dbo].[invoices] CHECK CONSTRAINT [FK7jvkv15u5k6nh4y3g0g725ol4]
    GO
ALTER TABLE [dbo].[invoices]  WITH CHECK ADD  CONSTRAINT [FKc32tm1bml7yii031771flnvdg] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[invoices] CHECK CONSTRAINT [FKc32tm1bml7yii031771flnvdg]
    GO
ALTER TABLE [dbo].[job_time_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKefaje5iva01029xk3me4xr5dk] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[job_time_punch_sets] CHECK CONSTRAINT [FKefaje5iva01029xk3me4xr5dk]
    GO
ALTER TABLE [dbo].[job_time_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKglrmxg3lrcq9rt5s2ibk4cnoi] FOREIGN KEY([job_id])
    REFERENCES [dbo].[work_order_jobs] ([id])
    GO
ALTER TABLE [dbo].[job_time_punch_sets] CHECK CONSTRAINT [FKglrmxg3lrcq9rt5s2ibk4cnoi]
    GO
ALTER TABLE [dbo].[job_time_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKle4q93y96s3dc1rav89lrypv8] FOREIGN KEY([in_id])
    REFERENCES [dbo].[time_punch_in] ([id])
    GO
ALTER TABLE [dbo].[job_time_punch_sets] CHECK CONSTRAINT [FKle4q93y96s3dc1rav89lrypv8]
    GO
ALTER TABLE [dbo].[job_time_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKoxj1rkxawmnwrc0t9yqruk6uk] FOREIGN KEY([out_id])
    REFERENCES [dbo].[time_punch_out] ([id])
    GO
ALTER TABLE [dbo].[job_time_punch_sets] CHECK CONSTRAINT [FKoxj1rkxawmnwrc0t9yqruk6uk]
    GO
ALTER TABLE [dbo].[paid_time_off]  WITH CHECK ADD  CONSTRAINT [FKkggv7bwfujf4l8jqan8xnuqfs] FOREIGN KEY([time_sheet_id])
    REFERENCES [dbo].[time_sheets] ([id])
    GO
ALTER TABLE [dbo].[paid_time_off] CHECK CONSTRAINT [FKkggv7bwfujf4l8jqan8xnuqfs]
    GO
ALTER TABLE [dbo].[paid_time_off]  WITH CHECK ADD  CONSTRAINT [FKorlk7q0eiqs1h6l5kspmju2xk] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[paid_time_off] CHECK CONSTRAINT [FKorlk7q0eiqs1h6l5kspmju2xk]
    GO
ALTER TABLE [dbo].[paid_time_off]  WITH CHECK ADD  CONSTRAINT [FKqaod962kc720j56b8ow994nap] FOREIGN KEY([approved_by_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[paid_time_off] CHECK CONSTRAINT [FKqaod962kc720j56b8ow994nap]
    GO
ALTER TABLE [dbo].[part_received_stock]  WITH CHECK ADD  CONSTRAINT [FK77puo8b3f92vmk1vlowl7yeyf] FOREIGN KEY([part_id])
    REFERENCES [dbo].[parts] ([id])
    GO
ALTER TABLE [dbo].[part_received_stock] CHECK CONSTRAINT [FK77puo8b3f92vmk1vlowl7yeyf]
    GO
ALTER TABLE [dbo].[part_received_stock]  WITH CHECK ADD  CONSTRAINT [FKe2ftmdtu8r9slbbug60gl13ba] FOREIGN KEY([checked_inby_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[part_received_stock] CHECK CONSTRAINT [FKe2ftmdtu8r9slbbug60gl13ba]
    GO
ALTER TABLE [dbo].[password_reset_tokens]  WITH CHECK ADD  CONSTRAINT [FK83nsrttkwkb6ym0anu051mtxn] FOREIGN KEY([user_id])
    REFERENCES [dbo].[users] ([id])
    GO
ALTER TABLE [dbo].[password_reset_tokens] CHECK CONSTRAINT [FK83nsrttkwkb6ym0anu051mtxn]
    GO
ALTER TABLE [dbo].[payments]  WITH CHECK ADD  CONSTRAINT [FK45dp0030s8e3myd8n6ky4e79g] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customers] ([id])
    GO
ALTER TABLE [dbo].[payments] CHECK CONSTRAINT [FK45dp0030s8e3myd8n6ky4e79g]
    GO
ALTER TABLE [dbo].[purchase_order_items]  WITH CHECK ADD  CONSTRAINT [FK5vdtjfmcm7cc2odplqokkguci] FOREIGN KEY([po_id])
    REFERENCES [dbo].[purchase_orders] ([id])
    GO
ALTER TABLE [dbo].[purchase_order_items] CHECK CONSTRAINT [FK5vdtjfmcm7cc2odplqokkguci]
    GO
ALTER TABLE [dbo].[purchase_orders]  WITH CHECK ADD  CONSTRAINT [FK962hbto8mg4l1bkocixxudsx2] FOREIGN KEY([to_id])
    REFERENCES [dbo].[vendors] ([id])
    GO
ALTER TABLE [dbo].[purchase_orders] CHECK CONSTRAINT [FK962hbto8mg4l1bkocixxudsx2]
    GO
ALTER TABLE [dbo].[purchase_orders]  WITH CHECK ADD  CONSTRAINT [FKgh7beh0qw0a1u13na4rl7ohvp] FOREIGN KEY([approved_by_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[purchase_orders] CHECK CONSTRAINT [FKgh7beh0qw0a1u13na4rl7ohvp]
    GO
ALTER TABLE [dbo].[purchase_orders]  WITH CHECK ADD  CONSTRAINT [FKp3skaf6e4n5kr7hkrwafthqyp] FOREIGN KEY([from_id])
    REFERENCES [dbo].[vendors] ([id])
    GO
ALTER TABLE [dbo].[purchase_orders] CHECK CONSTRAINT [FKp3skaf6e4n5kr7hkrwafthqyp]
    GO
ALTER TABLE [dbo].[repair_order_fees]  WITH CHECK ADD  CONSTRAINT [FK7qwn2j2f2whbotp01lyxsew1g] FOREIGN KEY([fee_id])
    REFERENCES [dbo].[fees] ([id])
    GO
ALTER TABLE [dbo].[repair_order_fees] CHECK CONSTRAINT [FK7qwn2j2f2whbotp01lyxsew1g]
    GO
ALTER TABLE [dbo].[repair_order_fees]  WITH CHECK ADD  CONSTRAINT [FKaq9r0c0xwjd2p13pn0un6gvrx] FOREIGN KEY([repair_order_id])
    REFERENCES [dbo].[repair_orders] ([id])
    GO
ALTER TABLE [dbo].[repair_order_fees] CHECK CONSTRAINT [FKaq9r0c0xwjd2p13pn0un6gvrx]
    GO
ALTER TABLE [dbo].[repair_order_job_parts]  WITH CHECK ADD  CONSTRAINT [FKddldl1435vppbcuxno99vhekq] FOREIGN KEY([job_id])
    REFERENCES [dbo].[work_order_jobs] ([id])
    GO
ALTER TABLE [dbo].[repair_order_job_parts] CHECK CONSTRAINT [FKddldl1435vppbcuxno99vhekq]
    GO
ALTER TABLE [dbo].[repair_order_job_parts]  WITH CHECK ADD  CONSTRAINT [FKoveqtofr9yjlqd2nc5hhs1hbb] FOREIGN KEY([part_id])
    REFERENCES [dbo].[parts] ([id])
    GO
ALTER TABLE [dbo].[repair_order_job_parts] CHECK CONSTRAINT [FKoveqtofr9yjlqd2nc5hhs1hbb]
    GO
ALTER TABLE [dbo].[repair_order_misc_items]  WITH CHECK ADD  CONSTRAINT [FKaka0e8w7ca2rhvk4ungr1oys2] FOREIGN KEY([repair_order_id])
    REFERENCES [dbo].[repair_orders] ([id])
    GO
ALTER TABLE [dbo].[repair_order_misc_items] CHECK CONSTRAINT [FKaka0e8w7ca2rhvk4ungr1oys2]
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD  CONSTRAINT [FK5q2cm57vpt511711pnu65fkd3] FOREIGN KEY([advisor_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[repair_orders] CHECK CONSTRAINT [FK5q2cm57vpt511711pnu65fkd3]
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD  CONSTRAINT [FK67970vdrm41a7gylephv00xqh] FOREIGN KEY([tax_charge_id])
    REFERENCES [dbo].[tax_charges] ([id])
    GO
ALTER TABLE [dbo].[repair_orders] CHECK CONSTRAINT [FK67970vdrm41a7gylephv00xqh]
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD  CONSTRAINT [FK9m04dl6jgcfxmj8jt9bw5a3s9] FOREIGN KEY([vehicle_id])
    REFERENCES [dbo].[vehicles] ([id])
    GO
ALTER TABLE [dbo].[repair_orders] CHECK CONSTRAINT [FK9m04dl6jgcfxmj8jt9bw5a3s9]
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD  CONSTRAINT [FKea3drngeg2vy15hhoygw2dg71] FOREIGN KEY([invoice_id])
    REFERENCES [dbo].[invoices] ([id])
    GO
ALTER TABLE [dbo].[repair_orders] CHECK CONSTRAINT [FKea3drngeg2vy15hhoygw2dg71]
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD  CONSTRAINT [FKg08stocfm3cdo1lka4wc6elk6] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customers] ([id])
    GO
ALTER TABLE [dbo].[repair_orders] CHECK CONSTRAINT [FKg08stocfm3cdo1lka4wc6elk6]
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes]  WITH CHECK ADD  CONSTRAINT [FK5hnrioosvtkn0vrnnel0djioh] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes] CHECK CONSTRAINT [FK5hnrioosvtkn0vrnnel0djioh]
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes]  WITH CHECK ADD  CONSTRAINT [FK7g97rc2kng8qyofcuxuul46gk] FOREIGN KEY([lead_id])
    REFERENCES [dbo].[sale_leads] ([id])
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes] CHECK CONSTRAINT [FK7g97rc2kng8qyofcuxuul46gk]
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes]  WITH CHECK ADD  CONSTRAINT [FKikpqqsrw9srqo47h6xslbkqx0] FOREIGN KEY([sale_lead_id])
    REFERENCES [dbo].[sale_leads] ([id])
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes] CHECK CONSTRAINT [FKikpqqsrw9srqo47h6xslbkqx0]
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes]  WITH CHECK ADD  CONSTRAINT [FKok7b3wxk4f21y0aq0ecvedv7d] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customers] ([id])
    GO
ALTER TABLE [dbo].[sale_lead_sale_lead_notes] CHECK CONSTRAINT [FKok7b3wxk4f21y0aq0ecvedv7d]
    GO
ALTER TABLE [dbo].[sale_leads]  WITH CHECK ADD  CONSTRAINT [FK5jbhdnoeey76s4oul6ih27ogy] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[sale_leads] CHECK CONSTRAINT [FK5jbhdnoeey76s4oul6ih27ogy]
    GO
ALTER TABLE [dbo].[statement_status_rates]  WITH CHECK ADD  CONSTRAINT [FKfx355c1v6fnqxpj4pusybjy3p] FOREIGN KEY([payment_term_id])
    REFERENCES [dbo].[payment_terms] ([id])
    GO
ALTER TABLE [dbo].[statement_status_rates] CHECK CONSTRAINT [FKfx355c1v6fnqxpj4pusybjy3p]
    GO
ALTER TABLE [dbo].[statements]  WITH CHECK ADD  CONSTRAINT [FK4s1995u4i0vwrjwlcqmkrihgv] FOREIGN KEY([vendor_id])
    REFERENCES [dbo].[vendors] ([id])
    GO
ALTER TABLE [dbo].[statements] CHECK CONSTRAINT [FK4s1995u4i0vwrjwlcqmkrihgv]
    GO
ALTER TABLE [dbo].[statements]  WITH CHECK ADD  CONSTRAINT [FK9jjh5e3jymcxtbi0pkcem48ax] FOREIGN KEY([invoice_id])
    REFERENCES [dbo].[invoices] ([id])
    GO
ALTER TABLE [dbo].[statements] CHECK CONSTRAINT [FK9jjh5e3jymcxtbi0pkcem48ax]
    GO
ALTER TABLE [dbo].[statements]  WITH CHECK ADD  CONSTRAINT [FKcik4p1lai3hdefcfg1aqkjohh] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customers] ([id])
    GO
ALTER TABLE [dbo].[statements] CHECK CONSTRAINT [FKcik4p1lai3hdefcfg1aqkjohh]
    GO
ALTER TABLE [dbo].[statements]  WITH CHECK ADD  CONSTRAINT [FKfjxl99hmq01tqwuxx6r2gefaw] FOREIGN KEY([statement_id])
    REFERENCES [dbo].[statements] ([id])
    GO
ALTER TABLE [dbo].[statements] CHECK CONSTRAINT [FKfjxl99hmq01tqwuxx6r2gefaw]
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD  CONSTRAINT [FK7u5fgskryxatk5xh5tgcjjo21] FOREIGN KEY([state_taxes])
    REFERENCES [dbo].[state_taxes] ([id])
    GO
ALTER TABLE [dbo].[tax_charges] CHECK CONSTRAINT [FK7u5fgskryxatk5xh5tgcjjo21]
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD  CONSTRAINT [FKcssl11vnxmxh1b6k1xyw2m2kb] FOREIGN KEY([local_taxes])
    REFERENCES [dbo].[local_taxes] ([id])
    GO
ALTER TABLE [dbo].[tax_charges] CHECK CONSTRAINT [FKcssl11vnxmxh1b6k1xyw2m2kb]
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD  CONSTRAINT [FKj73uw8r76cy3ulfk90cu5pmwb] FOREIGN KEY([repair_order])
    REFERENCES [dbo].[repair_orders] ([id])
    GO
ALTER TABLE [dbo].[tax_charges] CHECK CONSTRAINT [FKj73uw8r76cy3ulfk90cu5pmwb]
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD  CONSTRAINT [FKk7ipoaabk9nfkrsw4ky09k3in] FOREIGN KEY([federal_taxes])
    REFERENCES [dbo].[federal_taxes] ([id])
    GO
ALTER TABLE [dbo].[tax_charges] CHECK CONSTRAINT [FKk7ipoaabk9nfkrsw4ky09k3in]
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD  CONSTRAINT [FKnjmhu99vq4r0xq2k3d1yueuyg] FOREIGN KEY([purchase_order])
    REFERENCES [dbo].[purchase_orders] ([id])
    GO
ALTER TABLE [dbo].[tax_charges] CHECK CONSTRAINT [FKnjmhu99vq4r0xq2k3d1yueuyg]
    GO
ALTER TABLE [dbo].[technician_flat_rate_hours]  WITH CHECK ADD  CONSTRAINT [FKaed526lwinud7wq4llklrvme] FOREIGN KEY([technician_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[technician_flat_rate_hours] CHECK CONSTRAINT [FKaed526lwinud7wq4llklrvme]
    GO
ALTER TABLE [dbo].[technician_flat_rate_hours]  WITH CHECK ADD  CONSTRAINT [FKci9mbqlumgtl36anv7l5nhvpo] FOREIGN KEY([job_id])
    REFERENCES [dbo].[work_order_jobs] ([id])
    GO
ALTER TABLE [dbo].[technician_flat_rate_hours] CHECK CONSTRAINT [FKci9mbqlumgtl36anv7l5nhvpo]
    GO
ALTER TABLE [dbo].[time_clock_punch_sets]  WITH CHECK ADD  CONSTRAINT [FK1p9nblc19awqoexfap7cvobgu] FOREIGN KEY([in_id])
    REFERENCES [dbo].[time_punch_in] ([id])
    GO
ALTER TABLE [dbo].[time_clock_punch_sets] CHECK CONSTRAINT [FK1p9nblc19awqoexfap7cvobgu]
    GO
ALTER TABLE [dbo].[time_clock_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKco6rl793goyqvtcfjegyjgsa9] FOREIGN KEY([pay_period_id])
    REFERENCES [dbo].[pay_periods] ([id])
    GO
ALTER TABLE [dbo].[time_clock_punch_sets] CHECK CONSTRAINT [FKco6rl793goyqvtcfjegyjgsa9]
    GO
ALTER TABLE [dbo].[time_clock_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKom00of28hjfjp07tpnq8mu0mo] FOREIGN KEY([time_sheet_id])
    REFERENCES [dbo].[time_sheets] ([id])
    GO
ALTER TABLE [dbo].[time_clock_punch_sets] CHECK CONSTRAINT [FKom00of28hjfjp07tpnq8mu0mo]
    GO
ALTER TABLE [dbo].[time_clock_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKopbpg70payl85ndyfpil797jb] FOREIGN KEY([out_id])
    REFERENCES [dbo].[time_punch_out] ([id])
    GO
ALTER TABLE [dbo].[time_clock_punch_sets] CHECK CONSTRAINT [FKopbpg70payl85ndyfpil797jb]
    GO
ALTER TABLE [dbo].[time_clock_punch_sets]  WITH CHECK ADD  CONSTRAINT [FKpljhk6tab8vxkykp7j4v62wc2] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[time_clock_punch_sets] CHECK CONSTRAINT [FKpljhk6tab8vxkykp7j4v62wc2]
    GO
ALTER TABLE [dbo].[time_sheets]  WITH CHECK ADD  CONSTRAINT [FKmilmlbo3dgvqlngrj6shedi4d] FOREIGN KEY([pay_period_id])
    REFERENCES [dbo].[pay_periods] ([id])
    GO
ALTER TABLE [dbo].[time_sheets] CHECK CONSTRAINT [FKmilmlbo3dgvqlngrj6shedi4d]
    GO
ALTER TABLE [dbo].[time_punch_in]  WITH CHECK ADD  CONSTRAINT [FK2rhsknkknhv0pg23ty01jqm92] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[time_punch_in] CHECK CONSTRAINT [FK2rhsknkknhv0pg23ty01jqm92]
    GO
ALTER TABLE [dbo].[time_punch_out]  WITH CHECK ADD  CONSTRAINT [FKr34lsaj6mjswgca02942evxjm] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[time_punch_out] CHECK CONSTRAINT [FKr34lsaj6mjswgca02942evxjm]
    GO
ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [FKh8ciramu9cc9q3qcqiv4ue8a6] FOREIGN KEY([role_id])
    REFERENCES [dbo].[roles] ([id])
    GO
ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [FKh8ciramu9cc9q3qcqiv4ue8a6]
    GO
ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [FKhfh9dx7w3ubf1co1vdev94g3f] FOREIGN KEY([user_id])
    REFERENCES [dbo].[users] ([id])
    GO
ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [FKhfh9dx7w3ubf1co1vdev94g3f]
    GO
ALTER TABLE [dbo].[users]  WITH CHECK ADD  CONSTRAINT [FK6p2ib82uai0pj9yk1iassppgq] FOREIGN KEY([employee_id])
    REFERENCES [dbo].[employees] ([id])
    GO
ALTER TABLE [dbo].[users] CHECK CONSTRAINT [FK6p2ib82uai0pj9yk1iassppgq]
    GO
ALTER TABLE [dbo].[vehicles]  WITH CHECK ADD  CONSTRAINT [FKjrosretvs9ih5ybhpsd5qskc3] FOREIGN KEY([customer_id])
    REFERENCES [dbo].[customers] ([id])
    GO
ALTER TABLE [dbo].[vehicles] CHECK CONSTRAINT [FKjrosretvs9ih5ybhpsd5qskc3]
    GO
ALTER TABLE [dbo].[verification_tokens]  WITH CHECK ADD  CONSTRAINT [FK_VERIFY_USER] FOREIGN KEY([user_id])
    REFERENCES [dbo].[users] ([id])
    GO
ALTER TABLE [dbo].[verification_tokens] CHECK CONSTRAINT [FK_VERIFY_USER]
    GO
ALTER TABLE [dbo].[work_order_job_misc_items]  WITH CHECK ADD  CONSTRAINT [FKlvjd5qa2qywioa10gi67hiuai] FOREIGN KEY([work_order_job_id])
    REFERENCES [dbo].[work_order_jobs] ([id])
    GO
ALTER TABLE [dbo].[work_order_job_misc_items] CHECK CONSTRAINT [FKlvjd5qa2qywioa10gi67hiuai]
    GO
ALTER TABLE [dbo].[work_order_jobs]  WITH CHECK ADD  CONSTRAINT [FKjn81kpct8iivaw1k4crcg40gg] FOREIGN KEY([repair_order_id])
    REFERENCES [dbo].[repair_orders] ([id])
    GO
ALTER TABLE [dbo].[work_order_jobs] CHECK CONSTRAINT [FKjn81kpct8iivaw1k4crcg40gg]
    GO
ALTER TABLE [dbo].[account_transactions]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[accounts]  WITH CHECK ADD CHECK  (([account_type]>=(0) AND [account_type]<=(1)))
    GO
ALTER TABLE [dbo].[employees]  WITH CHECK ADD CHECK  (([job_title]>=(0) AND [job_title]<=(25)))
    GO
ALTER TABLE [dbo].[federal_taxes]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[fees]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[invoices]  WITH CHECK ADD CHECK  (([invoice_status]>=(0) AND [invoice_status]<=(8)))
    GO
ALTER TABLE [dbo].[local_taxes]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[payments]  WITH CHECK ADD CHECK  (([payment_method]>=(0) AND [payment_method]<=(6)))
    GO
ALTER TABLE [dbo].[repair_orders]  WITH CHECK ADD CHECK  (([status]>=(0) AND [status]<=(5)))
    GO
ALTER TABLE [dbo].[state_taxes]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[statement_status_rates]  WITH CHECK ADD CHECK  (([status]='ANNUAL' OR [status]='MONTHLY'))
    GO
ALTER TABLE [dbo].[statements]  WITH CHECK ADD CHECK  (([status]='DELINQUENT' OR [status]='OVER_120' OR [status]='OVER_90' OR [status]='OVER_60' OR [status]='OVER_30' OR [status]='CURRENT'))
    GO
ALTER TABLE [dbo].[tax_charges]  WITH CHECK ADD CHECK  (([type]>=(0) AND [type]<=(1)))
    GO
ALTER TABLE [dbo].[time_punch_in]  WITH CHECK ADD CHECK  (([code]>=(0) AND [code]<=(4)))
    GO
ALTER TABLE [dbo].[time_punch_out]  WITH CHECK ADD CHECK  (([code]>=(0) AND [code]<=(4)))
    GO
ALTER TABLE [dbo].[transactions]  WITH CHECK ADD CHECK  (([payment_method]>=(0) AND [payment_method]<=(6)))
    GO
ALTER TABLE [dbo].[work_order_jobs]  WITH CHECK ADD CHECK  (([status]>=(0) AND [status]<=(5)))
    GO
    USE [master]
    GO
ALTER DATABASE [dms] SET  READ_WRITE
GO