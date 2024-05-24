create table teacherinfo
(
    teacherId      int auto_increment
        primary key,
    teacherName    varchar(10)   null,
    teacherAccount varchar(10)   null,
    teacherPwd     varchar(10)   null,
    adminPower     int default 0 null,
    isWork         int default 0 null
)
    charset = utf8;

