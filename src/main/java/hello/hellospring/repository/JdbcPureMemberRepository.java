package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 순수 JDBC - Java Data Base Connectivity 구현 파일
 */
public class JdbcPureMemberRepository implements MemberRepository{

    private final DataSource dataSource;
    //application.properties에 등록한 정보를 통해 Database에 연결할수있게 해주는 Datasource객체이다.

    public JdbcPureMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; // ?는 파라미터 바인딩을 위한 처리
        Connection conn = null; // sql문을 호출하도록 하는 DB 연결 소켓에 대한 객체
        /*  실제 자바프로그램과 데이터베이스를 네트워크상에서 연결을 해주는 메소드이다.
        연결에 성공하면 DB와 연결 상태를 Connection 객체로 표현하여 반환한다.
         Connection은 아래의 그림과 같이 네트워크상의 연결 자체를 의미한다.
         자바프로그램과 DB사이의 길로 볼 수 있다.
         보통 Connection하나당 트랜잭션 하나를 관리한다.
         트랜잭션은 하나 이상의 쿼리에서 동일한 Connection 객체를 공유하는 것을 뜻한다.
         Mybatis의 SqlSession, Hibernate에 TransactionManager등의 Close가 이루어지면 Connection을 ConnectionPool에 반납하게 된다.
        * */
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, member.getName());
            pstmt.executeUpdate(); // 삽입 수정 삭제 등의 갱신은 executeUpdate를 한다.
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery(); //조회는 executeQuery이다.
            if(rs.next()) { // 결과의 다음행이 존재한다면 실행
                Member member = new Member();
                member.setId(rs.getLong("id")); //id와 name을 세팅해서 반환한다.
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>(); //리스트에 담아서 반환한다.
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        //스프링 프레임워크를 통해서 JDBC를 사용하기 위해서는 DataSourceUtils를 통해서 커넥션 객체를 획득해야한다. 트랜잭션이 걸렸을때 커넥션을 유지하기위함이다.
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        // 커넥션 연결을 닫을때에도 DataSourceUtils를 통해서 닫아야 한다.
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
