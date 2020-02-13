-- 创建数据库
create database if not exists shetuan;
use shetuan;

-- 创建序列
drop table if exists sequence;
create table sequence (
seq_name        VARCHAR(50) NOT NULL, -- 序列名称
current_val     INT         NOT NULL, -- 当前值
increment_val   INT         NOT NULL    DEFAULT 1, -- 步长(跨度)
PRIMARY KEY (seq_name)   );

INSERT INTO sequence VALUES ('seq_test1_num1', '0', '1');
INSERT INTO sequence VALUES ('seq_test1_num2', '100000', '2');

create function currval(v_seq_name VARCHAR(50))
returns integer(11)
begin
 declare value integer;
 set value = 0;
 select current_val into value  from sequence where seq_name = v_seq_name;
   return value;
end;

create function nextval (v_seq_name VARCHAR(50)) returns integer(11)
begin
    update sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;
	return currval(v_seq_name);
end;


-- 创建登录表
CREATE TABLE if not exists tc_acct_login (
login_id	varchar(20) not NULL,
login_name	varchar(20) not NULL,
login_pass	varchar(200) not NULL,
status	    varchar(2)  not NULL DEFAULT '1',
PRIMARY KEY (login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 创建登录角色表
CREATE TABLE if not exists tc_acct_role (
login_id	varchar(20)  not NULL,
login_name	varchar(20)  not NULL,
role_id	    varchar(20)  not NULL,
status	    varchar(2)   not NULL DEFAULT '1',
PRIMARY KEY (login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 创建登录基本信息表
CREATE TABLE if not exists tc_acct_member (
login_id		varchar(20) not NULL,
login_name		varchar(20) not NULL,
user_name		varchar(50) DEFAULT NULL,
student_id		varchar(50) DEFAULT NULL,
sex		        varchar(10) DEFAULT NULL,
in_date		    varchar(10) DEFAULT NULL,
college		    varchar(50) DEFAULT NULL,
specially		varchar(50) DEFAULT NULL,
phone		    varchar(20) DEFAULT NULL,
address		    varchar(200) DEFAULT NULL,
email		    varchar(50) DEFAULT NULL,
create_date		varchar(20) DEFAULT NULL,
is_add_info		varchar(2)  not NULL DEFAULT '0',
status		    varchar(2)  not NULL DEFAULT '1',
PRIMARY KEY (login_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 角色码表
CREATE TABLE if not exists tc_cde_role (
role_id	varchar(10)              not NULL,
role_name	varchar(50)          not NULL,
remark	varchar(200)             DEFAULT NULL,
status	varchar(2)               not NULL DEFAULT '1',
PRIMARY KEY (role_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `activity` (
  `activity_id` varchar(100) DEFAULT NULL,
  `community_id` varchar(100) DEFAULT NULL,
  `activity_name` varchar(100) DEFAULT NULL,
  `community_name` varchar(100) DEFAULT NULL,
  `activity_date` varchar(100) DEFAULT NULL,
  `activity_place` varchar(100) DEFAULT NULL,
  `activity_info` varchar(100) DEFAULT NULL,
  `activity_video` varchar(100) DEFAULT NULL,
  `activity_photo` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `activity`
--

LOCK TABLES `activity` WRITE;
/*!40000 ALTER TABLE `activity` DISABLE KEYS */;
/*!40000 ALTER TABLE `activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `community`
--

DROP TABLE IF EXISTS `community`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `community` (
  `community_id` varchar(100) DEFAULT NULL,
  `community_name` varchar(100) DEFAULT NULL,
  `community_createrid` varchar(100) DEFAULT NULL,
  `community_creatername` varchar(100) DEFAULT NULL,
  `community_createdate` varchar(100) DEFAULT NULL,
  `community_num` varchar(100) DEFAULT NULL,
  `community_range` varchar(100) DEFAULT NULL,
  `community_classid` varchar(100) DEFAULT NULL,
  `community_classname` varchar(100) DEFAULT NULL,
  `community_create` varchar(100) DEFAULT NULL,
  `community_info` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `community`
--

LOCK TABLES `community` WRITE;
/*!40000 ALTER TABLE `community` DISABLE KEYS */;
/*!40000 ALTER TABLE `community` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `communityclass`
--

DROP TABLE IF EXISTS `communityclass`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `communityclass` (
  `class_id` varchar(100) DEFAULT NULL,
  `community_id` varchar(100) DEFAULT NULL,
  `class_name` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `communityclass`
--

LOCK TABLES `communityclass` WRITE;
/*!40000 ALTER TABLE `communityclass` DISABLE KEYS */;
/*!40000 ALTER TABLE `communityclass` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `login`
--

DROP TABLE IF EXISTS `login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `login` (
  `manager_id` varchar(100) DEFAULT NULL,
  `login_name` varchar(100) DEFAULT NULL,
  `login_pass` varchar(100) DEFAULT NULL,
  `login_id` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `login`
--

LOCK TABLES `login` WRITE;
/*!40000 ALTER TABLE `login` DISABLE KEYS */;
INSERT INTO `login` VALUES (NULL,'zoukh','123456',NULL),(NULL,'test','123456',NULL);
/*!40000 ALTER TABLE `login` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `member` (
  `login_name` varchar(100) DEFAULT NULL,
  `manager_id` varchar(100) DEFAULT NULL,
  `member_name` varchar(100) DEFAULT NULL,
  `member_header` varchar(100) DEFAULT NULL,
  `member_gender` varchar(100) DEFAULT NULL,
  `member_grade` varchar(100) DEFAULT NULL,
  `member_institute` varchar(100) DEFAULT NULL,
  `member_add` varchar(100) DEFAULT NULL,
  `member_email` varchar(100) DEFAULT NULL,
  `member_phone` varchar(100) DEFAULT NULL,
  `creat_community` varchar(100) DEFAULT NULL,
  `join_community` varchar(100) DEFAULT NULL,
  `join_communityid` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES ('zoukh',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'907022598@qq.com','17611255080',NULL,NULL,NULL),('test',NULL,NULL,NULL,NULL,NULL,NULL,NULL,'zouhq1206@163.com','13244534536',NULL,NULL,NULL);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-02 10:16:46
