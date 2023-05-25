//package com.sara.services.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sara.services.web.rest.TestUtil;
//import org.junit.jupiter.api.Test;
//
//class InterationsTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(Interations.class);
//        Interations interations1 = new Interations();
//        interations1.setId(1L);
//        Interations interations2 = new Interations();
//        interations2.setId(interations1.getId());
//        assertThat(interations1).isEqualTo(interations2);
//        interations2.setId(2L);
//        assertThat(interations1).isNotEqualTo(interations2);
//        interations1.setId(null);
//        assertThat(interations1).isNotEqualTo(interations2);
//    }
//}
