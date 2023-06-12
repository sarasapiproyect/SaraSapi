package com.sara.services.repository;

import com.sara.services.domain.Channel;
import com.sara.services.domain.DefaultResponse;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Channel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
	
	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT dr.channel_multimedia_id FROM SaraBusinessInteligente.rel_default_response__channel_multimedia dr WHERE dr.default_response_id=:defaultResponseId);", nativeQuery = true)
	List<Channel> getChannelMultimediaByDefaultResponseId(@Param("defaultResponseId") Long defaultResponseId);

	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT dr.channel_voice_id FROM SaraBusinessInteligente.rel_default_response__channel_voice dr WHERE dr.default_response_id=:defaultResponseId);", nativeQuery = true)
	List<Channel> getChannelVoiceByDefaultResponseId(@Param("defaultResponseId") Long defaultResponseId);

	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT dr.channel_animation_id FROM SaraBusinessInteligente.rel_default_response__channel_animation dr WHERE dr.default_response_id=:defaultResponseId);", nativeQuery = true)
	List<Channel> getChannelAnimationByDefaultResponseId(@Param("defaultResponseId") Long defaultResponseId);
	
	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT ur.channel_multimedia_id FROM SaraBusinessInteligente.rel_user_response__channel_multimedia ur WHERE ur.user_response_id=:userResponseId);", nativeQuery = true)
	List<Channel> getChannelMultimediaByUserResponseId(@Param("userResponseId") Long userResponseId);

	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT ur.channel_voice_id FROM SaraBusinessInteligente.rel_user_response__channel_voice ur WHERE ur.user_response_id=:userResponseId);", nativeQuery = true)
	List<Channel> getChannelVoiceByUserResponseId(@Param("userResponseId") Long userResponseId);

	@Query(value = "SELECT * FROM SaraBusinessInteligente.channel c WHERE c.id in (SELECT ur.channel_animation_id FROM SaraBusinessInteligente.rel_user_response__channel_animation ur WHERE ur.user_response_id=:userResponseId);", nativeQuery = true)
	List<Channel> getChannelAnimationByUserResponseId(@Param("userResponseId") Long userResponseId);


}
