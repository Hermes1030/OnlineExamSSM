create table examsubjectmiddleinfo
(
    esmId       int auto_increment
        primary key,
    examPaperId int not null,
    subjectId   int not null,
    constraint FK_Reference_7
        foreign key (examPaperId) references exampaperinfo (examPaperId),
    constraint FK_Reference_8
        foreign key (subjectId) references subjectinfo (subjectId)
)
    charset = utf8;

