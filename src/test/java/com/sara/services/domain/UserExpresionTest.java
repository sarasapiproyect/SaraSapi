//package com.sara.services.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sara.services.web.rest.TestUtil;
//import org.junit.jupiter.api.Test;
//
//class UserExpresionTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(UserExpresion.class);
//        UserExpresion userExpresion1 = new UserExpresion();
//        userExpresion1.setId(1L);
//        UserExpresion userExpresion2 = new UserExpresion();
//        userExpresion2.setId(userExpresion1.getId());
//        assertThat(userExpresion1).isEqualTo(userExpresion2);
//        userExpresion2.setId(2L);
//        assertThat(userExpresion1).isNotEqualTo(userExpresion2);
//        userExpresion1.setId(null);
//        assertThat(userExpresion1).isNotEqualTo(userExpresion2);
//    }
//}
