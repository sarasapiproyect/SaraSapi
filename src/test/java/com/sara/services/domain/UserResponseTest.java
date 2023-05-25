//package com.sara.services.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sara.services.web.rest.TestUtil;
//import org.junit.jupiter.api.Test;
//
//class UserResponseTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(UserResponse.class);
//        UserResponse userResponse1 = new UserResponse();
//        userResponse1.setId(1L);
//        UserResponse userResponse2 = new UserResponse();
//        userResponse2.setId(userResponse1.getId());
//        assertThat(userResponse1).isEqualTo(userResponse2);
//        userResponse2.setId(2L);
//        assertThat(userResponse1).isNotEqualTo(userResponse2);
//        userResponse1.setId(null);
//        assertThat(userResponse1).isNotEqualTo(userResponse2);
//    }
//}
