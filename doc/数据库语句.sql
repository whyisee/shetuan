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
INSERT INTO sequence VALUES ('seq_user_id', '100000', '1');
INSERT INTO sequence VALUES ('seq_comm_id', '100000', '1');
INSERT INTO sequence VALUES ('seq_appro_id', '100000', '1');


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
head_pic		varchar(200) DEFAULT NULL,
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


-- 社团表
 CREATE TABLE if not exists tc_comm_community (
comm_id	varchar(20)                   not NULL,
comm_name	varchar(200)              DEFAULT NULL,
create_persion_id	varchar(20)       DEFAULT NULL,
create_persion_name	varchar(200)      DEFAULT NULL,
boss_id	varchar(20)                   DEFAULT NULL,
boss_name	varchar(200)              DEFAULT NULL,
create_date	varchar(20)               DEFAULT NULL,
comm_people_num	varchar(20)           DEFAULT NULL,
comm_class_id	varchar(20)           DEFAULT NULL,
comm_info	varchar(500)              DEFAULT NULL,
comm_pic	varchar(200)              DEFAULT NULL,
comm_special_act	varchar(500)      DEFAULT NULL,
status	varchar(2)                    not NULL DEFAULT '1',
PRIMARY KEY (comm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE  TABLE if not exists tc_comm_class (
        comm_class_id	varchar(20)                       not NULL,
        comm_class_name	varchar(200)                      DEFAULT NULL,
        update_persion_id	varchar(20)                   DEFAULT NULL,
        update_date	varchar(20)                           DEFAULT NULL,
        remark	varchar(200)                              DEFAULT NULL,
        show_order_no int                  DEFAULT 0,
        status	varchar(2)                                DEFAULT NULL,
        PRIMARY KEY (comm_class_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 社团成员关系表
CREATE TABLE if not exists tc_comm_member (
   login_id	    varchar(20)
    ,login_name	    varchar(20)
    ,comm_id	    varchar(20)
    ,comm_name	    varchar(200)
    ,comm_worker_id	varchar(20)
    ,comm_worker	varchar(200)
    ,comm_remark	varchar(500)
    ,comm_person_remark	varchar(500)
    ,create_date  varchar(20)
    ,is_create	varchar(2)
    ,status	varchar(2)
    ,PRIMARY KEY (login_id,comm_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 审核表
CREATE TABLE if not exists tc_flow_appro (
appro_id	varchar(20)
,flow_id varchar(20)
,appro_name	varchar(200)
,create_login_name	varchar(20)
,create_date	varchar(20)
,appro_login_name	varchar(20)
,appro_status	varchar(20)
,appro_info	varchar(200)
,appro_date	varchar(20)
,appro_type	varchar(20)
,status	varchar(2)
,PRIMARY KEY (appro_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 活动表
CREATE TABLE if not exists tc_comm_activity (
activity_id	varchar(20)
,activity_name	varchar(200)
,comm_id	varchar(20)
,comm_name	varchar(200)
,activity_date	varchar(20)
,activity_addr	varchar(200)
,activity_sign_date	varchar(20)
,activity_info	varchar(500)
,activity_persion_num	varchar(20)
,activity_persion_now	varchar(20)
    is_notcomm_can_sign    varchar(2) default '1' null,
    is_notstudent_can_sign varchar(2) default '0' null,
,create_persion_name	varchar(20)
,create_date	varchar(20)
,status	varchar(2)
,PRIMARY KEY (activity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 活动成员关系表
CREATE TABLE if not exists tc_act_member (
login_id	varchar(20)
,login_name	varchar(20)
,activity_id	varchar(20)
,activity_name	varchar(200)
,sign_info	varchar(200)
,create_date	varchar(20)
,status	varchar(2)
,PRIMARY KEY (login_id,activity_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 权限控制
CREATE TABLE if not exists td_b_rolefuncright (
role_code	varchar(20)
,role_name	varchar(20)
,update_persion_id	varchar(20)
,update_persion_name	varchar(20)
,update_date	varchar(20)
,menu_id	varchar(20)
,menu_name	varchar(100)
,menu_link	varchar(100)
,menu_des	varchar(500)
,menu_level varchar(20)
,parent_menu_id varchar(20)
,show_order int
,remark	varchar(500)
,status	varchar(2)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 留言表
CREATE TABLE if not exists tc_acct_message (
msg_id	varchar(20)
,login_name	varchar(20)
,flow_id	varchar(20)
,msg_info	varchar(500)
,msg_date	varchar(20)
,status	varchar(2)
,PRIMARY KEY (msg_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;