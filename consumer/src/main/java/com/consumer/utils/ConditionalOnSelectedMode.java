package com.consumer.utils;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Conditional(ConditionalOnSelectedMode.OnSelectedModeCondition.class)
public @interface ConditionalOnSelectedMode {
    String value();

    class OnSelectedModeCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String modoEsperado = (String) metadata.getAnnotationAttributes(
                    ConditionalOnSelectedMode.class.getName()).get("value");
            return modoEsperado.equals(RabbitMQConfig.getFilaSelecionada());
        }
    }
}