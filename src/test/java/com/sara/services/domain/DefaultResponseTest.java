//package com.sara.services.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sara.services.web.rest.TestUtil;
//import org.junit.jupiter.api.Test;
//
//class DefaultResponseTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(DefaultResponse.class);
//        DefaultResponse defaultResponse1 = new DefaultResponse();
//        defaultResponse1.setId(1L);
//        DefaultResponse defaultResponse2 = new DefaultResponse();
//        defaultResponse2.setId(defaultResponse1.getId());
//        assertThat(defaultResponse1).isEqualTo(defaultResponse2);
//        defaultResponse2.setId(2L);
//        assertThat(defaultResponse1).isNotEqualTo(defaultResponse2);
//        defaultResponse1.setId(null);
//        assertThat(defaultResponse1).isNotEqualTo(defaultResponse2);
//    }
//}
