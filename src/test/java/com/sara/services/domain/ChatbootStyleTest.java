//package com.sara.services.domain;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import com.sara.services.web.rest.TestUtil;
//import org.junit.jupiter.api.Test;
//
//class ChatbootStyleTest {
//
//    @Test
//    void equalsVerifier() throws Exception {
//        TestUtil.equalsVerifier(ChatbootStyle.class);
//        ChatbootStyle chatbootStyle1 = new ChatbootStyle();
//        chatbootStyle1.setId(1L);
//        ChatbootStyle chatbootStyle2 = new ChatbootStyle();
//        chatbootStyle2.setId(chatbootStyle1.getId());
//        assertThat(chatbootStyle1).isEqualTo(chatbootStyle2);
//        chatbootStyle2.setId(2L);
//        assertThat(chatbootStyle1).isNotEqualTo(chatbootStyle2);
//        chatbootStyle1.setId(null);
//        assertThat(chatbootStyle1).isNotEqualTo(chatbootStyle2);
//    }
//}
