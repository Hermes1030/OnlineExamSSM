create table courseinfo
(
    courseId   int auto_increment
        primary key,
    courseName varchar(50)   not null,
    division   int default 0 null,
    gradeId    int           null,
    constraint courseinfo_ibfk_1
        foreign key (gradeId) references gradeinfo (gradeId)
)
    charset = utf8;

create index gradeId
    on courseinfo (gradeId);

