create table subjectinfo
(
    subjectId    int auto_increment
        primary key,
    subjectName  varchar(500)  not null,
    optionA      varchar(500)  not null,
    optionB      varchar(500)  not null,
    optionC      varchar(500)  not null,
    optionD      varchar(500)  not null,
    rightResult  varchar(500)  not null,
    subjectScore int           not null,
    subjectType  int default 0 null,
    courseId     int           not null,
    gradeId      int           not null,
    subjectEasy  int default 1 null,
    division     int default 0 null,
    constraint FK_Reference_5
        foreign key (courseId) references courseinfo (courseId),
    constraint FK_Reference_6
        foreign key (gradeId) references gradeinfo (gradeId)
)
    charset = utf8;

