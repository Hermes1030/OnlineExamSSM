create table examhistoryinfo
(
    historyId   int auto_increment
        primary key,
    studentId   int not null,
    examPaperId int not null,
    examScore   int null,
    constraint FK_Reference_10
        foreign key (studentId) references studentinfo (studentId),
    constraint FK_Reference_9
        foreign key (examPaperId) references exampaperinfo (examPaperId)
)
    charset = utf8;

