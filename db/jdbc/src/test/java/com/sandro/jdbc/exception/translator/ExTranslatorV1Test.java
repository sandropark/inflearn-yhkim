package com.sandro.jdbc.exception.translator;

import com.sandro.jdbc.domain.Member;
import com.sandro.jdbc.repository.ex.MyDbException;
import com.sandro.jdbc.repository.ex.MyDuplicateKeyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.support.JdbcUtils;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

@SpringBootTest
class ExTranslatorV1Test {

    @Autowired
    Repository repository;
    @Autowired
    Service service;

    @Test
    void duplicateEx() throws Exception {
        service.create("myId");
        service.create("myId");
    }

    @SpringBootApplication
    static class TestConfig {

        @Autowired
        DataSource dataSource;

        @Bean
        Repository repository() {
            return new Repository(dataSource);
        }

        @Bean
        Service service() {
            return new Service(repository());
        }
    }

    @Slf4j
    @RequiredArgsConstructor
    static class Service {
        private final Repository repository;

        void create(String memberId) {
            try {
                repository.save(new Member(memberId, 0));
                log.info("saveId={}", memberId);
            } catch (MyDuplicateKeyException e) {
                log.info("키 중복, 복구 시도");
                String retryId = generateNewId(memberId);
                log.info("retryId={}", retryId);
                repository.save(new Member(retryId, 0));
            } catch (MyDbException e) {
                log.info("데이터 접근 계층 예외", e);
                throw e;
            }
        }

        private String generateNewId(String memberId) {
            return memberId + new Random().nextInt(10000);
        }
    }

    @RequiredArgsConstructor
    static class Repository {
        private final DataSource dataSource;

        Member save(Member member) {
            String sql = "insert into member(member_id, money) values (?,?)";

            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);
                pstmt.setString(1, member.getMemberId());
                pstmt.setInt(2, member.getMoney());
                pstmt.executeUpdate();
                return member;
            } catch (SQLException e) {
                //h2 DB
                if (e.getErrorCode() == 23505)
                    throw new MyDuplicateKeyException(e);
                throw new MyDbException(e);
            } finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(con);
            }
        }

        @PostConstruct
        public void initTable() {
            String sql = "create table MEMBER (MEMBER_ID CHARACTER VARYING(10) not null primary key, MONEY INTEGER default 0 not null)";

            Connection con = null;
            PreparedStatement pstmt = null;

            try {
                con = dataSource.getConnection();
                pstmt = con.prepareStatement(sql);

                pstmt.executeUpdate();
            } catch (SQLException e) {
                throw new MyDbException(e);
            } finally {
                JdbcUtils.closeStatement(pstmt);
                JdbcUtils.closeConnection(con);

            }
        }
    }
}
