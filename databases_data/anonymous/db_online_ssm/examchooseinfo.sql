create table examchooseinfo
(
    chooseId     int auto_increment
        primary key,
    studentId    int          not null,
    examPaperId  int          not null,
    subjectId    int          not null,
    chooseResult varchar(500) not null,
    constraint FK_Reference_11
        foreign key (studentId) references studentinfo (studentId),
    constraint FK_Reference_12
        foreign key (examPaperId) references exampaperinfo (examPaperId),
    constraint FK_Reference_13
        foreign key (subjectId) references subjectinfo (subjectId)
)
    charset = utf8;

