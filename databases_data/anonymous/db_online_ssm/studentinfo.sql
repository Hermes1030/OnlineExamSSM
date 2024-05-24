create table studentinfo
(
    studentId      int auto_increment
        primary key,
    studentName    varchar(32) not null,
    studentAccount varchar(64) not null,
    studentPwd     varchar(32) not null,
    classId        int         not null,
    constraint FK_Reference_3
        foreign key (classId) references classinfo (classId)
)
    charset = utf8;

