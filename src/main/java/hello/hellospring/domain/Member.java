package hello.hellospring.domain;

public class Member {
    private Long id; //System이 정하는 아이디(데이터구분값 예를들어 시퀀스 등의 자동증가값)
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
