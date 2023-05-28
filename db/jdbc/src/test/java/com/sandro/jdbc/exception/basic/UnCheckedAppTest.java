package com.sandro.jdbc.exception.basic;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class UnCheckedAppTest {

    Service service = new Service();

    @Test
    void test() throws Exception {
        assertThatThrownBy(service::logic).isInstanceOf(RuntimeSQLException.class);
    }

    static class Service {
        Repository repository = new Repository();

        public void logic() {
            repository.call();
        }
    }

    static class Repository {
        /**
         * 체크 예외를 런타임 예외로 감싸서 던진다.
         */
        public void call() {
            try {
                runSQL();
            } catch (SQLException e) {
                /**
                 * 예외를 감쌀 때는 꼭 기존 예외를 포함해야 한다.
                 */
                throw new RuntimeSQLException(e);
            }
        }

        private void runSQL() throws SQLException {
            throw new SQLException("ex");
        }
    }

    static class RuntimeSQLException extends RuntimeException {
        public RuntimeSQLException(Throwable cause) {
            super(cause);
        }
    }
}
