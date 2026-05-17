package 학습용1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		new DBsetting();
	}
}

//JAVA에서는 DML(데이터 조작어)를 맞는다. -> CRUD작업이라고 함
/*
Create (생성): 새로운 데이터를 데이터베이스에 저장하는 기능
- SQL 명령어: INSERT

Read (읽기): 저장되어 있는 데이터를 화면에 불러오거나 조회하는 기능
- SQL 명령어: SELECT

Update (수정): 기존에 있던 데이터를 새로운 내용으로 변경하는 기능
- SQL 명령어: UPDATE

Delete (삭제): 필요 없어진 데이터를 완전히 지우는 기능
- SQL 명령어: DELETE
*/


class DBsetting {
	// tip. SQL 연결 관련 라이브러리는 내부 라이브러리에 포함되지 않아서 따로 찾아야한다.
	
	Connection conn; //SQL 서버에 접근할 객체
	Statement stmt; //서버에 접속한 뒤 결과를 받는 등 조작에 접근할 장치
	
	
	public DBsetting() {
		try {
			conn = DriverManager.getConnection("jdbc:mysql://localhost?serverTimezone=UTC&allowLoadLocalInfile=true", "root", "1234"); //접속 url(프로토콜), 접근 권한, 비밀번호
			//url은 지정할게 많다. (서버타임존, 권한 허용 등)
			
			//결과를 보내고 받을 객체를 다루는 부분 = "셔틀"이라고 부름
			stmt = conn.createStatement();
			
			stmt.execute("drop database if exists clothingstore");
			stmt.execute("create database clothingstore");
			stmt.execute("use clothingstore");

			stmt.execute("create table user(u_no int primary key auto_increment not null, u_id varchar(10), u_pw varchar(10), u_name varchar(10), u_price int, u_birth date, u_img longblob);");
			stmt.execute("create table Category(c_no int primary key auto_increment not null, c_name varchar(10));");
			stmt.execute("create table SubCategory(sb_no int primary key auto_increment not null, sb_name varchar(10), c_no int, foreign key(c_no) references Category(c_no));");
			stmt.execute("create table ProductList(p_no int primary key auto_increment not null, p_name varchar(10), p_price int, sb_no int, u_no int, p_s int, p_m int, p_l int, p_xl int, p_img longblob, foreign key(sb_no) references SubCategory(sb_no), foreign key(u_no) references user(u_no));");
			stmt.execute("create table Purchase(pu_no int primary key auto_increment not null, pu_date date, p_no int, u_no int, pu_s int, pu_m int, pu_l int, pu_xl int, foreign key(p_no) references ProductList(p_no), foreign key(u_no) references user(u_no));");
			stmt.execute("create table SaleList(sa_no int primary key auto_increment not null, start_date date, end_date date, sa_sale double, p_no int, foreign key(p_no) references ProductList(p_no));");
			stmt.execute("create table shoppingbasket(s_no int primary key auto_increment not null, p_no int, u_no int, p_s int, p_m int, p_l int, p_xl int, foreign key(p_no) references ProductList(p_no), foreign key(u_no) references user(u_no));");
			stmt.execute("create table review(r_no int primary key auto_increment not null, pu_no int, r_content varchar(50), r_star varchar(10), foreign key(pu_no) references Purchase(pu_no));");
			
			//텍스트 파일을 읽어서 테이블에 채우는 부분
			// tip. 첫 줄은 데이터가 아니기에 넘기고 실질적 데이터 부분만 읽는다. 
			stmt.execute("set global local_infile=1");
			//데이터를 읽어와서 (경로 설정 후 어떤 테이블에 저장할지 결정한다.)
			stmt.execute("load data local infile './datafiles/user.txt' into table user ignore 1 lines");
			stmt.execute("load data local infile './datafiles/Category.txt' into table Category ignore 1 lines");
			stmt.execute("load data local infile './datafiles/SubCategory.txt' into table SubCategory ignore 1 lines");
			stmt.execute("load data local infile './datafiles/ProductList.txt' into table ProductList ignore 1 lines");
			stmt.execute("load data local infile './datafiles/Purchase.txt' into table Purchase ignore 1 lines");
			stmt.execute("load data local infile './datafiles/SaleList.txt' into table SaleList ignore 1 lines");
			stmt.execute("load data local infile './datafiles/ShoppingBasket.txt' into table ShoppingBasket ignore 1 lines");
			stmt.execute("load data local infile './datafiles/Review.txt' into table Review ignore 1 lines");
			
			
			
			JOptionPane.showMessageDialog(null, "성공"); //다이얼로그 메세지 표시
		} catch (SQLException e) { //SQL에서 발생하는 오류를 캐치한다. (그냥 Exception은 모든 오류를 잡음)
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "실패");
		}
	}
	
}

