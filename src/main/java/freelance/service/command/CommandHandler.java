package freelance.service.command;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

public interface CommandHandler {
   class HandlingContext{
      private final List<Runnable> onSuccessActions = new ArrayList<>();

      public void doOnSuccess(Runnable runnable){
         this.onSuccessActions.add(runnable);
      }

      public Iterable<Runnable> getOnSuccessActions(){
         return this.onSuccessActions;
      }
   }


   void handle(Command command, HandlingContext handlingContext);



}
