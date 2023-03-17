package freelance.service.command;

import org.springframework.transaction.annotation.Isolation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface Command {
    void validateStateBeforeHandling();
   default String name (){
      return this.getClass().getName();
   }
     void validateStateAfterHandling();

     @Target(ElementType.TYPE)
     @Retention(RetentionPolicy.RUNTIME)
     public @interface Usecase{
          Class<? extends CommandHandler>[] handlers();

          boolean parallelHandling() default false;

          Isolation isolation() default Isolation.DEFAULT;

          String requiredAuthority() default "";

          boolean secured() default false;
     }
}
