#DataBase 언어 : SQL 
#DB 구축 언어를 "DDL"이라고 부른다.(데이터 정의어)
drop database if exists clothingstore; #이미 데이터 베이스가 존재하면 삭제한다. -> 실습(전체 실행)을 원할하게 사용하기 위함임
create database clothingstore; #데이터 베이스의 이름을 clothingstore라고 하고 그리고 그 안에 테이블을 넣어 줄 것임
use clothingstore; #앞으로 하는 쿼리(질문)들은 이 DB에서 작업하겠다.

#user 테이블은 7개의 필드(속성)을 가지고 있음
# tip. 외래키가 없는 테이블부터 작업하는 것이 좋다.
create table user(
    u_no int primary key auto_increment not null,
    u_id varchar(10),
    u_pw varchar(10),
    u_name varchar(10),
    u_price int,
    u_birth date,
    u_img longblob
);

create table Category(
    c_no int primary key auto_increment not null,
    c_name varchar(10)
);

create table SubCategory(
    sb_no int primary key auto_increment not null,
    sb_name varchar(10),
    c_no int,
    foreign key(c_no) references Category(c_no) #외래키 c_no는 Category테이블의 c_no에서 가져온다.
);

create table ProductList(
    # 묶음1
    p_no int primary key auto_increment not null,
    p_name varchar(10),
    p_price varchar(10),
    
    # 묶음2
    sb_no int,
    u_no int, 
    p_s int,
    p_m int,
    p_l int,
    p_xl int,
    p_img longblob,
    
    # 키 선언 
    foreign key(sb_no) references SubCategory(sb_no),
    foreign key(u_no) references user(u_no)
);

create table Purchase(
    pu_no int primary key auto_increment not null,
    pu_date date,
    p_no int,
    u_no int,
    p_s int,
    p_m int,
    p_l int,
    p_xl int,
    
    foreign key(p_no) references ProductList(p_no),
    foreign key(u_no) references user(u_no)
);

create table SaleList(
    sa_no int primary key auto_increment not null,
    start_date date,
    end_date date,
    sa_sale double,
    p_no int,
    foreign key(p_no) references ProductList(p_no)
);

create table shoppingbasket(
    s_no int primary key auto_increment not null,
    p_no int,
    u_no int,
    p_s int,
    p_m int,
    p_l int,
    p_xl int,
    foreign key(p_no) references ProductList(p_no),
    foreign key(u_no) references user(u_no)
);

create table review(
    r_no int primary key auto_increment not null,
    pu_no int,
    r_content varchar(50),
    r_star varchar(10),
    foreign key(pu_no) references Purchase(pu_no)
);

# u_no(자동증가)와 u_img(이미지) 자리에 각각 null을 대입
# user 테이블 데이터 삽입 (생략 없는 방식)
INSERT INTO user VALUES (1, 'user01', 'user01!', '손준서', 67669, '1976-12-18', null);
INSERT INTO user VALUES (2, 'user02', 'user02!', '이예은', 38860, '1982-10-29', null);


select * from user;
