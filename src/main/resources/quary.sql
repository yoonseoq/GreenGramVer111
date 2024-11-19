CREATE TABLE USER (
                      user_id BIGINT AUTO_INCREMENT PRIMARY key
    , uid VARCHAR(30) UNIQUE NOT NULL -- 아이디는 유니크하고 누락되지않음
    , upw VARCHAR(100) NOT NULL
    , nick_name VARCHAR(30)
    , pic VARCHAR(70) -- 사진이 아니라 파일명 (null허용)
    , created_at DATETIME NOT NULL DEFAULT current_timestamp
    , updated_at DATETIME ON UPDATE current_timestamp
); -- 제약조건 거는 이유는 잘못된 값이 들어오지 않게 하기 위함

CREATE TABLE feed (
                      feed_id BIGINT AUTO_INCREMENT PRIMARY KEY
    , writer_user_id BIGINT not null  -- fk걸때는 타입이 같아야 한다
    , contents VARCHAR(1000)
    , location VARCHAR(30)
    , created_at DATETIME NOT NULL DEFAULT current_timestamp
    , updated_at DATETIME ON UPDATE current_timestamp
    , FOREIGN KEY (writer_user_id) REFERENCEs user(user_id)
);

CREATE TABLE feed_pics (
                           feed_pic_id BIGINT AUTO_INCREMENT PRIMARY KEY
    , feed_id BIGINT NOT NULL
    , pic VARCHAR(70) NOT null
    , created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
    , FOREIGN KEY (pics_user_id) REFERENCEs user(user_id)
);
