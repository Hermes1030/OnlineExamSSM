create table examplaninfo
(
    examPlanId  int auto_increment
        primary key,
    courseId    int      not null,
    classId     int      not null,
    examPaperId int      not null,
    beginTime   datetime not null,
    constraint examplaninfo_ibfk_1
        foreign key (courseId) references courseinfo (courseId),
    constraint examplaninfo_ibfk_2
        foreign key (classId) references classinfo (classId),
    constraint examplaninfo_ibfk_3
        foreign key (examPaperId) references exampaperinfo (examPaperId)
)
    charset = utf8;

create index classId
    on examplaninfo (classId);

create index courseId
    on examplaninfo (courseId);

create index examPaperId
    on examplaninfo (examPaperId);

