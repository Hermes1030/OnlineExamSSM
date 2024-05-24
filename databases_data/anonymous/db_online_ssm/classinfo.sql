create table classinfo
(
    classId   int auto_increment
        primary key,
    className varchar(50) not null,
    gradeId   int         not null,
    teacherId int         null,
    constraint FK_Reference_1
        foreign key (gradeId) references gradeinfo (gradeId),
    constraint classinfo_ibfk_1
        foreign key (teacherId) references teacherinfo (teacherId)
)
    charset = utf8;

create index teacherId
    on classinfo (teacherId);

