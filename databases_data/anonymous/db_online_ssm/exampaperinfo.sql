create table exampaperinfo
(
    examPaperId    int auto_increment
        primary key,
    examPaperName  varchar(50)   not null,
    subjectNum     int           not null,
    examPaperTime  int           not null,
    examPaperScore int           not null,
    gradeId        int           not null,
    division       int default 0 null,
    examPaperEasy  int default 1 null,
    constraint FK_Reference_4
        foreign key (gradeId) references gradeinfo (gradeId)
)
    charset = utf8;

