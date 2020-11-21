package com.lottery.conf;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@Configuration
public class JacksonConfig {
 
	/**
	 * Jacksonȫ��ת��long����ΪString�����jackson���л�ʱlong����ȱʧ��������
	 * @return Jackson2ObjectMapperBuilderCustomizer ע��Ķ���
	 */
	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
		Jackson2ObjectMapperBuilderCustomizer cunstomizer = new Jackson2ObjectMapperBuilderCustomizer() {
			
			@Override
			public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
 
				jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
				jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);

			}
		};
		
		return cunstomizer;
	}
}
